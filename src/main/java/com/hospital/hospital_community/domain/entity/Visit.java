package com.hospital.hospital_community.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Visit {
    @Id @GeneratedValue
    private Long id;

    private LocalDateTime createdAt;
    private Disease disease;
    private int cost;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "hsopital_id")
    private Hospital hospital;

}
