package cgg.jwtspring.jwtdemo.services;

import cgg.jwtspring.jwtdemo.entities.RefreshToken;
import cgg.jwtspring.jwtdemo.entities.User;
import cgg.jwtspring.jwtdemo.repos.RefreshTokenRepo;
import cgg.jwtspring.jwtdemo.repos.UserRepository;
import java.time.Instant;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RefreshTokenService
 */
@Service
public class RefreshTokenService {

  public long tokenValidity = 2 * 60 * 1000;

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private RefreshTokenRepo refreshTokenRepo;

  public RefreshToken createRefreshToken(String userName) {
    User user = userRepo.findByUserName(userName).get();
    RefreshToken refreshToken = user.getRefreshToken();
    if (refreshToken == null) {
      refreshToken =
        RefreshToken
          .builder()
          .refreshToken(UUID.randomUUID().toString())
          .expiry(Instant.now().plusMillis(tokenValidity))
          .user(user)
          .build();
    } else {
      refreshToken.setExpiry(Instant.now().plusMillis(tokenValidity));
    }
    user.setRefreshToken(refreshToken);
    refreshTokenRepo.save(refreshToken);
    return refreshToken;
  }

  public RefreshToken verifyRefreshToken(String refreshToken) {
    RefreshToken token = refreshTokenRepo
      .findByRefreshToken(refreshToken)
      .orElseThrow(() -> new RuntimeException("token does not exist"));
    if (token.getExpiry().compareTo(Instant.now()) < 0) {
      throw new RuntimeException("Refresh token expired");
    }
    return token;
  }
}
