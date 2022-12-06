package com.hospital.hospital_community.controller;

import com.hospital.hospital_community.domain.Response;
import com.hospital.hospital_community.domain.dto.*;
import com.hospital.hospital_community.domain.entity.ErrorCode;
import com.hospital.hospital_community.domain.entity.HospitalReviewAppException;
import com.hospital.hospital_community.domain.entity.User;
import com.hospital.hospital_community.repository.UserRepository;
import com.hospital.hospital_community.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {
    private final UserService userService;
    @GetMapping("/ping")
    public String testConnection(){
        return "pong";
    }

    @PostMapping("/join")
    public Response<?> join(@RequestBody UserJoinRequest userJoinRequest){
        UserDto userDto = userService.join(userJoinRequest);
        if(userDto != null){
            return Response.success(new UserJoinResponse(userDto.getUsername(), userDto.getEmail()));
        } else {
            return Response.error("회원가입에 실패했습니다");
        }
    }
    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        log.info(String.format(userLoginRequest.toString()));
        String token = userService.login(userLoginRequest.getEmail(), userLoginRequest.getPassword());
        return Response.success(new UserLoginResponse(token));
    }
    @GetMapping("/{email}")
    public Response checkCanJoin(@PathVariable String email){
        log.info(String.format(email + " is recived"));
        Boolean canJoin = userService.checkCanJoin(email);
        Map<String, String> body = new HashMap<>();
        if(canJoin){
            body.put("message", "가입 가능한 이메일입니다.");
            return Response.success(body);
        } else {
            body.put("message", "이미 가입된 이메일입니다.");
            return Response.error("이미 가입된 이메일입니다.");
        }
    }
}
