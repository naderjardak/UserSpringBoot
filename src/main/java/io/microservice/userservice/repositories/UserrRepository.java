package io.microservice.userservice.repositories;

import io.microservice.userservice.entities.User;
import io.microservice.userservice.entities.enmus.RoleType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserrRepository extends CrudRepository<User,Long> {

    List<User> findUserByRoleType(RoleType roleType);
    Optional<User> findUserByEmail(String email);

    User findByResetToken(String resetToken);

}
