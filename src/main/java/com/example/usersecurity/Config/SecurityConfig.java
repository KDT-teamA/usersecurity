package com.example.usersecurity.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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

        //h2 로그인 이후
        http.headers((headers) -> headers.frameOptions().sameOrigin());

        //로그인 설정 (username(변경가능), password(변경불가) 구성)
        http.formLogin(login -> login
                .loginPage("/login") //사용자가 만든 로그인 페이지의 맵핑명
                .defaultSuccessUrl("/") //로그인 성공시 이동할 맵핑명
                .usernameParameter("userid") //로그인 폼에서 아이디에 해당하는 name명 (기본 username 자동인식)
                .permitAll() //로그인 폼에 접근권한
                .successHandler(new CustomAuthenticationSuccessHandler()) //로그인 성공 후 처리할 클래스
        ); //로그인 폼에 대한 설정

        //csrf 변조방지
        http.csrf(AbstractHttpConfigurer::disable);

        //로그아웃 처리
        http.logout(logout -> logout
                .logoutUrl("/logout") //로그아웃 맵핑명
                .logoutSuccessUrl("/login") //로그아웃 성공 시
        );

        return http.build();
    }
}
