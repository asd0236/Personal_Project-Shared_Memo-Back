package com.shareMemo.demo.service.impl;

import com.shareMemo.demo.domain.dto.JoinRequest;
import com.shareMemo.demo.domain.dto.LoginRequest;
import com.shareMemo.demo.domain.dto.MemberInfoDto;
import com.shareMemo.demo.domain.entity.Member;
import com.shareMemo.demo.repository.MemberRepository;
import com.shareMemo.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public void join(JoinRequest request) {
        // Password 암호화
        String encodedPassword = encoder.encode(request.getPassword());
        List<String> roles = new ArrayList<>();
        roles.add("USER"); // USER 권한 부여
        memberRepository.save(request.toEntity(encodedPassword, roles));
}

    // LoginRequest(loginId, password)를 입력받아 loginId에 대응되는 password가 일치하면 Member return
    // loginId가 존재하지 않거나 password가 일치하지 않으면 null return
    @Override
    public Member login(LoginRequest request) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(request.getLoginId());

        // loginId와 일치하는 Member가 없으면 null return
        if (optionalMember.isEmpty()) return null;

        // 찾아온 Member의 password와 입력된 password가 다르면 null return
        Member member = optionalMember.get();
        // 인코딩 되지 않은 패스워드와 인코딩 된 패스워드를 비교하여 일치하면 true
        if (!encoder.matches(request.getPassword(), member.getPassword())) return null;

        return member;
    }

    // memberId를 입력받아 member를 return
    // 인증, 인가 시 사용
    @Override
    public MemberInfoDto getLoginMember(Integer memberId) {
        if (memberId == null) return MemberInfoDto.setNull();

        Optional<Member> optionalMember = memberRepository.findById(memberId);
        // memberId가 null이거나(로그인 x) memberId로 찾아온 member가 없으면 null return
        if(optionalMember.isEmpty()) return MemberInfoDto.setNull();

        return MemberInfoDto.toDto(optionalMember.get());

    }

    // loginIn 중복 체크, 중복 시 true return
    @Override
    public boolean checkLoginIdDuplicate(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    // nickname 중복 체크, 중복 시 true return
    @Override
    public boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }


}
