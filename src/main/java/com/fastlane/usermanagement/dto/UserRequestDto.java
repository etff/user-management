package com.fastlane.usermanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class UserRequestDto {

    @NotBlank
    private String password;

    public UserRequestDto(String password) {
        this.password = password;
    }
}
