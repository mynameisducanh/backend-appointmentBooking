package com.example.backend.responses;

import com.example.backend.models.Hospital;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalListResponses {
    private List<HospitalResponses> hospitalList;
    private int totalPages;
}
