package com.example.todolist.service;

import com.example.todolist.todo.dto.TodoRequestDto;
import com.example.todolist.todo.dto.TodoResponseDto;
import com.example.todolist.todo.entity.Todo;
import com.example.todolist.todo.repository.TodoRepository;
import com.example.todolist.todo.service.TodoService;
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
class TodoServiceTest {

    @InjectMocks
    TodoService todoService;

    @Mock
    TodoRepository todoRepository;



    User user;
    Todo todo;
    TodoRequestDto requestDto;

    @BeforeEach
    void setUp() {
        user = new User();

        requestDto = new TodoRequestDto("제목 테스트", "내용 테스트");
        todo = new Todo (requestDto, user);
    }


    @Test
    @DisplayName("할 일 생성")
    void createTodo() {
        // given
        given(todoRepository.save(any(Todo.class))).willReturn(todo);

        // when
        TodoResponseDto responseDto =  todoService.createTodo(requestDto, user);

        // then
        assertEquals("제목 테스트", responseDto.getTitle());
        assertEquals(responseDto.getContent(), requestDto.getContent());
    }
    
    @Test
    @DisplayName("선택 할 일 조회")
    void getIdTodo() {
        // given
        Long todoId = 1L;
        given(todoRepository.findById(todoId)).willReturn(Optional.of(todo));

        // when
        TodoResponseDto responseDto =todoService.getTodo(todoId);

        // then
        assertEquals("제목 테스트", responseDto.getTitle());
        assertEquals("내용 테스트", responseDto.getContent());
    }

    @Test
    @DisplayName("할일 수정 - 성공")
    void updateTodo() {
        // given
        Long todoId = 1L;
        User user = new User("username", "password");
        Todo todo = new Todo(requestDto, user);
        TodoRequestDto updateTodo = new TodoRequestDto("제목수정 테스트", "내용수정 테스트");
        given(todoRepository.findById(todoId)).willReturn(Optional.of(todo));

        // when
        TodoResponseDto responseDto = todoService.updateTodo(todoId, updateTodo, user);

        // then
        assertEquals(responseDto.getTitle(), updateTodo.getTitle());
        assertEquals(responseDto.getContent(), updateTodo.getContent());
    }

//    @Test
//    @DisplayName("할일 수정 - 실패 / 작성자가 아닐 때")
//    void updateTodo2() {
//        // given
//        Long todoId = 1L;
//        Todo todo = new Todo(requestDto, user);
//        given(todoRepository.findById(todoId)).willReturn(Optional.of(todo));
//
//        // when
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            todoService.updateTodo(todoId, requestDto, user);
//        });
//
//        // then
//        assertEquals("작성자만 수정 가능", exception.getMessage());
//    }

    @Test
    @DisplayName("삭제 성공 테스트")
    void deleteTodo() {
        // given
        Long todoId = 1L;
        User user = new User("username", "password");
        Todo todo = new Todo(requestDto, user);
        given(todoRepository.findById(todoId)).willReturn(Optional.of(todo));

        // when
        todoService.deleteTodo(todoId, user);

        // then
        verify(todoRepository).delete(any(Todo.class));
    }


    @Test
    @DisplayName("완료 테스트")
    void complateTodo() {
        // given
        Long todoId = 1L;
        User user = new User("username", "password");
        Todo todo = new Todo(new TodoRequestDto("제목테스트", "내용 테스트"), user);

        given(todoRepository.findById(todoId)).willReturn(Optional.of(todo));

        // when
        todoService.complateTodo(todoId, true, user);

        // then
        assertEquals(todo.isCompleted(), true);
    }


}