package com.todo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.model.User;
import com.todo.service.UserService;

//id="controller_user"
@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class UserController {

 private final UserService service;

 public UserController(UserService service) {
     this.service = service;
 }

 @PostMapping("/register")
 public User register(@RequestBody User user) {
     return service.register(user);
 }

 @PostMapping("/login")
 public User login(@RequestBody User user) {
     return service.login(user.getEmail(), user.getPassword());
 }
}
