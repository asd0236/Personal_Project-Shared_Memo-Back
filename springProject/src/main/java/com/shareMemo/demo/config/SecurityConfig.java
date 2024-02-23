package com.shareMemo.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // REST API 이므로 basic auth 및 csrf 보안을 사용하지 않음
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((authorizeRequests) -> {
                    authorizeRequests
                            // 해당 API에 대해서는 모든 요청을 허가
                            .requestMatchers("/session-login/**").permitAll()
                            // USER 권한이 있어야 요청할 수 있음
                            .requestMatchers("/**").permitAll()
                            // 이 밖에 모든 요청에 대해서 인증을 필요로 한다는 설정
                            .anyRequest().authenticated();
                }).build();


    }


}
