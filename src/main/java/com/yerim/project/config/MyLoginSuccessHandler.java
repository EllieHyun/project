package com.yerim.project.config;

import com.yerim.project.auth.PrincipalDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("login success handler");
        HttpSession httpSession = request.getSession();
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        String username = principal.getUser().getUsername();
        httpSession.setAttribute("sessionMessage", "Hello " + username);
        response.sendRedirect("/");
    }
}
