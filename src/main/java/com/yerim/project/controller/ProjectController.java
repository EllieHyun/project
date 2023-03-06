package com.yerim.project.controller;

import com.yerim.project.auth.PrincipalDetails;
import com.yerim.project.dto.PostCreateDto;
import com.yerim.project.dto.PostUpdateDto;
import com.yerim.project.dto.UserJoinDto;
import com.yerim.project.dto.UserLoginDto;
import com.yerim.project.entity.Post;
import com.yerim.project.entity.User;
import com.yerim.project.exception.PostNotFoundException;
import com.yerim.project.service.PostService;
import com.yerim.project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final UserService userService;
    private final PostService postService;


    @GetMapping("/login")
    public String getLogin(@RequestParam(defaultValue = "false") String error, Model model) {
        // TODO failureHandler에서 던지는 예외에 따라 error message 다르게 보여주기
        log.info("login error = " + error);
        model.addAttribute("error", error);
        model.addAttribute("loginDto", new UserLoginDto());
        return "login";
    }

    @GetMapping("/join")
    public String getJoin(Model model) {
        model.addAttribute("userJoinDto", new UserJoinDto());
        return "join";
    }

    @PostMapping("/join")
    public String postJoin(@ModelAttribute UserJoinDto userJoinDto) {
        userService.create(userJoinDto);
        return "redirect:/login";
    }

    @GetMapping("/")
    public String getHome(Authentication authentication, Model model) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        log.info(user.toString());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("posts", postService.findAllByOrderByCreateAtDesc());
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

    @GetMapping("/post")
    public String getPost(@RequestParam Long id, Model model) throws PostNotFoundException {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/create")
    public String getCreate(Model model) {
        model.addAttribute("postCreateDto", new PostCreateDto());
        return "create";
    }

    @PostMapping("/create")
    public String postCrate(Authentication authentication, @ModelAttribute PostCreateDto postCreateDto) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();   // 현재 로그인한 유저
        log.info("title : " + postCreateDto.getTitle());
        log.info("text : " + postCreateDto.getText());
        postService.create(postCreateDto, user);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String getUpdate(@RequestParam Long id, Model model) throws PostNotFoundException {
        PostUpdateDto postUpdateDto = new PostUpdateDto();
        Post post = postService.findById(id);
        postUpdateDto.setId(post.getId());
        postUpdateDto.setTitle(post.getTitle());
        postUpdateDto.setText(post.getText());
        model.addAttribute("postUpdateDto", postUpdateDto);
        return "update";
    }

    @PostMapping("/update")
    public String postUpdate(@ModelAttribute PostUpdateDto postUpdateDto) throws PostNotFoundException {
        log.info(postUpdateDto.getTitle());
        log.info(postUpdateDto.getText());

        postService.update(postUpdateDto);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String postDelete(@ModelAttribute Post post) {
        postService.delete(post);
        return "redirect:/";
    }
}
