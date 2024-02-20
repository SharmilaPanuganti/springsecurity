package cgg.springsecurity.springsecuritydemo.repos;

import cgg.springsecurity.springsecuritydemo.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  User findByName(String name);
}
