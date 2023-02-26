package com.yerim.project.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@Slf4j
public class FormLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(exception instanceof BadCredentialsException) {
            request.setAttribute("errorMessage", "사용자 정보자 존재하지 않습니다. 이메일 또는 비밀번호를 확인하세요.");
            //throw new BadCredentialsException("User Not Found");
            response.sendRedirect("/login?error=true");
        } else {
            response.sendRedirect("/login?error=true");
        }
    }
}
