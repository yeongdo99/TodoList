package com.example.todolist.todo.dto;

import com.example.todolist.todo.entity.Todo;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponseDto {
    private Long id;
    private String title;
    private String username;
    private String content;
    private Boolean isCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.username = todo.getUser().getUsername();
        this.content = todo.getContent();
        this.isCompleted = todo.isCompleted();
        this.createdAt = todo.getCreatedAt();
        this.modifiedAt = todo.getModifiedAt();

    }

}
