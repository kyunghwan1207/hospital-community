package com.hospital.hospital_community.service;

import com.hospital.hospital_community.domain.dto.UserDto;
import com.hospital.hospital_community.domain.dto.UserJoinRequest;
import com.hospital.hospital_community.domain.entity.ErrorCode;
import com.hospital.hospital_community.domain.entity.HospitalReviewAppException;
import com.hospital.hospital_community.domain.entity.User;
import com.hospital.hospital_community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserDto join(UserJoinRequest request){
        // 회원 이메일 중복check
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_EMAIL, "해당 이메일은 이미 가입되어있습니다!");
                });

        // ifPresent()를 넘어왔기 때문에, 회원가입 진행 .save()
        User savedUser = userRepository.save(request.toEntity());
        return UserDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .build();
    }
}
