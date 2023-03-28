package ru.rndev.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    JOUR,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }

}
