package com.example.backend.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentResponses extends BaseResponses{
    private Integer id;

    private Integer patientId;

    private Integer doctorId;

    private String appointmentDate;

    private String appointmentTime;
    private String status;

}
