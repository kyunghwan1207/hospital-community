package com.hospital.hospital_community.controller;

import com.hospital.hospital_community.domain.dto.ArticleDto;
import com.hospital.hospital_community.domain.entity.Article;
import com.hospital.hospital_community.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("")
    public String allArticles(Model model){
        List<Article> list = articleRepository.findAll();
        model.addAttribute("articles", list);
        return "articles/all";
    }
    @GetMapping("/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @GetMapping("/{id}")
    public String selectSingle(@PathVariable Long id, Model model){
        Optional<Article> articleOptional = articleRepository.findById(id);
        if(!articleOptional.isEmpty()){
            model.addAttribute("article", articleOptional.get());
            return "articles/detail";
        } else{
            log.info("selectSingle()/articleOpt is Empty!");
            return "error";
        }
    }

    @PostMapping("/posts")
    public String createArticle(ArticleDto articleDto){
        log.info(articleDto.toString());
        Article article = articleDto.toEntity();
        Article savedArticle = articleRepository.save(article);
        log.info("savedArticle's Id: " + savedArticle.getId());
        // 생성 완료하고 나면 detail페이지로 이동
        return String.format("redirect:/articles/%d", savedArticle.getId());
    }
}
