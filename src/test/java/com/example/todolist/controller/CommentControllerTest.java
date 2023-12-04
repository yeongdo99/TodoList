package com.example.todolist.controller;

import com.example.todolist.comment.dto.CommentRequestDto;
import com.example.todolist.comment.service.CommentService;
import com.example.todolist.config.WebSecurityConfig;
import com.example.todolist.test.MockSecurityFilter;
import com.example.todolist.test.TestSetting;
import com.example.todolist.todo.controller.TodoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {TodoController.class}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = WebSecurityConfig.class)
})
public class CommentControllerTest extends TestSetting {
    @MockBean
    CommentService commentService;

    @BeforeEach
    public void setup() {
        this.mockUserSetup();

        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSecurityFilter()))
                .alwaysDo(print())
                .build();
    }


    @Test
    @DisplayName("댓글 작성")
    void createCommentTest() throws Exception {
        // given
        Long todoId = 1L;
        CommentRequestDto requestDto = new CommentRequestDto("테스트 댓글");

        String commentInfo = objectMapper.writeValueAsString(requestDto);

        // when - then
        mvc.perform(post("/api/todo/{todoId}/comments", todoId)
                        .content(commentInfo)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 수정")
    void updateCommentTest() throws Exception {
        // given
        Long todoId = 1L;
        Long commentId = 1L;
        CommentRequestDto requestDto = new CommentRequestDto("댓글 수정");
        String commentInfo = objectMapper.writeValueAsString(requestDto);

        // when - then
        mvc.perform(put("/api/todo/{todoId}/comments/{commentId}", todoId, commentId)
                        .content(commentInfo)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteCommentTest() throws Exception{
        // given
        Long todoId = 1L;
        Long commentId = 1L;

        // when - then
        mvc.perform(delete("/api/todo/{todoId}/comments/{commentId}", todoId, commentId)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("댓글이 삭제되었습니다."))
                .andDo(print());
    }
    //


}
