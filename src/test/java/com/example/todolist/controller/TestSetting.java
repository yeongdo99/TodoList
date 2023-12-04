package com.example.todolist.controller;

import com.example.todolist.security.UserDetailsImpl;
import com.example.todolist.user.dto.SignupRequestDto;
import com.example.todolist.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest
public class TestSetting {

    protected MockMvc mvc;

    protected Principal mockPrincipal;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSecurityFilter()))
                .alwaysDo(print())
                .build();
    }

    protected  void mockUserSetup() {
        // Mock 테스트 유져 생성
        String username = "yeongdo11";
        String password = "zxc123@";
        User testUser = new User(new SignupRequestDto(username, password));
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }

}
