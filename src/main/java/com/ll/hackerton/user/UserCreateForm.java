package com.ll.hackerton.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수 항목입니다.")
    @Email
    private String email;

    @NotEmpty(message = "연락처는 필수 항목입니다.")
    @Size(min = 10, max = 15, message = "연락처는 10자 이상 15자 이하로 입력해 주세요.")
    private String contactNumber;  // 연락처

    @NotEmpty
    private String role;
}
