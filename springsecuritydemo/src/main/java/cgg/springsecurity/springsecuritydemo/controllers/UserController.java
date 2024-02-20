package cgg.springsecurity.springsecuritydemo.controllers;

import cgg.springsecurity.springsecuritydemo.entities.User;
import cgg.springsecurity.springsecuritydemo.repos.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  // private static List<User> users = new ArrayList<>();

  // static {
  //   users.add(new User(1, "Sharmila", "sharmi379", "sharmi@gmail.com"));
  //   users.add(new User(2, "Sai", "sai379", "sai@gmail.com"));
  //   users.add(new User(3, "Madhu", "madhu379", "Madhu@gmail.com"));
  //   users.add(new User(4, "Teja", "Teja", "Teja@gmail.com"));
  // }

  @GetMapping("/getusers")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(userRepository.findAll());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
    User user = userRepository.findById(id).orElse(null);
    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return ResponseEntity.ok(user);
  }

  @PostMapping("/")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }
}
