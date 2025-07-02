package com.example.usersecurity.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
//인증 성공 시 사용자 환경으로 변경처리
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    //인증 성공처리 메소드를 수정
    //request(요청) - 로그인 -> response(응답)
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, SecurityException {
        //로그인에 사용된 아이디(username)을 읽는다.
        UserDetails user = (UserDetails) authentication.getPrincipal();

        response.sendRedirect("/");
    }
}
