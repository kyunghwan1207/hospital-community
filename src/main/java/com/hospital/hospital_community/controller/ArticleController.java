package com.hospital.hospital_community.controller;

import com.hospital.hospital_community.domain.dto.ArticleDTO;
import com.hospital.hospital_community.domain.entity.Article;
import com.hospital.hospital_community.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Article> articlePage = articleRepository.findAll(pageRequest);
        model.addAttribute("articles", articlePage);
        System.out.println("안녕");
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
    public String createArticle(ArticleDTO articleDto){
        log.info(articleDto.toString());
        Article article = articleDto.toEntity();
        Article savedArticle = articleRepository.save(article);
        log.info("savedArticle's Id: " + savedArticle.getId());
        // 생성 완료하고 나면 detail페이지로 이동
        return String.format("redirect:/articles/%d", savedArticle.getId());
    }
    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, ArticleDTO articleDto, Model model){
        log.info("post | title: {} content: {}", articleDto.getTitle(), articleDto.getContent());
        Article article = articleRepository.save(articleDto.toEntity()); // 이미 존재하는 경우 update 쿼리 나감
        log.info("id={}, savedArticle.getId()={}", id, article.getId());
        model.addAttribute("article", article);
        return String.format("redirect:/articles/%d", article.getId());
    }
    @GetMapping("/{id}/update")
    public String getUpdate(@PathVariable Long id, Model model){
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (!optionalArticle.isEmpty()){
            Article article = optionalArticle.get();
            log.info("get | title: {} content: {}", article.getTitle(), article.getContent());
            model.addAttribute("article", article);
            return "articles/edit";
        } else {
            return "/error";
        }
    }
    @GetMapping("/{id}/delete")
    public String getDelete(@PathVariable Long id, Model model){
        articleRepository.deleteById(id);
        model.addAttribute("deletedId", id);
        return "redirect:/articles/";
    }
}
