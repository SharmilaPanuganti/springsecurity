package cgg.jwtspring.jwtdemo.helper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

  @Autowired
  private JwtHelper jwtHelper;

  @Autowired
  private UserDetailsService uDetailsService;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    String header = request.getHeader("Authorization");
    logger.info("Header : {}", header);
    String user = null;
    String token = null;
    if (header != null && header.startsWith("Bearer")) {
      token = header.substring(7);
      try {
        user = jwtHelper.getUsernameFromToken(token);
      } catch (IllegalArgumentException e) {
        logger.error("Illegal argument username");
        e.printStackTrace();
      } catch (ExpiredJwtException ex) {
        logger.error("Expired token");
        ex.printStackTrace();
      } catch (MalformedJwtException ex) {
        logger.error("Invalid token! changes are done in token");
        ex.printStackTrace();
      } catch (Exception e) {
        logger.error("Something went wrong!!");
        e.printStackTrace();
      }
    } else {
      logger.info("Invalid header value");
    }
    if (
      user != null &&
      SecurityContextHolder.getContext().getAuthentication() == null
    ) {
      UserDetails userDetails = this.uDetailsService.loadUserByUsername(user);
      Boolean validateToken = jwtHelper.validateToken(token, userDetails);
      if (validateToken) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()
        );
        authToken.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
      } else {
        logger.info("Validation fails");
      }
    }
    filterChain.doFilter(request, response);
  }
}
