package com.hospital.hospital_community.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ArticleAddResponse {
    private Long id;
    private String title;
    private String content;
}
