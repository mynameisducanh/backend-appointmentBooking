package com.example.backend.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponses extends BaseResponses{
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("date_of_birth")
    private String dateOfBirth;
    private String address;
    private String gender;
    @JsonProperty("medical_history")
    private String medicalHistory;

}
