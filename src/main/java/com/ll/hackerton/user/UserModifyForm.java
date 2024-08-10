package com.ll.hackerton.user;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자ID는 최소 3자 최대 25자입니다.")
    private String username;

    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 10, max = 15, message = "연락처는 10자 이상 15자 이하로 입력해 주세요.")
    private String contactNumber;  // 연락처
}
