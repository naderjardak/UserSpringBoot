package io.microservice.userservice.Service.auth;


import io.microservice.userservice.Service.UserService;
import io.microservice.userservice.entities.Model.auth.ExtendedUser;
import io.microservice.userservice.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        io.microservice.userservice.entities.User user = userService.findByEmail(email);

        Collection<SimpleGrantedAuthority> authorities =new ArrayList<>();
        String role = user.getRole().getType().toString();
        authorities.add(new SimpleGrantedAuthority(role));
        return new ExtendedUser(user.getEmail() ,user.getPassword(),authorities);
    }
}
