package com.hospital.hospital_community.repository;

import com.hospital.hospital_community.domain.dto.UserDto;
import com.hospital.hospital_community.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
