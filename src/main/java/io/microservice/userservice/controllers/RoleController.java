package io.microservice.userservice.controllers;

import io.microservice.userservice.Service.interfaces.RoleInterface;
import io.microservice.userservice.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
@CrossOrigin(origins = "*", allowedHeaders = "*") // add this line
public class RoleController {
    @Autowired
    RoleInterface roleInterface;

    @PostMapping("/addRole")
    public Role CreateRole(@RequestBody Role r) {
        return roleInterface.CreateRole(r);
    }

    @DeleteMapping("/deleteRole")
    void DeleteRoleById(@RequestParam long id) {
        roleInterface.DeleteRoleById(id);
    }

    @PutMapping("/updateRole")
    public Role updateRole(@RequestBody Role r) {
        return  roleInterface.updateRole( r);
    }

    @GetMapping("/selectRoleById")
    Role GetRoleById(@RequestParam long id){

        return roleInterface.GetRoleById(id);
    }

    @GetMapping("/selectRoleAll")
    public List<Role> GetRoleAll(){
        return roleInterface.GetRoleAll();
    }

}
