package com.example.ShareBlog.domain.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "Guest"),
    ADMIN("ROLE_ADMIN", "Admin");

    private final String key;
    private final String title;
}
