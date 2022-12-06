package com.hospital.hospital_community.domain.dto;

import com.hospital.hospital_community.domain.Role;
import lombok.*;

import javax.persistence.Enumerated;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;
}
