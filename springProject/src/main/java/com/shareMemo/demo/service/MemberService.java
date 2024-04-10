package com.shareMemo.demo.service;

import com.shareMemo.demo.domain.dto.JoinRequest;
import com.shareMemo.demo.domain.dto.KakaoUserDto;
import com.shareMemo.demo.domain.dto.LoginRequest;
import com.shareMemo.demo.domain.dto.MemberInfoDto;
import com.shareMemo.demo.domain.entity.Member;

public interface MemberService {

    public void join(JoinRequest request);
    public Member login(LoginRequest request);
    public Member kakaoLogin(KakaoUserDto kakaoUserDto);

    public MemberInfoDto getLoginMember(Integer memberId);
    public boolean checkLoginIdDuplicate(String loginId);
    public boolean checkEmailDuplicate(String email);

}
