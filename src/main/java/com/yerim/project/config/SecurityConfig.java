package com.yerim.project.config;

import com.yerim.project.auth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                    .requestMatchers("/login", "/join", "/css/**", "/img/**")
                    .permitAll()
                    .requestMatchers( "/admin/**")
                    .hasRole("ROLE_ADMIN")
                    .anyRequest()
                    .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login?error=true")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login")
                    .successHandler(new FormLoginSuccessHandler())
                    .failureHandler(new FormLoginFailureHandler())
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                .and()
                    .oauth2Login()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login?error=true")
                    .successHandler(new OAuthLoginSuccessHandler())
                    .userInfoEndpoint()
                    .userService(principalOauth2UserService);
        return http.build();
    }
}
