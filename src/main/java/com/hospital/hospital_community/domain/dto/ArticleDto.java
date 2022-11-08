package com.hospital.hospital_community.domain.dto;

import com.hospital.hospital_community.domain.entity.Article;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArticleDto {
    private Long id;
    private String title;
    private String content;

    public ArticleDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    public Article toEntity(){
        return new Article(this.id, this.title, this.content);
    }
}
