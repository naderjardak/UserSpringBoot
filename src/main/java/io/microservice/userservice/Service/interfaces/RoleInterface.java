package io.microservice.userservice.Service.interfaces;



import io.microservice.userservice.entities.Role;

import java.util.List;

public interface RoleInterface {

    Role CreateRole(Role r);

    void DeleteRoleById(long id);

    public Role updateRole(Role r);

    Role GetRoleById(long id);
    public List<Role> GetRoleAll();



}
