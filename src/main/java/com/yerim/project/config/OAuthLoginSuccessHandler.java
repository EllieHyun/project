package com.yerim.project.config;

import com.yerim.project.auth.PrincipalDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("oauth login success handler");
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        principal.getUser().setLastLoginAt(LocalDateTime.now());   // 마지막 로그인 날짜 update
        response.sendRedirect("/");
    }
}
