package com.hospital.hospital_community.service;

import com.hospital.hospital_community.domain.entity.Hospital;
import com.hospital.hospital_community.repository.HospitalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public List<Hospital> findAll(){
        List<Hospital> hospitalList = hospitalRepository.findAll();
        return hospitalList;
    }
}
