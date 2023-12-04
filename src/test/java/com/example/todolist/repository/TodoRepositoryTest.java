package com.example.todolist.repository;


import com.example.todolist.todo.dto.TodoRequestDto;
import com.example.todolist.todo.entity.Todo;
import com.example.todolist.todo.repository.TodoRepository;
import com.example.todolist.user.entity.User;
import com.example.todolist.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TodoRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TodoRepository todoRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = new User("test user", "password");
        userRepository.save(user);
    }

    @Test
    @DisplayName("내림차순 조회")
    void findAllByOrderByCreatedAtDesc() {
        // given
        User user = new User("username", "password");
        userRepository.save(user);
        TodoRequestDto requestDto = new TodoRequestDto("제목 테스트", "내용 테스트");
        Todo todo1 = new Todo(requestDto, user);
        Todo todo2 = new Todo(requestDto, user);

        todoRepository.saveAll(List.of(todo1, todo2));

        // when
        List<Todo> todoList = todoRepository.findAllByOrderByCreatedAtDesc();

        // then
        Assertions.assertThat(todoList).hasSize(2);
        Assertions.assertThat(todoList.get(0).getTitle()).isEqualTo("제목 테스트");
    }

    @Test
    @DisplayName("제목으로 조회")
    void findTodoByTitleContaining() {
        // given
        User user = new User("username", "password");
        userRepository.save(user);
        TodoRequestDto requestDto = new TodoRequestDto("제목 테스트", "내용 테스트");
        Todo todo1 = new Todo(requestDto, user);
        Todo todo2 = new Todo(requestDto, user);
        Todo todo3 = new Todo(requestDto, user);
        todoRepository.saveAll(List.of(todo1, todo2, todo3));

        // when
        List<Todo> todoList = todoRepository.findByTitleOrderByCreatedAtDesc("제목 테스트");

        // then
        assertThat(todoList).containsExactly(todo1, todo2, todo3);

    }


}
