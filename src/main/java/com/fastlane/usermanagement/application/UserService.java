package com.fastlane.usermanagement.application;

import com.fastlane.usermanagement.domain.User;
import com.fastlane.usermanagement.dto.UserResponseDto;
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
}
