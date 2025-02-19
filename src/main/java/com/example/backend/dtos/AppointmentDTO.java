package com.example.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private Integer id;

    private Integer patientId;

    private Integer doctorId;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;
    private String status;

    private String createdAt;

    private String updatedAt;
}
