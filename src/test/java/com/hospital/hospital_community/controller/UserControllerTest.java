package com.hospital.hospital_community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.hospital_community.domain.dto.UserDto;
import com.hospital.hospital_community.domain.dto.UserJoinRequest;
import com.hospital.hospital_community.domain.entity.ErrorCode;
import com.hospital.hospital_community.domain.entity.HospitalReviewAppException;
import com.hospital.hospital_community.repository.UserRepository;
import com.hospital.hospital_community.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;
    @MockBean
    UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 성공")
    void join_success() throws Exception{
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .username("ko3")
                .email("ko3@naver.com")
                .password("1234")
                .build();
        when(userService.join(any())).thenReturn(mock(UserDto.class));
        mockMvc.perform(
                post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userJoinRequest))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("회원가입 실패")
    void join_fail() throws Exception {
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .username("ko3")
                .email("ko3@naver.com")
                .password("1234")
                .build();
        when(userService.join(userJoinRequest)).thenThrow(
                new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_EMAIL, "해당 이메일은 이미 가입되어있습니다!"));
        mockMvc.perform(post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isConflict());
    }


}