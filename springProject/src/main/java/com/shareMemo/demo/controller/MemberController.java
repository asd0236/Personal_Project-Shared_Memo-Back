package com.shareMemo.demo.controller;

import com.shareMemo.demo.domain.entity.Member;
import com.shareMemo.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private MemberRepository memberRepository;

    @PostMapping("/sign-in")
    public void signIn(@RequestBody Member member){

    }

}
