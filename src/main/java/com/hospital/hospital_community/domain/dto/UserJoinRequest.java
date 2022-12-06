package com.hospital.hospital_community.domain.dto;

import com.hospital.hospital_community.domain.Role;
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

    public User toEntity(String encryptedPassword, Role role) {
        return User.builder()
                .username(this.username)
                .email(this.email)
                .password(encryptedPassword)
                .role(role)
                .build();
    }
}
