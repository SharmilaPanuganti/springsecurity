package cgg.springsecurity.springsecuritydemo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auth_user")
public class User {

  @Id
  @GeneratedValue
  private int id;

  @Column(unique = true)
  private String name;

  private String password;
  private String email;
  private String role;
}
