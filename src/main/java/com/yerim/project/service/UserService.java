package com.yerim.project.service;

import com.yerim.project.dto.UserJoinDto;
import com.yerim.project.entity.User;

public interface UserService {

    User findUserByEmail(String email);
    User save(User user);
    void create(UserJoinDto userJoinDto);
}
