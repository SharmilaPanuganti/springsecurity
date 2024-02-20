package cgg.jwtspring.jwtdemo.config;

import cgg.jwtspring.jwtdemo.helper.JwtAuthEntryPoint;
import cgg.jwtspring.jwtdemo.helper.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Autowired
  private JwtAuthEntryPoint entryPoint;

  @Autowired
  private JwtAuthFilter authFilter;

  @Bean
  SecurityFilterChain getFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .cors(cor -> cor.disable())
      .authorizeHttpRequests(auth ->
        auth
          .requestMatchers("/test")
          .authenticated()
          .requestMatchers("/auth/login")
          .permitAll()
          .requestMatchers("/create-user")
          .permitAll()
          .requestMatchers("/auth/refresh")
          .permitAll()
          .anyRequest()
          .authenticated()
      )
      .exceptionHandling(e -> e.authenticationEntryPoint(entryPoint))
      .sessionManagement(s ->
        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      );
    http.addFilterBefore(
      authFilter,
      UsernamePasswordAuthenticationFilter.class
    );
    return http.build();
  }
}
