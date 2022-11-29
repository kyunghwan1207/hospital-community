package com.hospital.hospital_community.service;

import com.hospital.hospital_community.domain.dto.ReviewCreateRequest;
import com.hospital.hospital_community.domain.dto.ReviewCreateResponse;
import com.hospital.hospital_community.domain.entity.Hospital;
import com.hospital.hospital_community.domain.entity.Review;
import com.hospital.hospital_community.repository.HospitalRepository;
import com.hospital.hospital_community.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final HospitalRepository hospitalRepository;
    private final ReviewRepository reviewRepository;
    public ReviewCreateResponse createReview(Long hospitalId, ReviewCreateRequest requestDto){
        Optional<Hospital> hospitalOptional = hospitalRepository.findById(hospitalId);
        Review review = Review.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .patientName(requestDto.getPatientName())
                .hospital(hospitalOptional.get())
                .build();
        Review savedReview = reviewRepository.save(review);
        return ReviewCreateResponse.builder()
                .patientName(savedReview.getPatientName())
                .reviewId(savedReview.getId())
                .content(savedReview.getContent())
                .message("리뷰가 정상적으로 등록되었습니다.")
                .title(savedReview.getTitle())
                .build();
    }
}
