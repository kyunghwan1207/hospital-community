package com.hospital.hospital_community.controller;

import com.hospital.hospital_community.domain.dto.ReviewCreateRequest;
import com.hospital.hospital_community.domain.dto.ReviewCreateResponse;
import com.hospital.hospital_community.domain.entity.Hospital;
import com.hospital.hospital_community.service.HospitalService;
import com.hospital.hospital_community.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/v1/hospitals")
@Slf4j
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity showHospitalList(){
        List<Hospital> hospitals = hospitalService.findAll();
        return ResponseEntity.ok().body(hospitals);
    }
//    @GetMapping("")
//    public String index(Model model,
//                        @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
//                        Pageable pageable){
//        Long startTime = System.currentTimeMillis();
//        // 병원정보를 10개씩, id 기준 오름차순으로 찾아옴
//        Page<Hospital> hospitalList = hospitalService.findWithPage(pageable);
//
//        int sizePerPage = hospitalList.getSize(); // 10 (한 페이지에 들어갈 hostpiatl 정보 수)
//        Long totalElements = hospitalList.getTotalElements(); // 111919 (총 row 수)
//        List<Hospital> hospitalContents = hospitalList.getContent();
//        int totalPages = hospitalList.getTotalPages(); // 11192 (총 page 수)
//        int dividedPage = Math.floorDiv(pageable.getPageNumber()+1, sizePerPage);
//        int offset = sizePerPage * dividedPage; // 0 -> 10 -> 20 ...
//        List<Map> list = new ArrayList<>(); // 사용자들이 선택할 수 있는 10개의 page num을 넣을 것임
//        for (int i = offset; i < sizePerPage + offset; i++) {
//            Map<String, Integer> map = new HashMap<>();
//            // Mustache에서 num이라는 이름으로 변수 i 값에 접근할 수 있도록 하기 위함, 만약에 new 를 하지 않는다면 맨 마지막 값(sizePerPage + offset - 1)만 쌓임
//            if (i >= hospitalList.getTotalPages()){
//                // 나타낼 수 있는 총 페이지 수 넘어가면 break
//                break;
//            }
//            map.put("num", i);
//            list.add(map);
//        }
//        int next = offset + sizePerPage; // "다음" 버튼 눌렀을 때 넘어갈 페이지 번호
//        if (next >= hospitalList.getTotalPages()){
//            next = hospitalList.getTotalPages() - 1;
//        }
//        int previous = offset - sizePerPage; // "이전" 버튼 눌렀을 때 넘어갈 페이지 번호
//        if (previous < 0){
//            previous = 0;
//        }
//
//        model.addAttribute("hospitals", hospitalList);
//        model.addAttribute("first", 0); // "맨 앞"으로 이동하기 위한 page num
//        model.addAttribute("last", hospitalList.getTotalPages()-1); // "맨 뒤"로 이동하기 위한 page num
//        model.addAttribute("previous", previous);
//        model.addAttribute("next", next);
//        model.addAttribute("numList", list);
//        Long elapsedTime = System.currentTimeMillis() - startTime;
//        System.out.println("new elapsedTime: " + elapsedTime); // 447
//        return "hospitals/index";
//    }
    @GetMapping("/old")
    public String indexOld(Model model){
        Long startTime = System.currentTimeMillis();
        List<Hospital> hospitals = hospitalService.findAll();
        model.addAttribute("hospitals", hospitals);
        Long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("index_old/ elapsedTime: " + elapsedTime); // 4361
        return "hospitals/index_old";
    }
    @PostMapping("/{id}/reviews")
    public ResponseEntity<ReviewCreateResponse> writeReview(@PathVariable("id") Long hospitalId,  @RequestBody ReviewCreateRequest requestDto){
        ReviewCreateResponse responseDto = reviewService.createReview(hospitalId, requestDto);
        return ResponseEntity.ok().body(responseDto);
    }


}
