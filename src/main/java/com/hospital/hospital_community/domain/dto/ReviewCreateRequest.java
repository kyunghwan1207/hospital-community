package com.hospital.hospital_community.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateRequest {
    private Long reviewId;
    private String title;
    private String content;
    private String patientName;

}
