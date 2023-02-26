package com.yerim.project.auth;

import com.yerim.project.entity.Role;
import com.yerim.project.entity.User;
import com.yerim.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();   // 어떤 플랫폼을 이용하는지
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId;

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String password = passwordEncoder.encode("패스워드" + uuid);

        String email = oAuth2User.getAttribute("email");

        User userByEmail = userService.findUserByEmail(email);

        if(userByEmail == null) {
            User newUser = User.oauth2Register()
                    .email(email)
                    .username(username)   // TODO oauth2 계정에 등록된 이름 가져오기
                    .password(password)
                    .role(Role.ROLE_USER)
                    .lastLoginAt(LocalDateTime.now())
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userByEmail = userService.save(newUser);
        }

        return new PrincipalDetails(userByEmail);
    }
}
