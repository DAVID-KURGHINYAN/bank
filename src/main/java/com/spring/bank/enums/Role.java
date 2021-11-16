package com.spring.bank.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN,
    USER;

    public Set<SimpleGrantedAuthority> getAuthorities(){
        Set<SimpleGrantedAuthority> roles = new HashSet<>();
        roles.stream().map(role -> new SimpleGrantedAuthority(ADMIN.name()))
                .collect(Collectors.toSet());
        return roles;
    }
}

