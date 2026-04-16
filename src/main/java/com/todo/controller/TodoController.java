package com.todo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.model.Todo;
import com.todo.service.TodoService;

//id="controller_todo"
@RestController
@RequestMapping("/todos")
@CrossOrigin("*")
public class TodoController {

 private final TodoService service;

 public TodoController(TodoService service) {
     this.service = service;
 }

 @PostMapping
 public Todo add(@RequestBody Todo todo) {
     return service.addTask(todo);
 }

 @GetMapping("/{userId}")
 public List<Todo> getAll(@PathVariable Long userId) {
     return service.getAll(userId);
 }

 @GetMapping("/task/{id}")
 public ResponseEntity<Todo> getById(@PathVariable Long id) {
     try {
         Todo todo = service.getById(id);
         return ResponseEntity.ok(todo);
     } catch (RuntimeException e) {
         return ResponseEntity.notFound().build();
     }
 }

 @PatchMapping("/{id}")
 public Todo updatePartial(@PathVariable Long id, @RequestBody Todo todo) {
     return service.updatePartial(id, todo);
 }

 @DeleteMapping("/{id}")
 public void delete(@PathVariable Long id) {
     service.delete(id);
 }
}
