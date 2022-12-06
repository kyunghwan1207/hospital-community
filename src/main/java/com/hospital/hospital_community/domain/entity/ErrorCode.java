package com.hospital.hospital_community.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATED_USER_EMAIL(HttpStatus.CONFLICT, "User email is duplicated."),
    USER_NOTFOUND(HttpStatus.NOT_FOUND, "User cannot found"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "User password is invalided" );

    private HttpStatus status;
    private String message;
}
