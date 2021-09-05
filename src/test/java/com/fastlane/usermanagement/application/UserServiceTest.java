package com.fastlane.usermanagement.application;

import com.fastlane.usermanagement.application.exception.UserNotFoundException;
import com.fastlane.usermanagement.domain.User;
import com.fastlane.usermanagement.dto.UserResponseDto;
import com.fastlane.usermanagement.global.model.Id;
import com.fastlane.usermanagement.infra.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static final long GIVEN_USER_ID = 1L;
    private static final String GIVEN_PASSWORD = "1234";

    @Mock
    private UserRepository userRepository;

    private User user;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
        user = new User(GIVEN_USER_ID, GIVEN_PASSWORD);
    }

    @Test
    void registerUser() {
        // given
        given(userRepository.save(any(User.class)))
                .willReturn(user);

        // when
        UserResponseDto actual = userService.registerUser(GIVEN_PASSWORD);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    void get_existed_user() {
        // given
        given(userRepository.findById(Id.of(User.class, GIVEN_USER_ID)))
                .willReturn(Optional.of(user));

        // when
        UserResponseDto actual = userService.getUser(GIVEN_USER_ID);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    void get_not_existed_user() {
        // when
        assertThatThrownBy(() -> {
            userService.getUser(GIVEN_USER_ID);
        }).isInstanceOf(UserNotFoundException.class);
    }
}
