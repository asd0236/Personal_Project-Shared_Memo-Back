package com.shareMemo.demo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareMemo.demo.domain.dto.KakaoUserDto;
import com.shareMemo.demo.domain.entity.Member;
import com.shareMemo.demo.service.KakaoLoginService;
import com.shareMemo.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginServiceImpl implements KakaoLoginService {

    private final MemberService memberService;

    public String requestToken(String code, String clientId, String clientSecret) throws JsonProcessingException {
        // 카카오에 POST 방식으로 key=value 데이터를 요청함.
        // RestTemplate 을 사용하면 요청을 편하게 보낼 수 있다.
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

        // HttpHeader 와 HttpBody 를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // 오브젝트를 담아 Http POST 로 요청하기
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token", // 요청 url
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class // 응답받을 타입
        );

        log.info(String.valueOf(response));

        // json 형태로 받은 응답을 자바의 object 형태로 파싱 후 access token 만 가져와 리턴
        // Gson, Json Simple, ObjectMapper 등 사용 가능
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readTree(response.getBody()).get("access_token").asText();
    }

    @Override
    public Member requestMemberInfo(String token) throws JsonProcessingException {
        // 사용자 정보를 받아오기 위한 또 다른 RestTemplate를 사용해서 응답 받기
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders httpHeaders = new HttpHeaders();
        //Content-type 을 HttpHeader에 담는다는 것은 내가 담을 데이터가 key-value 데이터라고 알려주는 것이다
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        httpHeaders.add("Authorization","Bearer "+ token);

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

        ObjectMapper objectMapper = new ObjectMapper();

        KakaoUserDto kakaoUserDto = new KakaoUserDto(objectMapper.readTree(responseEntity2.getBody()).get("id").asLong(),
                objectMapper.readTree(responseEntity2.getBody()).get("properties").get("nickname").asText());


        return memberService.kakaoLogin(kakaoUserDto);
    }
}
