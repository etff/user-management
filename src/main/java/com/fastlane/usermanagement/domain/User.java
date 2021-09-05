package com.fastlane.usermanagement.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

/**
 * 사용자.
 */
@Getter
public class User {

    @ApiModelProperty(value = "PK", required = true)
    private Long id;

    @ApiModelProperty(hidden = true)
    private String password;

    public User(String password) {
        this(null, password);
    }

    @Builder
    public User(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPassword());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
