package com.todo.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.todo.model.History;
import com.todo.model.User;
import com.todo.repository.HistoryRepository;

@Service
public class HistoryService {

    private final HistoryRepository repo;

    public HistoryService(HistoryRepository repo) {
        this.repo = repo;
    }

    public void save(String action, String taskName, User user) {
        History h = new History();
        h.setAction(action);
        h.setTaskName(taskName);
        h.setTime(LocalDateTime.now());
        h.setUser(user);

        repo.save(h);
    }

    public List<History> getUserHistory(Long userId) {
        return repo.findByUserId(userId);
    }
}