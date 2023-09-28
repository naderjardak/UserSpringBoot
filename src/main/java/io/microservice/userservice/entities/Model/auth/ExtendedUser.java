package io.microservice.userservice.entities.Model.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ExtendedUser extends User {

    public  ExtendedUser(String username, String password
            , Collection<? extends GrantedAuthority> authorities){
        super(username,password,authorities);
    }


}

