package cgg.jwtspring.jwtdemo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "jwt_user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue
  private int id;

  @Column(unique = true)
  private String userName;

  private String password;
  private String email;
  private String role;

  @OneToOne(mappedBy = "user")
  private RefreshToken refreshToken;
}
