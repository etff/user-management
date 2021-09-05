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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
    void getExistedUser() {
        // given
        given(userRepository.findById(Id.of(User.class, GIVEN_USER_ID)))
                .willReturn(Optional.of(user));

        // when
        UserResponseDto actual = userService.getUser(GIVEN_USER_ID);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    void getNotExistedUser() {
        // when
        assertThatThrownBy(() -> {
            userService.getUser(GIVEN_USER_ID);
        }).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void deleteUser() {
        // when
        userService.deleteUser(anyLong());

        verify(userRepository).deleteById(anyLong());
    }

    @Test
    void updateExistUserPassword() {
        // given
        given(userRepository.findById(Id.of(User.class, GIVEN_USER_ID)))
                .willReturn(Optional.of(user));

        // when
        userService.updatePassword(GIVEN_USER_ID, anyString());

        // then
        verify(userRepository).updatePassword(anyLong(), anyString());
    }

    @Test
    void updateNotExistUserPassword() {
        // when
        assertThatThrownBy(() -> {
            userService.updatePassword(GIVEN_USER_ID, anyString());
        }).isInstanceOf(UserNotFoundException.class);
    }
}
