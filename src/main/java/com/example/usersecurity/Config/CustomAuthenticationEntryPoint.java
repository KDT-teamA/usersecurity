package com.example.usersecurity.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.rmi.ServerException;

//로그인을 실패했을 때
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    //HttpServlet 클라이언트 컴퓨터의 정보를 읽어온다.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServerException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 실패");
    }
}
