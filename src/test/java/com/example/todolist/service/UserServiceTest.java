package com.example.todolist.service;

import com.example.todolist.user.dto.LoginRequestDto;
import com.example.todolist.user.dto.SignupRequestDto;
import com.example.todolist.user.entity.User;
import com.example.todolist.user.repository.UserRepository;
import com.example.todolist.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    SignupRequestDto signupRequestDto;
    LoginRequestDto loginRequestDto;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {

        signupRequestDto = new SignupRequestDto("user", "password");
        loginRequestDto = new LoginRequestDto("username", "password");
    }
    
    
    @Test
    @DisplayName("회원가입")
    void signup() {
        // given
        SignupRequestDto requestDto = new SignupRequestDto("yeongdo123", "zxc123@");

        given(userRepository.findByUsername("yeongdo123")).willReturn(Optional.empty());

        // when/then
        assertDoesNotThrow(() -> userService.signup(requestDto));
    }

    @Test
    @DisplayName("중복확인")
    void Duplication() {
        // given
        SignupRequestDto requestDto = new SignupRequestDto("yeongdo123", "zxc123@");

        given(userRepository.findByUsername("yeongdo123")).willReturn(Optional.of(new User("user1", "12345@@")));

        // when/then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.signup(requestDto));

        assertEquals("중복된 이름입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginTest() {
        // given
        User user = new User("username", "password");
        given(userRepository.findById("username")).willReturn(Optional.of(user));
        UserService userService1 = new UserService(userRepository, passwordEncoder);
        given(passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())).willReturn(true);

        // when
        boolean result = userService1.login(loginRequestDto);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("로그인 실패 / 중복회원")
    void loginDuplication() {
        // given
        User user = new User("username", "passworddd");
        given(userRepository.findById("username")).willReturn(Optional.of(user));
        UserService userService1 = new UserService(userRepository, passwordEncoder);

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService1.login(loginRequestDto);
        });

        // then
        assertEquals("비밀번호가 일치하지 않습니다.", exception.getMessage());
    }

}