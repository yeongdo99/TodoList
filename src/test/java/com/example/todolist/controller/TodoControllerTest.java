package com.example.todolist.controller;

import com.example.todolist.config.WebSecurityConfig;
import com.example.todolist.todo.service.TodoService;
import com.example.todolist.test.MockSecurityFilter;
import com.example.todolist.test.TestSetting;
import com.example.todolist.todo.controller.TodoController;
import com.example.todolist.todo.dto.TodoRequestDto;
import com.example.todolist.todo.dto.TodoResponseDto;
import com.example.todolist.todo.entity.Todo;
import com.example.todolist.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {TodoController.class}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                            classes = WebSecurityConfig.class)
})
@MockBean(JpaMetamodelMappingContext.class)
public class TodoControllerTest extends TestSetting {

    @MockBean
    private TodoService todoService;

    @BeforeEach
    public void setup() {
        this.mockUserSetup();

        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSecurityFilter()))
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("할 일 작성")
    void createTodo() throws Exception {
        // given
        TodoRequestDto requestDto = new TodoRequestDto("제목테스트", "내용테스트");
        String todoInfo = objectMapper.writeValueAsString(requestDto);


        // when - then
        mvc.perform(post("/api/todo")
                        .content(todoInfo)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("할 일 조회")
    void getTodoTest() throws Exception {
        // given
        Long todoId = 1L;
        TodoRequestDto requestDto = new TodoRequestDto("제목테스트", "내용테스트");
        User user = new User("test user", "password");
        Todo todo = new Todo(requestDto, user);
        TodoResponseDto responseDto = new TodoResponseDto(todo);

        given(todoService.getTodo(todoId)).willReturn(responseDto);

        // when - then
        mvc.perform(get("/api/todo/{todoId}", todoId).principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.content").value(responseDto.getContent()))
                .andExpect(jsonPath("$.username").value(responseDto.getUsername()))
                .andDo(print());
    }

    @Test
    @DisplayName("할 일 수정")
    void updateTodoTest() throws Exception {
        // given
        Long todoId = 1L;
        TodoRequestDto requestDto = new TodoRequestDto("제목 수정", "내용 수정");
        String todoInfo = objectMapper.writeValueAsString(requestDto);

        // when - then
        mvc.perform(put("/api/todo/{todoId}", todoId)
                        .content(todoInfo)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("할 일 삭제")
    void deleteTodoTest() throws Exception {
        // given
        Long todoId = 1L;

        // when - then
        mvc.perform(delete("/api/todo/{todoId}", todoId).principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(content().string("삭제 완료"))
                .andDo(print());
    }


}
