package com.example.todolist.todo.service;

import com.example.todolist.todo.dto.TodoRequestDto;
import com.example.todolist.todo.dto.TodoResponseDto;
import com.example.todolist.todo.entity.Todo;
import com.example.todolist.todo.repository.TodoRepository;
import com.example.todolist.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoResponseDto createTodo(TodoRequestDto requestDto, User user) {
        Todo todo = todoRepository.save(new Todo(requestDto, user));
        todo.setUser(user);

        return new TodoResponseDto(todo);
    }

    public List<TodoResponseDto> getAllTodo() {
        return todoRepository.findAll().stream().map(TodoResponseDto::new).toList();

    }

    public TodoResponseDto getTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()
                -> new NullPointerException("존재하지 않습니다."));

        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto updateTodo(Long id, TodoRequestDto requestDto, User user) {
        Todo todo = todoRepository.findById(id).orElseThrow(()
                -> new NullPointerException("존재하지 않습니다."));

        if (!todo.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalStateException("작성자만 수정할 수 있습니다.");
        }

        todo.update(requestDto);
        return new TodoResponseDto(todo);
    }


    @Transactional
    public void deleteTodo(Long id, User user) {
        Todo todo = todoRepository.findById(id).orElseThrow(()
                -> new NullPointerException("존재하지 않습니다."));

        if (!todo.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalStateException("작성자만 삭제할 수 있습니다.");
        }

        todoRepository.delete(todo);
    }

    @Transactional
    public boolean complateTodo(Long id, boolean isCompleted, User user) {
        Todo todo = todoRepository.findById(id).orElseThrow(()
                -> new NullPointerException("존재하지 않습니다."));

        if (!todo.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalStateException("작성자만 완료/취소할 수 있습니다.");
        }

        todo.setCompleted(isCompleted);
        return todo.isCompleted();
    }


}
