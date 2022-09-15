package com.example.newauthtest.controller;

import com.example.newauthtest.DTO.CredentialsDTO;
import com.example.newauthtest.service.impl.AuthenticationManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class CustomLoginController {
    private final AuthenticationManager authenticationManager;

    @Autowired
    public CustomLoginController(AuthenticationManagerImpl authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public @ResponseBody String authPost(@RequestBody CredentialsDTO credentialsDTO) {
        String username = credentialsDTO.username;
        String password = credentialsDTO.password;

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        authentication = authenticationManager.authenticate(authentication);

        if (authentication == null) return "User NOT authenticated";

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User is Authenticated";
    }
}
