package com.example.todolist.todo.controller;

import com.example.todolist.security.UserDetailsImpl;
import com.example.todolist.service.TodoService;
import com.example.todolist.todo.dto.TodoRequestDto;
import com.example.todolist.todo.dto.TodoResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("")
    public TodoResponseDto createTodo(@RequestBody @Valid TodoRequestDto requestDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoService.createTodo(requestDto, userDetails.getUser());
    }

    @GetMapping("")
    public List<TodoResponseDto> getAllTodo() {
        return todoService.getAllTodo();
    }

    @GetMapping("/{id}")
    public TodoResponseDto getTodo(@PathVariable Long id) {
        return todoService.getTodo(id);
    }


    @PutMapping("/{id}")
    public TodoResponseDto updateTodo(@PathVariable Long id, @RequestBody @Valid TodoRequestDto requestDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoService.updateTodo(id, requestDto, userDetails.getUser());


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        todoService.deleteTodo(id, userDetails.getUser());
        return ResponseEntity.ok("삭제 완료");
    }

    @PutMapping("/{id}/complate")
    public ResponseEntity<String> complateTodo(@PathVariable Long id, @PathVariable boolean isCompleted, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        todoService.complateTodo(id, isCompleted, userDetails.getUser());
        return ResponseEntity.ok("완료");
    }



}
