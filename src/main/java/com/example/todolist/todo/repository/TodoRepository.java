package com.example.todolist.todo.repository;

import com.example.todolist.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByOrderByCreatedAtDesc();

    List<Todo> findByTitleOrderByCreatedAtDesc(String 제목);
}
