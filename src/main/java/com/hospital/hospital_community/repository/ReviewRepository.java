package com.hospital.hospital_community.repository;


import com.hospital.hospital_community.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
