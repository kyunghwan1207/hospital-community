package com.hospital.hospital_community.service;

import com.hospital.hospital_community.domain.entity.Hospital;
import com.hospital.hospital_community.repository.HospitalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public Page<Hospital> findAll(Pageable pageable){
        Page<Hospital> hospitalList = hospitalRepository.findAll(pageable);
        return hospitalList;
    }
}
