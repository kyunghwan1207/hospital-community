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

    public Page<Hospital> findWithPage(Pageable pageable){
        Page<Hospital> hospitals = hospitalRepository.findAll(pageable);
        return hospitals;
    }

    public List<Hospital> findAll() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        return hospitals;
    }
}
