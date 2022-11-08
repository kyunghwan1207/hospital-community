package com.hospital.hospital_community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MustacheController {
    @GetMapping("/hello")
    public String mustacheCon(Model model){
        model.addAttribute("username", "ko"); // view에 값을 넘김
        return "greetings"; // view "greetings.mustache" return
    }
    @GetMapping("/hello/{id}")
    public String mustacheCon2(Model model, @PathVariable String id){
        model.addAttribute("username", "ko"); // view에 값을 넘김
        model.addAttribute("id", id);
        return "greetings"; // view "greetings.mustache" return
    }
}
