package com.yerim.project.service;

import com.yerim.project.entity.User;

public interface UserService {

    User findUserByEmail(String email);
    void save(User user);
}
