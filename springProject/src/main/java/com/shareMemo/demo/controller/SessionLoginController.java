package com.shareMemo.demo.controller;

import com.shareMemo.demo.domain.dto.JoinRequest;
import com.shareMemo.demo.domain.dto.MemberInfoDto;
import com.shareMemo.demo.domain.dto.SignResponse;
import com.shareMemo.demo.domain.dto.LoginRequest;
import com.shareMemo.demo.domain.entity.Member;
import com.shareMemo.demo.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/session-login")
public class SessionLoginController {

    private final MemberService memberService;

    @PostMapping("/join")
    @ResponseBody
    public SignResponse join(@Valid @ModelAttribute JoinRequest joinRequest) {
        // LoginId 중복 체크
        if (memberService.checkLoginIdDuplicate(joinRequest.getLoginId()))
            return new SignResponse("이미 존재하는 아이디입니다.", false);
        // 이메일 중복 체크
        if (memberService.checkEmailDuplicate(joinRequest.getEmail()))
            return new SignResponse("이미 존재하는 이메일입니다.", false);
        // password와 passwordCheck가 같은지 체크
        if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck()))
            return new SignResponse("비밀번호가 일치하지 않습니다.", false);


        // 회원 가입 성공 로직
        memberService.join(joinRequest);
        return new SignResponse("회원가입에 성공하였습니다.", true);
    }

    @PostMapping("/login")
    @ResponseBody
    public SignResponse login(@ModelAttribute LoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        Member member = memberService.login(loginRequest);

        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if (member == null)
            return new SignResponse("로그인 아이디 또는 비밀번호가 틀렸습니다.", false);

        // 로그인 성공 => 세션 생성

        // 세션을 생성하기 전에 기존의 세션 파기
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true); // Session이 없으면 생성
        // 세션에 memberId를 넣어줌
        session.setAttribute("memberId", member.getMemberId());
        session.setMaxInactiveInterval(1800); // Session이 30분동안 유지


        // 세션 id와 저장된 객체 정보 출력
        System.out.println(session.getId() + ", " + session.getAttribute("memberId"));

        //세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

        log.info("sessionId={}", session.getId());
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval());
        log.info("creationTime={}", new Date(session.getCreationTime()));
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
        log.info("isNew={}", session.isNew());

        return new SignResponse("로그인에 성공하였습니다.", true);
    }

    @GetMapping("/logout")
    @ResponseBody
    public boolean logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false); // Session이 없으면 null return
        if (session != null) {
            session.invalidate();
            return true;
        }

        return false;
    }

    @GetMapping("/info")
    @ResponseBody
    public MemberInfoDto memberInfo(@SessionAttribute(name = "memberId", required = false) Integer memberId) {

        MemberInfoDto loginMember = memberService.getLoginMember(memberId);
        log.info(String.valueOf(memberId));

        return loginMember;

    }

}
