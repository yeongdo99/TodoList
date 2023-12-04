package com.example.todolist.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequestDto {
    @NotBlank(message = "아이디는 필수 입력값 입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디 4~10자")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
    @Pattern(regexp = "[a-zA-Z0-9]{8,15}$", message = "비밀번호 8~15자")
    private String password;
}
