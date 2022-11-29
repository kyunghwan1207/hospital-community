package com.hospital.hospital_community.domain.dto;

import com.hospital.hospital_community.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserJoinRequest {
    private String username;
    private String password;
    private String email;

    public User toEntity() {
        return User.builder()
                .username(this.username)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
