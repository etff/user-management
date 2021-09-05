package com.fastlane.usermanagement.application;

import com.fastlane.usermanagement.domain.User;
import com.fastlane.usermanagement.dto.UserResponseDto;
import com.fastlane.usermanagement.infra.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    private User user;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
        user = new User(1L, "1234");
    }

    @Test
    void registerUser() {
        String givenPassword = "1234";
        given(userRepository.save(any(User.class)))
                .willReturn(user);

        UserResponseDto actual = userService.registerUser(givenPassword);

        assertThat(actual).isNotNull();
    }
}
