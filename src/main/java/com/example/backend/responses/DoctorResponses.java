package com.example.backend.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorResponses extends BaseResponses{

    private Integer userId;

    private String name;

    private String specialization;

    private String qualification;

    private Integer experienceYears;

    private String clinicAddress;

}
