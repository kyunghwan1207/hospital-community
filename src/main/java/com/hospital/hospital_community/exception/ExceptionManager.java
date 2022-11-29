package com.hospital.hospital_community.exception;

import com.hospital.hospital_community.domain.Response;
import com.hospital.hospital_community.domain.entity.HospitalReviewAppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error(exception.getMessage()));
    }
    @ExceptionHandler(HospitalReviewAppException.class)
    public ResponseEntity<?> hospitalReviewExceptionHandler(HospitalReviewAppException exception){
        return ResponseEntity.status(exception.getErrorCode().getStatus())
                .body(exception.getErrorCode().getMessage());
    }
}
