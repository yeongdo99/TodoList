package com.example.todolist.entity;

import com.example.todolist.comment.dto.CommentRequestDto;
import com.example.todolist.comment.entity.Comment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentTest {
    Comment comment = new Comment();

    @Test
    void commentEntityTest() {
        // When
        comment.setContent("댓글 테스트");

        // Then
        assertEquals("댓글 테스트", comment.getContent());
    }

    @Test
    void commentUpdateTest(){
        // Given
        CommentRequestDto requestDto = new CommentRequestDto("댓글 수정");

        // When
        comment.update(requestDto);

        // Then
        assertEquals("댓글 수정", comment.getContent());
    }
}
