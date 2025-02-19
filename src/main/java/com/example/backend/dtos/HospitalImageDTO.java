package com.example.backend.dtos;

import com.example.backend.models.Hospital;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalImageDTO {
    @JsonProperty("hospital_id")
    private Hospital hospital;

    @Size(min=5,max=200 , message = "Image's name")
    @JsonProperty("image_url")
    private String imageUrl;
}
