package com.example.backend.services;

import com.example.backend.dtos.SpecializationDTO;
import com.example.backend.models.Specialization;

import java.util.List;

public interface ISpecializationService {
    Specialization createSpecialization(SpecializationDTO specializationDTO);

    Specialization getSpecializationById(Integer id);
    List<Specialization> getAllSpecialization();
    Specialization updateSpecialization(Specialization specialization);

    void deleteSpecialization(Integer id);

}
