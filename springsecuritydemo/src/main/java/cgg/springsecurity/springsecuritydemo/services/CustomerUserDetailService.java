package cgg.springsecurity.springsecuritydemo.services;

import cgg.springsecurity.springsecuritydemo.entities.CustomUserDetail;
import cgg.springsecurity.springsecuritydemo.entities.User;
import cgg.springsecurity.springsecuritydemo.repos.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailService implements UserDetailsService {

  @Autowired
  private UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    User user = repository.findByName(username);
    if (user == null) {
      throw new UsernameNotFoundException("User Not found");
    }
    CustomUserDetail userDetail = new CustomUserDetail(user);
    return userDetail;
  }
}
