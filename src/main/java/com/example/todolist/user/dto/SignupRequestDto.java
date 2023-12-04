package com.example.todolist.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequestDto {
    @NotBlank(message = "아이디는 필수 입력값 입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{4,12}", message = "아이디 4~12자 영문의 소문자, 숫자를 입력하세요.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,15}", message = "비밀번호 8~15자 영문의 대 소문자, 숫자와 특수문자를 입력하세요.")
    private String password;
}
