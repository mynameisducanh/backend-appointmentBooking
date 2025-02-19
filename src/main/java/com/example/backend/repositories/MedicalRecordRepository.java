package com.example.backend.repositories;

import com.example.backend.models.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {
    // Các phương thức truy vấn bổ sung nếu cần
    List<MedicalRecord> findByPatientUserId(Integer userId);
    List<MedicalRecord> findByDoctorUserId(Integer doctorUserId);
}
