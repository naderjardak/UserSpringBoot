package io.microservice.userservice.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.microservice.userservice.entities.enmus.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Role implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleType type;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<User> users;



}
