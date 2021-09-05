package com.fastlane.usermanagement.ui;

import com.fastlane.usermanagement.application.UserService;
import com.fastlane.usermanagement.dto.UserRequestDto;
import com.fastlane.usermanagement.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody @Valid UserRequestDto dto) {
        UserResponseDto responseDto = userService.registerUser(dto.getPassword());

        return ResponseEntity.created(URI.create("/users/" + responseDto.getUserId())).build();
    }
}
