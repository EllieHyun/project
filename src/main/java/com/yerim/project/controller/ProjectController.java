package com.yerim.project.controller;

import com.yerim.project.auth.PrincipalDetails;
import com.yerim.project.dto.JoinDto;
import com.yerim.project.dto.LoginDto;
import com.yerim.project.entity.Role;
import com.yerim.project.entity.User;
import com.yerim.project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String getLogin(@RequestParam(defaultValue = "false") String error, Model model) {
        // TODO failureHandler에서 던지는 예외에 따라 error message 다르게 보여주기
        log.info("login error = " + error);
        model.addAttribute("error", error);
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @GetMapping("/join")
    public String getJoin(Model model) {
        model.addAttribute("joinDto", new JoinDto());
        return "join";
    }

    @PostMapping("/join")
    public String postJoin(@ModelAttribute JoinDto joinDto) {
        User newUser = User.userDetailRegister()
                .email(joinDto.getEmail())
                .username(joinDto.getUsername())
                .password(passwordEncoder.encode(joinDto.getPassword()))
                .role(Role.ROLE_USER)
                .lastLoginAt(null)
                .build();
        userService.save(newUser);
        return "redirect:/login";
    }

    @GetMapping("/")
    public String getHome(Authentication authentication, Model model) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        log.info(user.toString());
        model.addAttribute("username", user.getUsername());
        return "home";
    }

    @ResponseBody
    @GetMapping("/form/userinfo")
    public String getFormUserInfo(Authentication authentication, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("get form userinfo");

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        log.info(user.toString());

        User user1 = principalDetails.getUser();
        log.info(user1.toString());

        return user.toString();
    }

    @ResponseBody
    @GetMapping("/oauth/userinfo")
    public String getOauthUserInfo(Authentication authentication) {
        log.info("get oauth userinfo");

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();

        return user.toString();
    }
}
