package com.hospital.hospital_community.service;

import com.hospital.hospital_community.domain.Role;
import com.hospital.hospital_community.domain.dto.UserDto;
import com.hospital.hospital_community.domain.dto.UserJoinRequest;
import com.hospital.hospital_community.domain.entity.ErrorCode;
import com.hospital.hospital_community.domain.entity.HospitalReviewAppException;
import com.hospital.hospital_community.domain.entity.User;
import com.hospital.hospital_community.domain.utils.JwtUtil;
import com.hospital.hospital_community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.token.secret}")
    private String secretKey;

    private long expiredTimeMs = 1000 * 60 * 60; // 1시간

    public UserDto join(UserJoinRequest request){
        // 회원 이메일 중복check
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_EMAIL, "해당 이메일은 이미 가입되어있습니다!");
                });
        Role role = Role.ROLE_USER;
        if(request.getEmail().startsWith("admin")){
            role = Role.ROLE_ADMIN;
        }
        // ifPresent()를 넘어왔기 때문에, 회원가입 진행 .save()
        User savedUser = userRepository.save(request.toEntity(bCryptPasswordEncoder.encode(request.getPassword()), role));

        return UserDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .role(savedUser.getRole())
                .build();
    }

    public String login(String email, String password) {
        // email 있는지 여부 확인
        User findUser = userRepository.findByEmail(email).orElseThrow(
                () -> new HospitalReviewAppException(ErrorCode.USER_NOTFOUND, String.format("%s는 존재하지 않는 이메일입니다.", email))
                );

        // password 일치 여부 확인
        if(!bCryptPasswordEncoder.matches(password, findUser.getPassword())){
            throw new HospitalReviewAppException(ErrorCode.INVALID_PASSWORD, "이메일 또는 비밀번호를 다시 확인해주시기 바랍니다");
        }
        // 두가지 확인 과정에서 예외 안났으면 Token 발행
        String encordedPw = bCryptPasswordEncoder.encode(password);
        return JwtUtil.createToken(findUser.getEmail(), secretKey, expiredTimeMs);
    }

    public Boolean checkCanJoin(String email) {
        Optional<User> optUser = userRepository.findByEmail(email);
        optUser.ifPresent(user -> {
            throw new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_EMAIL, String.format("[Except]%s는 이미 존재하는 이메일입니다.", email));
        });
        return true;
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).get();
    }
}
