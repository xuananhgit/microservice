package com.example.userservice.controller;

import com.example.userservice.service.UserService;
import com.example.userservice.model.User;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Inject
    private UserService userService;

    @GetMapping("/{username}")
    public User findByUserName(@PathVariable String username) {
        return userService.findByUserName(username);
    }

}
