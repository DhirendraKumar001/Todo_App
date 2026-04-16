package com.todo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.todo.model.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUserId(Long userId);
}