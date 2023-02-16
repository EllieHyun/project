package com.yerim.project.controller;

import com.yerim.project.dto.LoginDto;
import com.yerim.project.entity.User;
import com.yerim.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private ProjectService projectService;

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @PostMapping("/login")
    public void postLogin(@ModelAttribute LoginDto loginDto) {
        log.info(loginDto.getEmail());
        log.info(loginDto.getPassword());
    }
}
