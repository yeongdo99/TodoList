package com.example.todolist.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoRequestDto {
    @NotBlank
    String title;
    @NotBlank
    String content;
}
