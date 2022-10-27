package com.example.newauthtest.controller;

import com.example.newauthtest.DTO.CredentialsDTO;
import com.example.newauthtest.model.UserModel;
import com.example.newauthtest.service.impl.AuthenticationManagerImpl;
import com.example.newauthtest.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Controller
public class CustomLoginController {
    private final UserServiceImpl userService;
    private final AuthenticationManagerImpl authenticationManager;

    @Autowired
    public CustomLoginController(UserServiceImpl userService,
                                 AuthenticationManagerImpl authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("auth")
    public @ResponseBody String authPost(@RequestBody CredentialsDTO credentialsDTO) {
        String username = credentialsDTO.username;
        String password = credentialsDTO.password;

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        authentication = authenticationManager.authenticate(authentication);

        if (authentication == null) return "User NOT authenticated";

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User is Authenticated";
    }

    @GetMapping("reset_password")
    public @ResponseBody String resetPassword(
            @RequestParam(value = "username") String username) {
        try {
            UserModel userFromDB = userService.findByUsername(username);
            userFromDB.setResetPasswordUUID(
                    UUID.randomUUID().toString()
            );

            userService.update(userFromDB);

            return userFromDB.getResetPasswordUUID();
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }

        return "nope";
    }

    @GetMapping("reset_password/{UUID}")
    public @ResponseBody String setNewPassword(@PathVariable(value = "UUID") String linkUUID,
                                 @RequestParam(value = "new_password") String newPassword) {
        try {
            UserModel userFromDB = userService.findByResetPasswordUUID(linkUUID);

            userFromDB.setPassword(newPassword);
            userFromDB.setResetPasswordUUID("");

            userService.update(userFromDB);

            return userFromDB.getPassword();
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }

        return "nope";
    }
}
