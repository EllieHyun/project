package com.yerim.project.auth;

import com.yerim.project.entity.User;
import com.yerim.project.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserService userService;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userByEmail = userService.findUserByEmail(email);
        if(userByEmail == null) throw new UsernameNotFoundException("User not found");
        else {
            userByEmail.setLastLoginAt(LocalDateTime.now());
            return new PrincipalDetails(userByEmail);
        }
    }
}
