package com.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.model.User;

//id="repo_user"
public interface UserRepository extends JpaRepository<User, Long> {
 User findByEmail(String email);
}
