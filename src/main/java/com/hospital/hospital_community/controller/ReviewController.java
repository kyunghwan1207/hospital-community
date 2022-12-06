package com.hospital.hospital_community.controller;

import com.hospital.hospital_community.domain.dto.ReviewCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    @PostMapping("/write")
    public String write(@RequestBody ReviewCreateRequest requestDto, Authentication authentication){
        System.out.println("authentication = " + authentication);
        System.out.println("authentication.getName() = " + authentication.getName());
        return String.format("authentication.getName() = %s", authentication.getName());
    }
}
