package com.shareMemo.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shareMemo.demo.domain.entity.Member;

public interface KakaoLoginService {
    public String requestToken(String code, String clientId, String clientSecret) throws JsonProcessingException;
    public Member requestMemberInfo(String token) throws JsonProcessingException;
}
