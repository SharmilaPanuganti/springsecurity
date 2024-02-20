package cgg.jwtspring.jwtdemo.controllers;

import cgg.jwtspring.jwtdemo.entities.CustomUserDetails;
import cgg.jwtspring.jwtdemo.entities.RefreshToken;
import cgg.jwtspring.jwtdemo.entities.User;
import cgg.jwtspring.jwtdemo.helper.JwtHelper;
import cgg.jwtspring.jwtdemo.helper.JwtRequest;
import cgg.jwtspring.jwtdemo.helper.JwtResponse;
import cgg.jwtspring.jwtdemo.helper.RefreshTokenRequest;
import cgg.jwtspring.jwtdemo.services.RefreshTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private UserDetailsService uDetailsService;

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private JwtHelper helper;

  @Autowired
  private RefreshTokenService rTokenService;

  private Logger logger = LoggerFactory.getLogger(AuthController.class);

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
    this.doAuthenticate(request.getUserName(), request.getPassword());
    UserDetails userDetails = uDetailsService.loadUserByUsername(
      request.getUserName()
    );
    String token = helper.generateToken(userDetails);
    RefreshToken refreshToken = rTokenService.createRefreshToken(
      userDetails.getUsername()
    );

    JwtResponse response = JwtResponse
      .builder()
      .token(token)
      .refreshToken(refreshToken.getRefreshToken())
      .user(request.getUserName())
      .build();

    return ResponseEntity.ok(response);
  }

  private void doAuthenticate(String userName, String password) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
      userName,
      password
    );
    try {
      authManager.authenticate(authenticationToken);
    } catch (BadCredentialsException ex) {
      throw new BadCredentialsException("Invalide username or password");
    }
  }

  @ExceptionHandler(BadCredentialsException.class)
  public String exceptionHandler(BadCredentialsException ex) {
    return ex.getMessage();
  }

  @PostMapping("/refresh")
  public JwtResponse refreshJwtToken(@RequestBody RefreshTokenRequest request) {
    RefreshToken refreshToken = rTokenService.verifyRefreshToken(
      request.getRefreshToken()
    );
    User user = refreshToken.getUser();

    String token = helper.generateToken(new CustomUserDetails(user));
    return JwtResponse
      .builder()
      .refreshToken(refreshToken.getRefreshToken())
      .token(token)
      .user(user.getUserName())
      .build();
  }
}
