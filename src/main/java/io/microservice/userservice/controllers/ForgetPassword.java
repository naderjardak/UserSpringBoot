package io.microservice.userservice.controllers;

import io.microservice.userservice.API.MailSenderService;
import io.microservice.userservice.Dto.auth.AccountResponse;
import io.microservice.userservice.Dto.auth.ResetPassword;
import io.microservice.userservice.Service.UserService;
import io.microservice.userservice.Service.interfaces.UserInterface;
import io.microservice.userservice.entities.User;
import io.microservice.userservice.repositories.UserrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("ForgetPassword")

public class ForgetPassword {

    @Autowired
    UserInterface userInterface;

    @Autowired
    UserService userService;

    @Autowired
    MailSenderService mailSenderService;


    @Autowired
    UserrRepository userrRepository;



    PasswordEncoder passwordEncoder;

    {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("checkEmail")
    public String resetPasswordEmail(@RequestBody ResetPassword resetPassword, HttpServletRequest request) {
        User user = userService.findByEmail(resetPassword.getEmail());
        AccountResponse accountResponse = new AccountResponse();
        if (user != null) {
            // String code = UserCode.getCode();
         user.setResetToken(UUID.randomUUID().toString());
            userService.CreateForReset(user);
            String appUrl = request.getScheme() + "://" + request.getServerName();
            mailSenderService.sendEmail(resetPassword.getEmail(), "Forget password", "To reset your password, check your token :\n" +
                    "token=" + user.getResetToken());
           // ResetTwilio.sendSMS("To reset your password, check your code : code=" + user.getResetToken());
            accountResponse.setResult("User Found");
        } else {
            accountResponse.setResult("Forgot Password");
        }
        return "redirect:/forgot-password?success";
    }

    @GetMapping("resetPassword")
    public Map<String, Boolean> resetPassword(@RequestParam(required = false) String resetToken) {
        Map<String, Boolean> response = new HashMap<>();
        User user = this.userService.findByResetToken(resetToken);
        if (user == null) {
            response.put("isValidToken", false);
        } else {
            response.put("isValidToken", true);
        }
        return response;
    }


    @GetMapping("/changePass")
    public String processResetPassword(@RequestParam("resetToken") String resetToken, @RequestParam String Password ) {

        if(userService.validatePassword(Password)) {

            AccountResponse accountResponse = new AccountResponse();


            // Find the user associated with the reset token
            User user = userService.findByResetToken(resetToken);

            if (user != null) {
                String a = this.passwordEncoder.encode(Password);
                System.out.println(a);
                user.setPassword(a);
                userrRepository.save(user);
            }
            return "password changed";
        }
        return "password error";
    }

}
