package com.hospital.hospital_community.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.annotation.processing.Generated;
import javax.persistence.*;

@Entity
@Getter
@Table(name = "article")
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue() // AUTO_INC를 DB에 맡기겠다
    private Long id;

    @Column
    private String title;
    @Column
    private String content;
}
