package com.yerim.project.service;

import com.yerim.project.dto.UserJoinDto;
import com.yerim.project.entity.Role;
import com.yerim.project.entity.User;
import com.yerim.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Transactional
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void create(UserJoinDto userJoinDto) {
        User newUser = User.userDetailRegister()
                .email(userJoinDto.getEmail())
                .username(userJoinDto.getUsername())
                .password(passwordEncoder.encode(userJoinDto.getPassword()))
                .role(Role.ROLE_USER)
                .lastLoginAt(null)
                .build();
        save(newUser);
    }
}
