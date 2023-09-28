package io.microservice.userservice.controllers;

import io.microservice.userservice.Service.interfaces.UserInterface;
import io.microservice.userservice.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "*", allowedHeaders = "*") // add this line
public class UserController {
    @Autowired
    UserInterface userInterface;


    @PostMapping("/addUser")
    public User Create(@RequestBody User u) {
        return userInterface.Create(u);
    }

    @DeleteMapping("/deleteUser")
    void DeleteById(@RequestParam long id) {
        userInterface.DeleteById(id);
    }

    @PutMapping("/updateUser")
    public User update(@RequestBody User u) {
       return  userInterface.update(u);
    }

    @GetMapping("/selectUserById")
    User GetById(@RequestParam long id){
        return userInterface.GetById(id);
    }

    @GetMapping("/selectUserAll")
    public List<User>GetAll(){
        return userInterface.GetAll();
    }

    @PutMapping("/affectRole")
    public void affectRoleAtUser(@RequestParam long idRole,@RequestParam long idUser){
     userInterface.affectRoleAtUser(idRole,idUser);
    }
}
