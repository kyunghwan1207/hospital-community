package com.hospital.hospital_community.domain.dto;

import com.hospital.hospital_community.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCreateResponse {
    private Long reviewId;
    private String title;
    private String content;
    private String patientName;
    private String message;

//    public ReviewCreateResponse(ReviewCreateRequest reviewCreateRequest){
//        reviewCreateRequest.
//    }
}
