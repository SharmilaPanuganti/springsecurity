package cgg.springsecurity.springsecuritydemo;

import cgg.springsecurity.springsecuritydemo.entities.User;
import cgg.springsecurity.springsecuritydemo.repos.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringsecuritydemoApplication implements CommandLineRunner {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public static void main(String[] args) {
    SpringApplication.run(SpringsecuritydemoApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    User user = new User();
    user.setEmail("admin@gmail.com");
    user.setName("admin");
    user.setPassword(passwordEncoder.encode("1234"));
    user.setRole("ROLE_ADMIN");
    // User user2 = new User();
    // user2.setEmail("sharmila@gmail.com");
    // user2.setName("sharmila");
    // user2.setPassword(passwordEncoder.encode("1234"));
    // user2.setRole("ROLE_NORMAL");
    // userRepository.save(user);
  }
}
