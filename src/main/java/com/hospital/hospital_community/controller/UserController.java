package com.hospital.hospital_community.controller;

import com.hospital.hospital_community.domain.Response;
import com.hospital.hospital_community.domain.dto.UserDto;
import com.hospital.hospital_community.domain.dto.UserJoinRequest;
import com.hospital.hospital_community.domain.dto.UserJoinResponse;
import com.hospital.hospital_community.domain.entity.User;
import com.hospital.hospital_community.repository.UserRepository;
import com.hospital.hospital_community.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest){
        UserDto userDto = userService.join(userJoinRequest);
        return Response.success(new UserJoinResponse(userDto.getUsername(), userDto.getEmail()));
    }
}
