package com.hospital.hospital_community.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HospitalReviewAppException extends RuntimeException{
    private ErrorCode errorCode;
    private String message;

    @Override
    public String toString() {
        if(this.message == null){
            return errorCode.getMessage();
        } else {
            return String.format("%s. %s", errorCode.getMessage(), this.message);
        }
    }
}
