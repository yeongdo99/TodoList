package com.example.todolist.entity;

import com.example.todolist.todo.dto.TodoRequestDto;
import com.example.todolist.todo.entity.Todo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {

    Todo todo = new Todo();

    @Test
    void todoEntityTest() {
        // when
        todo.setTitle("제목 테스트");
        todo.setContent("내용 테스트");

        // then
        assertEquals("제목 테스트", todo.getTitle());
        assertEquals("내용 테스트", todo.getContent());
    }

    @Test
    void feedUpdateTest(){
        // Given
        TodoRequestDto requestDto = new TodoRequestDto("제목 수정", "내용 수정");

        // When
        todo.update(requestDto);

        // Then
        assertEquals("제목 수정", todo.getTitle());
        assertEquals("내용 수정", todo.getContent());
    }

}
