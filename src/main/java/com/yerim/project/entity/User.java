package com.yerim.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "provider")
    private String provider;   // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지

    @Column(name = "provider_id")
    private String providerId;   // oauth2를 이용할 경우 id값

    @Builder(builderClassName = "UserDetailRegister", builderMethodName = "userDetailRegister")
    public User(String email, String username, String password, Role role, LocalDateTime lastLoginAt) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.lastLoginAt = lastLoginAt;
    }

    @Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
    public User(String email, String username, String password, Role role, LocalDateTime lastLoginAt, String provider, String providerId) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.lastLoginAt = lastLoginAt;
        this.provider = provider;
        this.providerId = providerId;
    }
}