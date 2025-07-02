package com.example.usersecurity.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    //암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //권한설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            //자원 공유(static) -> 자동으로 폴더 검색
            auth.requestMatchers("/assets/**", "/css/**", "/js/**").permitAll();

            //초기 개발시(모든 페이지 접근가능) -> 중기때는 삭제, Controller의 맵핑명
            auth.requestMatchers("/**").permitAll();
            //H2콘솔 접근
            auth.requestMatchers("/h2-console/**").permitAll();
            //개발중기 시 각 맵핑별 세부 권한 지정
            auth.requestMatchers("/", "/login", "/register", "/password").permitAll();
            auth.requestMatchers("/board/**", "/product/**").authenticated();
            auth.requestMatchers("/manager/**").hasAnyRole("ADMIN");
        });

        return http.build();
    }
}
