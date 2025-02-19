package com.example.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Integer userId;

    private String dateOfBirth;
    private String address;
    private String gender;

    private String medicalHistory;

    private String createdAt;

    private String updatedAt;
}
