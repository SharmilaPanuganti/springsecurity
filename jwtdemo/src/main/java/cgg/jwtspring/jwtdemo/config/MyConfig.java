package cgg.jwtspring.jwtdemo.config;

import cgg.jwtspring.jwtdemo.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class MyConfig {

  @Autowired
  private CustomUserDetailsService userDetailsService;

  // @Bean
  // UserDetailsService getUDetailsService() {
  //   UserDetails userDetails = User
  //     .builder()
  //     .username("sharmila")
  //     .password(getPasswordEncoder().encode("1234"))
  //     .roles("ADMIN")
  //     .build();
  //   return new InMemoryUserDetailsManager(userDetails);
  // }

  @Bean
  PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  AuthenticationManager getAuthenticationManager(
    AuthenticationConfiguration builder
  ) throws Exception {
    return builder.getAuthenticationManager();
  }

  @Bean
  AuthenticationProvider getAuthenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(
      getPasswordEncoder()
    );
    authProvider.setUserDetailsService(userDetailsService);
    return authProvider;
  }
}
