package com.hospital.hospital_community.repository;

import com.hospital.hospital_community.domain.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
