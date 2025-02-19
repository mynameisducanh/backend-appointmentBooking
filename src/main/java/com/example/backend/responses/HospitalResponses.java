package com.example.backend.responses;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalResponses extends BaseResponses{
    private String name;
    private String description;
    private int vote;
}
