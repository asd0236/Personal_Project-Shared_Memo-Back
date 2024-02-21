package com.shareMemo.demo.controller;

import com.shareMemo.demo.domain.entity.Member;
import com.shareMemo.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class MemberController {

    private MemberRepository memberRepository;

    @GetMapping
    public String test(){
        return "hello world";
    }

}
