package com.example.backend.repositories;

import com.example.backend.models.HospitalImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalImageRepository extends JpaRepository<HospitalImage,Integer> {
    List<HospitalImage> findByHospitalId(Integer id);
}
