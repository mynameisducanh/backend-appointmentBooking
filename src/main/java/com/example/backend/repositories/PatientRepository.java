package com.example.backend.repositories;

import com.example.backend.models.Doctor;
import com.example.backend.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    List<Patient> findPatientByUserId(Integer userId);
    List<Patient> findAllBy();
}
