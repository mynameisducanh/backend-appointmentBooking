package com.example.backend.repositories;

import com.example.backend.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findDoctorByUserId(Integer userid);
    List<Doctor> findAllBy();
}
