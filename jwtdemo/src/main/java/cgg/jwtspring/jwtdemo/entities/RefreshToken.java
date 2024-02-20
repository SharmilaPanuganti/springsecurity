package cgg.jwtspring.jwtdemo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Table(name = "refresh_tokens")
public class RefreshToken {

  @Id
  @GeneratedValue
  private int tokenId;

  private String refreshToken;
  private Instant expiry;

  @OneToOne
  @JsonIgnore
  private User user;
}
