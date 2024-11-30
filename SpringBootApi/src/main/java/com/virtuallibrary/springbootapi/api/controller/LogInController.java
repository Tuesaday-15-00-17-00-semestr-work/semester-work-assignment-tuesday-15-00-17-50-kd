package com.virtuallibrary.springbootapi.api.controller;

import com.virtuallibrary.springbootapi.api.model.User;
import com.virtuallibrary.springbootapi.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class LogInController {
    private UserService userService;

    public LogInController() {
        this.userService = new UserService();
    }

    @GetMapping("/login")
    public ResponseEntity login(@RequestParam String username, @RequestParam String password) throws SQLException {
        User user = userService.getUserByUsername(username);
        if(user.getPassword().equals(password)) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
