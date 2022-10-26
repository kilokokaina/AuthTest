package com.example.newauthtest.controller;

import com.example.newauthtest.model.UserModel;
import com.example.newauthtest.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reg")
public class RegController {
    private final UserServiceImpl userService;

    @Autowired
    public RegController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String regGet(Model model) {
        return "reg";
    }

    @PostMapping()
    public String regPost(@RequestParam String username, @RequestParam String password) {
        UserModel user = new UserModel(username, password);
        userService.save(user);

        return "redirect:/";
    }
}
