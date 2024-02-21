package com.shareMemo.demo.controller;

import com.shareMemo.demo.domain.dto.JoinRequest;
import com.shareMemo.demo.domain.dto.LoginRequest;
import com.shareMemo.demo.domain.entity.Member;
import com.shareMemo.demo.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/session-login")
public class SessionLoginController {

    private final MemberService memberService;

    @GetMapping(value = {"", "/"}) // 다른 페이지에 있을 때 로그인 상태가 아니면 메인 페이지로
    public String home(Model model, @SessionAttribute(name = "memberId", required = false) Integer memberId) {
        // 공통 화면 사용을 위해 모든 요청에 아래 코드 추가
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        Member loginMember = memberService.getLoginMember(memberId);

        if (loginMember != null) {
            model.addAttribute("nickname", loginMember.getNickname());
        }

        return "mainPage";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        model.addAttribute("joinRequest", new JoinRequest());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequest joinRequest, BindingResult bindingResult, Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        // LoginId 중복 체크
        if (memberService.checkLoginIdDuplicate(joinRequest.getLoginId()))
            bindingResult.addError(new FieldError("joinRequest",
                    "loginId", "로그인 아이디가 중복됩니다."));

        // 닉네임 중복 체크
        if (memberService.checkNicknameDuplicate(joinRequest.getNickname()))
            bindingResult.addError(new FieldError("joinRequest",
                    "nickName", "닉네임이 중복됩니다."));

        // password와 passwordCheck가 같은지 체크
        if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck()))
            bindingResult.addError(new FieldError("joinRequest",
                    "passwordCheck", "비밀번호가 일치하지 않습니다."));

        if (bindingResult.hasErrors())
            return "join";

        memberService.join(joinRequest);
        return "redirect:/loginPage";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "    세션 로그인");

        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, BindingResult bindingResult,
                        HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        Member member = memberService.login(loginRequest);

        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if (member == null)
            bindingResult.reject("loginFail", "로그인 아이디 또는 비밀번호가 틀렸습니다.");

        if(bindingResult.hasErrors())
            return "login";

        // 로그인 성공 => 세션 생성

        // 세션을 생성하기 전에 기존의 세션 파기
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true); // Session이 없으면 생성
        // 세션에 memberId를 넣어줌
        session.setAttribute("memberId", member.getMemberId());
        session.setMaxInactiveInterval(1800); // Session이 30분동안 유지

        return "redirect:/session-login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model){
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        HttpSession session = request.getSession(false); // Session이 없으면 null return
        if(session != null)
            session.invalidate();

        return "redirect:/session-login";
    }

    @GetMapping("/info")
    public String memberInfo(@SessionAttribute(name = "userId", required = false) Integer memberId, Model model){
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        Member loginMember = memberService.getLoginMember(memberId);

        if(loginMember == null)
            return "redirect:/session-login/login";

        model.addAttribute("member", loginMember);
        return "info";
    }

}
