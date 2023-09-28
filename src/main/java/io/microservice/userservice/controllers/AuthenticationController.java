package io.microservice.userservice.controllers;


import io.microservice.userservice.Dto.auth.AuthenticationRequest;
import io.microservice.userservice.Dto.auth.AuthenticationResponse;
import io.microservice.userservice.Service.auth.ApplicationUserDetailsService;
import io.microservice.userservice.configuration.JWT;
import io.microservice.userservice.configuration.SpringSecurityConfiguration;
import io.microservice.userservice.repositories.UserrRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("authentication")

public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ApplicationUserDetailsService userDetailsService;
    @Autowired
    private JWT jwt;
    @Autowired
    UserrRepository userrRepository;


    @PostMapping("auth")
    @SecurityRequirements
    public AuthenticationResponse authenticate(@NotNull @RequestBody AuthenticationRequest Request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        Request.getLogin(),
                        Request.getPassword()
                )
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(Request.getLogin());

        // Vérifier si le mot de passe fourni correspond au mot de passe enregistré pour l'utilisateur
        if (userDetails != null && SpringSecurityConfiguration.matchPassword(Request.getPassword(), userDetails.getPassword())) {

            final String JWTT = jwt.generateToken(userDetails);
         /*   HttpHeaders headers=new HttpHeaders();
            headers.set("Authorization","Bearer" + JWTT);*/
            return new AuthenticationResponse(JWTT);
        } else {
            // Le mot de passe est incorrect, retourner une erreur d'authentification
            throw new BadCredentialsException("Invalid username or password");
        }


    }


}