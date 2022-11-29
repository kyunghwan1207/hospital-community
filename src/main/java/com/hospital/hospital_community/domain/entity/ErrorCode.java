package com.hospital.hospital_community.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATED_USER_EMAIL(HttpStatus.CONFLICT, "User email is duplicated.");
    private HttpStatus status;
    private String message;
}
