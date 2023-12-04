package com.example.todolist.controller;


import com.example.todolist.test.MockSecurityFilter;
import com.example.todolist.test.TestSetting;
import com.example.todolist.user.dto.SignupRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends TestSetting {

    @BeforeEach
    public void setup() {
        this.mockUserSetup();

        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSecurityFilter()))
                .alwaysDo(print())
                .build();
    }



    @Test
    @DisplayName("회원가입 테스트")
    void signup() throws Exception {
        SignupRequestDto requestDto = new SignupRequestDto("username", "password");

        mvc.perform(post("/api/user/signup")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception {
        // given
        this.mockUserSetup();

        // when, then
        mvc.perform(delete("/api/user")
                .principal(mockPrincipal)
        ).andExpect(status().isNoContent());
    }
}
