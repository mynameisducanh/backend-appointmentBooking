package com.example.backend.repositories;

import com.example.backend.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    // Truy vấn theo ID của User liên kết với Patient
    List<Appointment> findByPatientUserId(Integer userId);

    // Truy vấn theo ID của Doctor
    List<Appointment> findByDoctorUserId(Integer doctorUserId);
}
