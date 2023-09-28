package io.microservice.userservice.Dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPassword {

    private String email;
}