package com.todo.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.todo.model.User;
import com.todo.repository.UserRepository;

//id="service_user"
@Service
public class UserService {

 private final UserRepository repo;

 public UserService(UserRepository repo) {
     this.repo = repo;
 }

 
 public User register(User user) {
	 user.setCreatedDate(LocalDate.now());
     user.setCreatedTime(LocalTime.now());
	 if (repo.findByEmail(user.getEmail()) != null) {
	        throw new RuntimeException("User already exists");
	    }
	    return repo.save(user);
	}

	public User login(String email, String password) {
	    User user = repo.findByEmail(email);

	    if (user == null || !user.getPassword().equals(password)) {
	        throw new RuntimeException("Invalid credentials");
	    }

	    return user;
	}
}