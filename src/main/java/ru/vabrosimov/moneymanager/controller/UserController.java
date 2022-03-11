package ru.vabrosimov.moneymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vabrosimov.moneymanager.config.jwt.JwtProvider;
import ru.vabrosimov.moneymanager.entity.User;
import ru.vabrosimov.moneymanager.service.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/addUser")
    public String addUser(@RequestBody User user) {
        boolean status = userService.saveUser(user);
        if (status) return "success";
        else return "fail";
    }

    @PostMapping("/auth")
    public String authUser(@RequestBody User request) {
        User user = userService.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        String token = jwtProvider.generateToken(user.getUsername(), user.getUserRole().getName());
        return token;
    }

    @GetMapping("/isUsernameUsed")
    public boolean isUsernameUsed(@RequestParam String username) {
        return userService.isUsernameUsed(username);
    }
}
