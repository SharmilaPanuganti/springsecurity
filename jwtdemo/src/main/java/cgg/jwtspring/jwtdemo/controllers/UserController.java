package cgg.jwtspring.jwtdemo.controllers;

import cgg.jwtspring.jwtdemo.entities.User;
import cgg.jwtspring.jwtdemo.repos.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostMapping("/create-user")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User save = userRepo.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(save);
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> getUsers() {
    List<User> all = userRepo.findAll();
    return ResponseEntity.ok(all);
  }
}
