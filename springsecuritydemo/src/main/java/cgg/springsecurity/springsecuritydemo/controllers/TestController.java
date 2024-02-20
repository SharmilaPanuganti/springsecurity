package cgg.springsecurity.springsecuritydemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {

  @GetMapping("/signin")
  public String login() {
    return "login";
  }

  @GetMapping("/logout")
  public String logout() {
    return "logout";
  }
}
