package cgg.springsecurity.springsecuritydemo.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@EnableMethodSecurity
public class HomeController {

  @GetMapping("/home")
  @PreAuthorize("hasRole('NORMAL')")
  public String home() {
    return "Home page";
  }

  @GetMapping("/login")
  @PreAuthorize("hasRole('NORMAL')")
  public String login() {
    return "login page";
  }

  @GetMapping("/register")
  @PreAuthorize("hasRole('NORMAL')")
  public String register() {
    return "Register page";
  }
}
