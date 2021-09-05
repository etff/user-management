package com.fastlane.usermanagement.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @DisplayName("생성자로 입력받은 사용자 정보를 리턴합니다.")
    @Test
    void create() {
        final String givenPassword = "1234";
        final Long givenId = 1L;
        final User user = new User(givenId, givenPassword);

        assertThat(user.getId()).isEqualTo(givenId);
        assertThat(user.getPassword()).isEqualTo(givenPassword);
    }
}
