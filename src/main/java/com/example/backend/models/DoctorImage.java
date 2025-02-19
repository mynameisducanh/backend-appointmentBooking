package com.example.backend.models;

import jakarta.persistence.*;

public class DoctorImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Doctor doctor;

    @Column(name = "image_url",length = 300)
    private String imageUrl;
}
