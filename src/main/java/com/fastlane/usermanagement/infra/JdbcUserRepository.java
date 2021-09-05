package com.fastlane.usermanagement.infra;

import com.fastlane.usermanagement.domain.User;
import com.fastlane.usermanagement.global.model.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users(id,password) VALUES (null,?)", new String[]{"id"});
            ps.setString(1, user.getPassword());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        long generatedId = key != null ? key.longValue() : -1;
        return User.builder().id(generatedId)
                .password(user.getPassword())
                .build();
    }

    @Override
    public void update(User user) {

    }

    @Override
    public Optional<User> findById(Id<User, Long> userId) {
        return Optional.empty();
    }
}
