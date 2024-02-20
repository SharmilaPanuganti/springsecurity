package cgg.springsecurity.springsecuritydemo.config;

import cgg.springsecurity.springsecuritydemo.services.CustomerUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MySecurityConfig {

  @Autowired
  private CustomerUserDetailService userDetailService;

  @Bean
  SecurityFilterChain getSecurityFilterChain(HttpSecurity http)
    throws Exception {
    /*http
        .csrf(csrf ->
          csrf
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
        )
        .authorizeHttpRequests(authReq ->
          authReq
            // .requestMatchers("/public/**")
            // .hasRole("NORMAL")
            .requestMatchers("/users/**")
            .hasRole("ADMIN")
            .anyRequest()
            .authenticated()
        )
        .httpBasic(withDefaults());*/

    // http
    //   .authorizeHttpRequests(auth ->
    //     auth.requestMatchers("/signin").permitAll().anyRequest().authenticated()
    //   )
    //   .formLogin(Customizer.withDefaults());
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth ->
        auth
          .requestMatchers("/signin", "/doLogin")
          .permitAll()
          .anyRequest()
          .authenticated()
      )
      .formLogin(login ->
        login
          .loginPage("/signin")
          .loginProcessingUrl("/doLogin")
          .defaultSuccessUrl("/users/getusers")
      )
      .logout(logout ->
        logout.logoutUrl("/logout").logoutSuccessUrl("/signin?logout")
      );

    /*http
      .authorizeHttpRequests(auth ->
        auth
          .requestMatchers("/public/**")
          .permitAll()
          .anyRequest()
          .authenticated()
      )
      .httpBasic(withDefaults());*/
    /* http
      .authorizeHttpRequests(auth ->
        auth
          .requestMatchers("/public/**")
          .hasRole("NORMAL")
          .anyRequest()
          .authenticated()
      )
      .httpBasic(withDefaults());*/
    /*http
      .authorizeHttpRequests(auth ->
        auth
          .requestMatchers("/public/**")
          .hasRole("NORMAL")
          .requestMatchers("/users/**")
          .hasRole("ADMIN")
          .anyRequest()
          .authenticated()
      )
      .httpBasic(withDefaults());*/

    return http.build();
  }

  @Bean
  UserDetailsService getUserDetailsService() {
    /*UserBuilder user = User
      .withUsername("sharmila")
      .password(getPasswordEncoder().encode("1234"))
      .roles("NORMAL");
    UserDetails admin = User
      .withUsername("admin")
      .password(getPasswordEncoder().encode("1234"))
      .roles("ADMIN")
      .build();*/

    // UserDetails userDetails = user.build();
    return userDetailService;
  }

  @Bean
  PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  AuthenticationProvider getAuthProvider() {
    DaoAuthenticationProvider dAuthProvider = new DaoAuthenticationProvider();
    dAuthProvider.setUserDetailsService(userDetailService);
    dAuthProvider.setPasswordEncoder(getPasswordEncoder());
    return dAuthProvider;
  }
}
