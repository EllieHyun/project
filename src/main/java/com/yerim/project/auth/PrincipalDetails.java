package com.yerim.project.auth;

import com.yerim.project.entity.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@ToString
public class PrincipalDetails implements UserDetails {

    private final User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    /*
    해당 유저의 권한 목록 리턴
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) () -> user.getRole().toString());

        return collect;
    }

    /*
    비밀번호를 리턴
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /*
    PK값 반환
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /*
    계정 만료 여부
    true : 만료 안됨
    false : 만료됨
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
    계정 잠김 여부
    true : 잠기지 않음
    false : 잠김
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
    계정 비밀번호 만료 여부
    true : 만료 안됨
    false : 만료됨
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*
    계정 활성화 여부
    true : 활성화됨
    false : 활성화 안됨
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
