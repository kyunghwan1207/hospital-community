package com.hospital.hospital_community.controller;

import com.hospital.hospital_community.domain.entity.Hospital;
import com.hospital.hospital_community.service.HospitalService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/hospitals")
@Slf4j
public class HospitalController {
    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("")
    public String index(Model model){
        List<Hospital> hospitalList = hospitalService.findAll();
        model.addAttribute("hospitals", hospitalList);
        return "hospitals/index";
    }
}
