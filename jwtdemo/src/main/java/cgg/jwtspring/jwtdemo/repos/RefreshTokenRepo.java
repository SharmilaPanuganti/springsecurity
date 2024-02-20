package cgg.jwtspring.jwtdemo.repos;

import cgg.jwtspring.jwtdemo.entities.RefreshToken;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {
  Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
