package cgg.jwtspring.jwtdemo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  private Logger logger = LoggerFactory.getLogger(HomeController.class);

  @GetMapping("/test")
  public String test() {
    logger.warn("test warning");
    return "test";
  }
}
