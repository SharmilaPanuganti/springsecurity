package cgg.jwtspring.jwtdemo.helper;

import cgg.jwtspring.jwtdemo.entities.RefreshToken;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtResponse {

  private String token;
  private String user;
  private String refreshToken;
}
