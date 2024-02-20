package com.shareMemo.demo.controller;

import com.shareMemo.demo.domain.dto.JoinRequest;
import com.shareMemo.demo.domain.entity.Member;
import com.shareMemo.demo.service.MemberService;
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

    MemberService memberService;

    @GetMapping(value = {"", "/"}) // 다른 페이지에 있을 때 로그인 상태가 아니면 메인 페이지로
    public String home(Model model, @SessionAttribute(name = "memberId", required = false) Integer memberId) {
        // 공통 화면 사용을 위해 모든 요청에 아래 코드 추가
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        Member loginMember = memberService.getLoginMember(memberId);

        if (loginMember != null) {
            model.addAttribute("nickname", loginMember.getNickName());
        }

        return "home";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        model.addAttribute("joinRequest", new JoinRequest());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequest joinRequest, BindingResult bindingResult, Model model){
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        // LoginId 중복 체크
        if(memberService.checkLoginIdDuplicate(joinRequest.getLoginId())){
            bindingResult.addError(new FieldError("joinRequest", "loginId", "로그인 아이디가 중복됩니다."));
        }
        // 닉네임 중복 체크
        if(memberService.checkNicknameDuplicate(joinRequest.getNickname())){
            bindingResult.addError(new FieldError("joinRequest", "nickName", "닉네임이 중복됩니다."));
        }
        // password와 passwordCheck가 같은지 체크
        if(!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())){
            bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "비밀번호가 일치하지 않습니다."));
        }

        if(bindingResult.hasErrors()){
            return "join";
        }

        memberService.join(joinRequest);
        return "redirect:/session-login";

    }

}
