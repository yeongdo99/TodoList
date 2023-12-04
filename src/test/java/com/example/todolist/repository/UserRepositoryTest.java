package com.example.todolist.repository;

import com.example.todolist.user.dto.SignupRequestDto;
import com.example.todolist.user.entity.User;
import com.example.todolist.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findByUsernameTest() {
        // given
        User user = new User(new SignupRequestDto("test user", "password"));
        userRepository.save(user);

        // when
        Optional<User> findUser = userRepository.findByUsername("test user");

        // then
        assertNotNull(findUser);

    }
}
