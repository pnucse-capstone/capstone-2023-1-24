package com.c9Pay.userservice.security.userdetails;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 사용자 정보를 나타내는 UserDetails 구현 클래스
 */
public class CustomUserDetails implements UserDetails {
    private final String id;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * 생성자로 사용자 정보와 권한 정보를 받아서 최기화한다.
     *
     * @param id          사용자의 아이디
     * @param password    사용자의 암호화된 비밀번호
     * @param authorities 사용자의 권한 목록
     */
    public CustomUserDetails(String id, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
