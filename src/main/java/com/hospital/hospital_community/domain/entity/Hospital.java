package com.hospital.hospital_community.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Hospital {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hospitalName;
    private String roadAddress;

    @OneToMany(mappedBy = "hospital")
    private List<Review> reviews = new ArrayList<>();
}
