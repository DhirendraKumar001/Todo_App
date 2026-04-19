package com.todo.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.model.Todo;

//id="repo_todo"
public interface TodoRepository extends JpaRepository<Todo, Long> {

    // 🔥 Correct way to access nested field (user.id)
    Optional<Todo> findByIdAndUser_Id(Long id, Long userId);

    // 🔥 Also fix getAll if not already
    List<Todo> findByUser_Id(Long userId);
}