package com.example.todolist.comment.controller;


import com.example.todolist.comment.dto.CommentRequestDto;
import com.example.todolist.comment.dto.CommentResponseDto;
import com.example.todolist.comment.service.CommentService;
import com.example.todolist.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{todoId}")
    public CommentResponseDto createComment(@PathVariable Long todoId, @RequestBody @Valid CommentRequestDto requestDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return commentService.createComment(todoId, requestDto, userDetails.getUser());
    }

    @PutMapping("/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long commentId, @RequestBody @Valid CommentRequestDto requestDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(commentId, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails.getUser());
        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }
}
