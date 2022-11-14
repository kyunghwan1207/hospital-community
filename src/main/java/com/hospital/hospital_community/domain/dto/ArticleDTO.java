package com.hospital.hospital_community.domain.dto;

import com.hospital.hospital_community.domain.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;

    public Article toEntity(){
        return new Article(this.id, this.title, this.content);
    }
}
