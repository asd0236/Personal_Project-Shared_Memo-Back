package com.shareMemo.demo.service;

import com.shareMemo.demo.domain.dto.JoinRequest;
import com.shareMemo.demo.domain.entity.Member;

public interface MemberService {

    public void join(JoinRequest request);

    public Member getLoginMember(Integer memberId);
    public boolean checkLoginIdDuplicate(String loginId);
    public boolean checkNicknameDuplicate(String nickname);

}
