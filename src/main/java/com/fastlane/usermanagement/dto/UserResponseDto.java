package com.fastlane.usermanagement.dto;

import com.fastlane.usermanagement.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserResponseDto {
    private Long userId;

    public UserResponseDto(Long userId) {
        this.userId = userId;
    }

    public UserResponseDto(User user) {
        this.userId = user.getId();
    }
}
