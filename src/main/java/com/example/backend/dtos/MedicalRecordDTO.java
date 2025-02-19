package com.example.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordDTO {
    private Integer id;
    @JsonProperty("patient_id")
    private Integer patientId;
    @JsonProperty("doctor_id")
    private Integer doctorId;
    @JsonProperty("appointment_id")
    private Integer appointmentId;
    private String diagnosis;
    private String treatment;
    private String notes;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
}
