package com.hospital.hospital_community.domain.dto;

import com.hospital.hospital_community.domain.entity.Hospital;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDTO {
    private Long id;

    private String managementNumber;
    private String phone;
    private String roadNameAddress;
    private String hospitalName;
    private String businessTypeName;
    private int businessStatus; // 변경가능
    private int healthcareProviderCount;  // 변경가능
    private int patientRoomCount;  // 변경가능
    private int totalNumberOfBeds;  // 변경가능
    private float totalAreaSize;  // 변경가능

    public HospitalDTO(int businessStatus, int healthcareProviderCount, int patientRoomCount, int totalNumberOfBeds) {
        this.businessStatus = businessStatus;
        this.healthcareProviderCount = healthcareProviderCount;
        this.patientRoomCount = patientRoomCount;
        this.totalNumberOfBeds = totalNumberOfBeds;
    }
}
