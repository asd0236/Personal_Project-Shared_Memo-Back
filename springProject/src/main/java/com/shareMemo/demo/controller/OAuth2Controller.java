package com.shareMemo.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shareMemo.demo.domain.entity.Member;
import com.shareMemo.demo.service.KakaoLoginService;
import com.shareMemo.demo.service.SessionLoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/login/oauth2/code")
public class OAuth2Controller {

    private final SessionLoginService sessionLoginService;
    private final KakaoLoginService kakaoLoginService;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @GetMapping(value = "/kakao")
    @ResponseBody
    public RedirectView kakaoLogin(@RequestParam String code, HttpServletRequest httpServletRequest) throws JsonProcessingException {

        log.info("code : " + code);

        // 액세스 토큰 발급 받기
        String oAuth2Token = kakaoLoginService.requestToken(code, clientId, clientSecret);

        // 발급받은 토큰으로 회원 정보 가져오기
        Member currentMember = kakaoLoginService.requestMemberInfo(oAuth2Token);

        // 세션 생성
        sessionLoginService.sessionLogin(currentMember, httpServletRequest);

        // 메인 페이지로 리디렉션
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:5000/mainPage.html");

        return redirectView;
    }
}
