package com.example.backend.responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientListResponses {
    private List<PatientResponses> patientList;
    private int totalPages;
}
