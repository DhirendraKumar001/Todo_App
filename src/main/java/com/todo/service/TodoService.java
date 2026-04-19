package com.todo.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.todo.model.Todo;
import com.todo.repository.TodoRepository;

//id="service_todo"
@Service
public class TodoService {

    private final TodoRepository repo;
    private final HistoryService historyService;   // ✅ added

    public TodoService(TodoRepository repo, HistoryService historyService) {
        this.repo = repo;
        this.historyService = historyService;      // ✅ added
    }

    // ➕ ADD TASK
    public Todo addTask(Todo todo) {
        todo.setDate(LocalDate.now());
        todo.setTime(LocalTime.now());

        Todo saved = repo.save(todo);

        // ✅ HISTORY
        historyService.save("CREATED", saved.getTask(), saved.getUser());

        return saved;
    }

    // 📋 GET ALL
    public List<Todo> getAll(Long userId) {
        return repo.findByUser_Id(userId);   // ✅ FIXED
    }
    
 // 🔍 GET BY ID
    public Todo getById(Long id, Long userId) {
        return repo.findByIdAndUser_Id(id, userId)   // ✅ FIXED
                .orElseThrow(() -> new RuntimeException("Task not found or unauthorized"));
    }

    // 🔄 UPDATE STATUS
    public Todo updatePartial(Long id, Todo newTodo) {

        Todo existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        System.out.println("Incoming object: " + newTodo);
        System.out.println("Incoming status: " + newTodo.getStatus());

        if (newTodo.getStatus() != null) {
            if (newTodo.getStatus() <= 0 || newTodo.getStatus() >= 100) {
                throw new RuntimeException("Status must be 0-100");
            }

            existing.setStatus(newTodo.getStatus());
            System.out.println("Updated status to: " + newTodo.getStatus());

            // ✅ HISTORY
            historyService.save("UPDATED", existing.getTask(), existing.getUser());

        } else {
            System.out.println("Status is NULL ❌");
        }

        return repo.save(existing);
    }

    // ❌ DELETE
    public void delete(Long id, Long userId) {

        Todo todo = repo.findByIdAndUser_Id(id, userId)   // ✅ FIXED
                .orElseThrow(() -> new RuntimeException("Task not found or not allowed"));

        // ✅ HISTORY (optional but recommended)
        historyService.save("DELETED", todo.getTask(), todo.getUser());

        repo.delete(todo);
    }
}