package com.yerim.project.auth;

import com.yerim.project.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
public class PrincipalDetails implements UserDetails, OAuth2User {

    private final User user;
    private Map<String, Object> attributes;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    /*
    OAuth2 Login
     */
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /*
    Form Login
    해당 유저의 권한 목록 리턴
    */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) () -> user.getRole().toString());

        return collect;
    }

    /*
    Form Login
    비밀번호를 리턴
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /*
    Form Login
    PK값 반환
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /*
    Form Login
    계정 만료 여부
    true : 만료 안됨
    false : 만료됨
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
    Form Login
    계정 잠김 여부
    true : 잠기지 않음
    false : 잠김
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
    Form Login
    계정 비밀번호 만료 여부
    true : 만료 안됨
    false : 만료됨
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*
    Form Login
    계정 활성화 여부
    true : 활성화됨
    false : 활성화 안됨
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /*
    OAuth2 Login
     */
    @Override
    public String getName() {
        return attributes.get("sub").toString();
    }
}
