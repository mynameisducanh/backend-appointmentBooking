package com.example.backend.services;

import com.example.backend.dtos.SpecializationDTO;
import com.example.backend.models.Specialization;
import com.example.backend.repositories.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class SpecializationService implements ISpecializationService {
    private final SpecializationRepository specializationRepository;
    @Override
    public Specialization createSpecialization(SpecializationDTO specializationDTO) {
        Specialization newSpecialization = Specialization.builder()
                .name(specializationDTO.getName())
                .description(specializationDTO.getDescription())
                .build();
        return specializationRepository.save(newSpecialization);
    }

    @Override
    public Specialization getSpecializationById(Integer id) {
        return specializationRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Specialization not found " + id));
    }

    @Override
    public List<Specialization> getAllSpecialization() {
        return specializationRepository.findAllBy();
    }

    @Override
    public Specialization updateSpecialization(Specialization specialization) {
        return specializationRepository.save(specialization);
    }

    @Override
    public void deleteSpecialization(Integer id) {
        Specialization specialization = getSpecializationById(id);
        specializationRepository.delete(specialization);
    }
}
