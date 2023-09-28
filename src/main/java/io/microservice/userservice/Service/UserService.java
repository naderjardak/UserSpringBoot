package io.microservice.userservice.Service;


import io.microservice.userservice.Service.interfaces.UserInterface;
import io.microservice.userservice.entities.Role;
import io.microservice.userservice.entities.User;
import io.microservice.userservice.repositories.RoleRepository;
import io.microservice.userservice.repositories.UserrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
@Service
public class UserService implements UserInterface {

    @Autowired
    UserrRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    PasswordEncoder passwordEncoder;
   @Autowired
   public UserService(){
       this.passwordEncoder=new BCryptPasswordEncoder();
   }

    @Override
    public User CreateForReset(User u) {
        u.setPassword(this.passwordEncoder.encode(u.getPassword()));
        userRepository.save(u);
        return  u;
    }

    @Override
    public User findByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    @Override
    public User Create(User u) {
        if(validatePassword(u.getPassword())) {
            u.setPassword(this.passwordEncoder.encode(u.getPassword()));
            u.setRole(roleRepository.findById(2L).get());
            return userRepository.save(u);
        }
        return new User();
    }

    @Override
    public void DeleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update( User u) {
        return userRepository.save(u);
    }

    @Override
    public User GetById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> GetAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public void affectRoleAtUser(long idRole, long idUser) {
        Role r=roleRepository.findById(idRole).get();
        User u=userRepository.findById(idUser).get();
        u.setRole(r);
        userRepository.save(u);
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(()->new EntityNotFoundException((
                "user not found with email =" +email)
        ));
    }


    public boolean validatePassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (isSpecialChar(c)) {
                hasSpecialChar = true;
            }
        }
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }

    private boolean isSpecialChar(char c) {
        // Replace this with your desired set of special characters
        String specialChars = "!@#$%^&*()_+-=[]{}|;':\"<>,.?/\\";
        return specialChars.indexOf(c) != -1;
    }

}
