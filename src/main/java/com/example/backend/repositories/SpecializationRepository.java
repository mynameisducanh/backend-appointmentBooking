package com.example.backend.repositories;

import com.example.backend.models.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

    List<Specialization> findAllBy();
    List<Specialization> findSpecializationById(Integer id);
}
