package com.kdigital.factoryPick.config;

import java.util.Collection;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.kdigital.factoryPick.entity.UserEntity;

public class CustomOauth2UserDetails implements OAuth2User {

    private final UserEntity user;
    private final Map<String, Object> attributes;
    private final String principalName;

    public CustomOauth2UserDetails(UserEntity user, Map<String, Object> attributes, String principalName) {
        this.user = user;
        this.attributes = attributes;
        this.principalName = principalName;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 역할을 권한으로 반환
        return Collections.singletonList(new SimpleGrantedAuthority(user.getUserRole()));
    }

    @Override
    public String getName() {
        return principalName;
    }

    public UserEntity getUser() {
        return user;
    }
}
