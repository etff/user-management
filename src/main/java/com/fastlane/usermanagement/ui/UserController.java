package com.fastlane.usermanagement.ui;

import com.fastlane.usermanagement.application.UserService;
import com.fastlane.usermanagement.dto.UserRequestDto;
import com.fastlane.usermanagement.dto.UserResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@Api(tags = "사용자 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "사용자 가입")
    @PostMapping
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody @Valid UserRequestDto dto) {
        UserResponseDto responseDto = userService.registerUser(dto.getPassword());
        return ResponseEntity.created(URI.create("/users/" + responseDto.getUserId())).body(responseDto);
    }

    @ApiOperation(value = "사용자 조회")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId) {
        UserResponseDto responseDto = userService.getUser(userId);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation(value = "사용자 삭제")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "사용자 비밀반호 수정")
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUserPassword(@PathVariable Long userId, @RequestBody UserRequestDto dto) {
        userService.updatePassword(userId, dto.getPassword());
        return ResponseEntity.ok().build();
    }
}
