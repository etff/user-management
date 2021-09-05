package com.fastlane.usermanagement.infra;

import com.fastlane.usermanagement.domain.User;
import com.fastlane.usermanagement.global.model.Id;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    void update(User user);

    Optional<User> findById(Id<User, Long> userId);

    void deleteById(Long userId);
}
