package com.shareMemo.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareMemo.demo.domain.dto.KakaoUserDto;
import com.shareMemo.demo.domain.dto.SignResponse;
import com.shareMemo.demo.domain.entity.Member;
import com.shareMemo.demo.service.MemberService;
import com.shareMemo.demo.service.SessionLoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/login/oauth2/code")
public class OAuth2Controller {

    private final MemberService memberService;
    private final SessionLoginService sessionLoginService;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @GetMapping(value = "/kakao")
    @ResponseBody
    public SignResponse kakaoLogin(@RequestParam String code, HttpServletRequest httpServletRequest) throws JsonProcessingException {

        log.info("code : " + code);

        /*
                    액세스 토큰 발급 받기
        */

        // 카카오에 POST 방식으로 key=value 데이터를 요청함.
        // RestTemplate를 사용하면 요청을 편하게 보낼 수 있다.
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type",
                "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", "http://localhost:8080/login/oauth2/code/kakao");
        params.add("code", code);
        params.add("client_secret", clientSecret);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // 오브젝트를 담아 Http POST로 요청하기
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token", // 요청 url
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class // 응답받을 타입
        );

        log.info(String.valueOf(response));


        /*
                    json으로 오는 응답 받기
         */

        // Gson, Json Simple, ObjectMapper 등 사용 가능
        ObjectMapper objectMapper = new ObjectMapper();
        String oAuth2Token = objectMapper.readTree(response.getBody()).get("access_token").asText();


        // 사용자 정보를 받아오기 위한 또 다른 RestTemplate를 사용해서 응답 받기
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders httpHeaders = new HttpHeaders();
        //Content-type 을 HttpHeader에 담는다는 것은 내가 담을 데이터가 key-value 데이터라고 알려주는 것이다
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        httpHeaders.add("Authorization","Bearer "+ oAuth2Token);

        //httpBody 생성 부분
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(httpHeaders);
        //Http 요청하기 - POST방식 그리고 response 변수의 응답을 받음
        ResponseEntity<String> responseEntity2 = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",  //요청 주소
                HttpMethod.POST,    //요청방법
                kakaoProfileRequest,  //넘기는 데이터
                String.class    //받아올 데이터 타입
        );

        log.info(String.valueOf(responseEntity2));


        KakaoUserDto kakaoUserDto = new KakaoUserDto(objectMapper.readTree(responseEntity2.getBody()).get("id").asLong(),
                objectMapper.readTree(responseEntity2.getBody()).get("properties").get("nickname").asText());


        Member currentMember = memberService.kakaoLogin(kakaoUserDto);

        sessionLoginService.sessionLogin(currentMember, httpServletRequest);


        return new SignResponse("로그인에 성공하였습니다.", true);
    }
}
