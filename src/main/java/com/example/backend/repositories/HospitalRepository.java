package com.example.backend.repositories;

import com.example.backend.models.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {
    List<Hospital> findHospitalById(Integer id);

    List<Hospital> findById(Hospital hospital);

}
