package com.shareMemo.demo.domain.dto;

import com.shareMemo.demo.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MemberInfoDto {
    private Integer memberId;
    private String loginId;
    private String email;
    private String nickname;

    static public MemberInfoDto toDto(Member member){
        return MemberInfoDto.builder()
                .memberId(member.getMemberId())
                .loginId(member.getLoginId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }

    static public MemberInfoDto setNull(){
        return MemberInfoDto.builder()
                .memberId(null)
                .loginId(null)
                .email(null)
                .nickname(null)
                .build();
    }
}
