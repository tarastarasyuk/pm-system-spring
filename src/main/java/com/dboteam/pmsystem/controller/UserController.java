package com.dboteam.pmsystem.controller;


import com.dboteam.pmsystem.model.User;
import com.dboteam.pmsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

}
