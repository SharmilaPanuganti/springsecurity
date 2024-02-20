package cgg.jwtspring.jwtdemo.helper;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtRequest {

  private String userName;
  private String password;
}
