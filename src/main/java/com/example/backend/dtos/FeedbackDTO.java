package com.example.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    private Integer id;
    @JsonProperty("patient_id")
    private Integer patientId;
    @JsonProperty("doctor_id")
    private Integer doctorId;
    @JsonProperty("appointment_id")
    private Integer appointmentId;
    @JsonProperty("rating")
    private Integer rating;
    private String comments;
    @JsonProperty("created_at")
    private String createdAt;
}
