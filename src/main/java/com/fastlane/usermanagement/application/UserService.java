package com.fastlane.usermanagement.application;

import com.fastlane.usermanagement.application.exception.UserNotFoundException;
import com.fastlane.usermanagement.domain.User;
import com.fastlane.usermanagement.dto.UserResponseDto;
import com.fastlane.usermanagement.global.model.Id;
import com.fastlane.usermanagement.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto registerUser(String password) {
        User user = new User(password);
        User saved = userRepository.save(user);

        return new UserResponseDto(saved);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long userId) {
        final User user = findUser(userId);
        return new UserResponseDto(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void updatePassword(Long userId, String password) {
        findUser(userId);
        userRepository.updatePassword(userId, password);
    }

    private User findUser(Long userId) {
        return userRepository.findById(Id.of(User.class, userId))
                .orElseThrow(UserNotFoundException::new);
    }
}
