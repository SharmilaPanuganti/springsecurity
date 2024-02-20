package cgg.jwtspring.jwtdemo.repos;

import cgg.jwtspring.jwtdemo.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  public Optional<User> findByUserName(String userName);
}
