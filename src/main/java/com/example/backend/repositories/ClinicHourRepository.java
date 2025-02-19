package com.example.backend.repositories;

import com.example.backend.models.ClinicHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicHourRepository extends JpaRepository<ClinicHour, Integer> {
    List<ClinicHour> findByDoctorUserId(Integer doctorUserId);
}
