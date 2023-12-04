package com.example.todolist.comment.service;

import com.example.todolist.comment.dto.CommentRequestDto;
import com.example.todolist.comment.dto.CommentResponseDto;
import com.example.todolist.comment.entity.Comment;
import com.example.todolist.comment.repository.CommentRepository;
import com.example.todolist.todo.entity.Todo;
import com.example.todolist.todo.repository.TodoRepository;
import com.example.todolist.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;

    public CommentResponseDto createComment(Long todoId, CommentRequestDto requestDto, User user) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(()
                -> new NullPointerException("게시글이 존재하지 않습니다."));

        Comment comment = commentRepository.save(new Comment(requestDto, user, todo));
        return new CommentResponseDto(comment);
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new NullPointerException("댓글이 존재하지 않습니다."));

        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalStateException("작성자만 수정할 수 있습니다.");
        }

        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }


    public ResponseEntity<?> deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new NullPointerException("댓글이 존재하지 않습니다."));

        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("작성자만 수정할 수 있습니다.");
        }

        commentRepository.delete(comment);
        return ResponseEntity.status(HttpStatus.OK).body("댓글 삭제 성공");
    }
}
