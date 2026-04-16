package com.todo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.todo.model.History;
import com.todo.service.HistoryService;

@RestController
@RequestMapping("/history")
@CrossOrigin("*")
public class HistoryController {

    private final HistoryService service;

    public HistoryController(HistoryService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public List<History> getHistory(@PathVariable Long userId) {
        return service.getUserHistory(userId);
    }
}