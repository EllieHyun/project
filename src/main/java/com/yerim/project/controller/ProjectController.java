package com.yerim.project.controller;

import com.yerim.project.dto.JoinDto;
import com.yerim.project.dto.LoginDto;
import com.yerim.project.entity.Role;
import com.yerim.project.entity.User;
import com.yerim.project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String getLogin(@RequestParam(defaultValue = "false") String error, Model model) {
        log.info("login error = " + error);
//        if(error.equals("true")) {
//            model.addAttribute("errorMessage", errorMessage);
//        }
        model.addAttribute("error", error);
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @PostMapping("/login")
    public void postLogin() {
    }

    @PostMapping("/loginProcessing")
    public void getLoginProcessing() {
        log.info("loginProcessing");
    }

    @GetMapping("/join")
    public String getJoin(Model model) {
        model.addAttribute("joinDto", new JoinDto());
        return "join";
    }

    @PostMapping("/join")
    public String postJoin(@ModelAttribute JoinDto joinDto) {
        log.info(joinDto.getUsername());
        log.info(joinDto.getEmail());
        log.info(joinDto.getPassword());
        User user = new User();
        user.setUsername(joinDto.getUsername());
        user.setPassword(passwordEncoder.encode(joinDto.getPassword()));
        user.setEmail(joinDto.getEmail());
        user.setRole(Role.ROLE_USER);
        user.setLastLoginAt(LocalDateTime.now());
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/")
    public String getHome() {
        log.info("home");
        return "home";
    }

    @GetMapping("/user/main")
    public void getUserMain() {
        log.info("getUserMain");
    }
}
