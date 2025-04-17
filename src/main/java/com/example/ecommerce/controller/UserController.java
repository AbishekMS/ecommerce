package com.example.ecommerce.controller;


import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.serviceimplementation.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomUserDetailsService userService;

    @GetMapping("/details")
    public ResponseEntity<List<Users>> getUsersDetails(){
        return  ResponseEntity.ok(userService.getUsersDetails());
    }

    @PostMapping
    public ResponseEntity<Users> addUserDetails(@RequestBody Users user){
        return new ResponseEntity<>(userService.saveUsers(user), HttpStatus.CREATED);
    }
}
