package com.todo.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

//id="entity_todo"
@Entity
@Data
@NoArgsConstructor
public class Todo {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String task;
 private String description;

 private LocalDate date;
 private LocalTime time;

 private Integer status; // percentage (0–100)

 @ManyToOne
 @JoinColumn(name = "user_id")
 private User user;
}