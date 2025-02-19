package com.example.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hospital_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalImage {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Column(name = "image_url",length = 300)
    private String imageUrl;
}
