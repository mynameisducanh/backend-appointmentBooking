package com.example.backend.responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorListResponses {
    private List<DoctorResponses> doctorList;
    private int totalPages;
}
