package com.shareMemo.demo.domain.dto;

import com.shareMemo.demo.domain.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
    private String passwordCheck;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    public Member toEntity(String encodedPassword){
        return Member.builder()
                .loginId(loginId)
                .password(this.password)
                .email(this.email)
                .nickname(this.nickname)
                .password(encodedPassword)
                .build();
    }
}
