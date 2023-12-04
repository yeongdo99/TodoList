package com.example.todolist.service;

import com.example.todolist.comment.dto.CommentRequestDto;
import com.example.todolist.comment.dto.CommentResponseDto;
import com.example.todolist.comment.entity.Comment;
import com.example.todolist.comment.repository.CommentRepository;
import com.example.todolist.comment.service.CommentService;
import com.example.todolist.todo.dto.TodoRequestDto;
import com.example.todolist.todo.entity.Todo;
import com.example.todolist.todo.repository.TodoRepository;
import com.example.todolist.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    TodoRepository todoRepository;

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentService commentService;

    User user;
    Todo todo;
    Comment comment;

    TodoRequestDto requestDto;
    CommentRequestDto commentRequestDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        requestDto = new TodoRequestDto("제목 테스트", "내용 테스트");
        todo = new Todo(requestDto, user);

        commentRequestDto = new CommentRequestDto("댓글 테스트");
        comment = new Comment(commentRequestDto, user, todo);
    }


    @Test
    @DisplayName("댓글 작성 성공")
    void createCommentTest() {
        // given
        given(todoRepository.findById(1L)).willReturn(Optional.of(todo));
        given(commentRepository.save(any(Comment.class))).willReturn(comment);

        // when
        CommentResponseDto responseDto = commentService.createComment(1L, commentRequestDto, user);

        // then
        assertEquals("댓글 테스트", responseDto.getContent());
    }

    @Test
    @DisplayName("댓글 수정 성공")
    void updateCommentTest() {
        // given
        Long commentId = 1L;
        CommentRequestDto requestDto = new CommentRequestDto("댓글 수정");
        given(commentRepository.save(any(Comment.class))).willReturn(comment);

        // when
        CommentResponseDto responseDto = commentService.updateComment(1L, requestDto, user);

        assertEquals("댓글 수정", responseDto.getContent());
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void deleteCommentTest() {
        // given
        Long CommentId = 1L;

        given(commentRepository.save(any(Comment.class))).willReturn(comment);

        // when
        commentService.deleteComment(1L, user);

        // then
        verify(commentRepository).delete(any(Comment.class));

    }



}
