package com.fastlane.usermanagement.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * 사용자.
 */
@Getter
public class User {
    private Long id;
    private String password;

    public User(String password) {
        this(null, password);
    }

    @Builder
    public User(Long id, String password) {
        this.id = id;
        this.password = password;
    }
}
