package com.hospital.hospital_community.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class EncryptPasswordConfig {
    @Bean
    public BCryptPasswordEncoder encodePw(){
        System.out.println("EncryptPasswordConfig.encodePw");
        return new BCryptPasswordEncoder(); // password 인코딩할 때 사용
    }
}