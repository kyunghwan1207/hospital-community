package com.hospital.hospital_community.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.hospital_community.domain.dto.UserDto;
import com.hospital.hospital_community.domain.dto.UserJoinRequest;
import com.hospital.hospital_community.domain.dto.UserLoginRequest;
import com.hospital.hospital_community.domain.entity.ErrorCode;
import com.hospital.hospital_community.domain.entity.HospitalReviewAppException;
import com.hospital.hospital_community.exception.ExceptionManager;
import com.hospital.hospital_community.repository.UserRepository;
import com.hospital.hospital_community.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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
    @MockBean
    ExceptionManager exceptionManager;

    @Autowired
    ObjectMapper objectMapper;
    UserJoinRequest make_UserJoinRequest(String username, String password, String email){
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
        return userJoinRequest;
    }

    @Test
    @DisplayName("회원가입 성공")
    @WithMockUser
    void join_success() throws Exception{
        UserJoinRequest userJoinRequest = make_UserJoinRequest("ko3", "1234", "ko3@naver.com");
        when(userService.join(any())).thenReturn(mock(UserDto.class));
        mockMvc.perform(
                post("/api/v1/users/join")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userJoinRequest))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("회원가입 실패-email중복")
    @WithMockUser
    void join_fail() throws Exception {
        UserJoinRequest userJoinRequest = make_UserJoinRequest("ko1", "1234", "ko1@naver.com");
        when(userService.join(userJoinRequest)).thenThrow(
                new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_EMAIL, "해당 이메일은 이미 가입되어있습니다!"));
        mockMvc.perform(post("/api/v1/users/join")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isConflict()); // 409
    }
    @Test
    @DisplayName("로그인 성공")
    @WithMockUser
    void login_success() throws Exception {
        String email = "ko1@naver.com";
        String password = "1234";

        when(userService.login(any(), any()))
                .thenReturn("token");

        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(email, password)))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("로그인 실패 - email없음")
    @WithMockUser
    void login_fail1() throws Exception {
        String email = "ko1@naver.com";
        String password = "1234";

        when(userService.login(any(), any()))
                .thenThrow(new HospitalReviewAppException(ErrorCode.USER_NOTFOUND, String.format("%s 이메일은 존재하지 않습니다.", email)));


        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(email, password))))
                .andDo(print())
                .andExpect(status().isNotFound()); // 404
    }
    @Test
    @DisplayName("로그인 실패 - password틀림")
    @WithMockUser
    void login_fail2() throws Exception {
        String email = "ko1@naver.com";
        String password = "1234";

        when(userService.login(any(), any()))
                .thenThrow(new HospitalReviewAppException(ErrorCode.INVALID_PASSWORD, String.format("비밀번호가 일치하지 않습니다")));


        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(email, password))))
                .andDo(print())
                .andExpect(status().isUnauthorized()); // 401
    }

}