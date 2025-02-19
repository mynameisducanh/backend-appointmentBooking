package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {

    private Integer userId;

    private String name;

    private String specialization;

    private String qualification;

    private Integer experienceYears;

    private String clinicAddress;

//    private String file;

    // Additional fields for creation if needed
//    private String hospitalId;
//    private List<String> clinicHours;
}
