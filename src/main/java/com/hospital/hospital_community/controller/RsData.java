package com.hospital.hospital_community.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RsData<T> {
    private String resultCode;
    private String msg;
    private T data;
}
