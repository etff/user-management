package com.fastlane.usermanagement.infra;

import com.fastlane.usermanagement.domain.User;
import com.fastlane.usermanagement.global.model.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

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
    public void updatePassword(Long userId, String password) {
        jdbcTemplate.update("UPDATE users SET password=? WHERE id=?", password, userId);
    }

    @Override
    public Optional<User> findById(Id<User, Long> userId) {
        List<User> results = jdbcTemplate.query("SELECT * FROM users WHERE id=?",
                mapper,
                userId.value());
        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public void deleteById(Long userId) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", userId);
    }

    static RowMapper<User> mapper = (rs, rowNum) -> User.builder()
            .id(rs.getLong("id"))
            .password(rs.getString("password"))
            .build();
}
