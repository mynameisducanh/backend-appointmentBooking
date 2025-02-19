package com.example.backend.services;

import com.example.backend.dtos.HospitalDTO;
import com.example.backend.dtos.HospitalImageDTO;
import com.example.backend.exceptions.DataNotFoundException;
import com.example.backend.exceptions.InvalidParamException;
import com.example.backend.models.Hospital;
import com.example.backend.models.HospitalImage;
import com.example.backend.repositories.HospitalImageRepository;
import com.example.backend.repositories.HospitalRepository;
import com.example.backend.responses.HospitalResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HospitalService implements IHospitalService {
    private final HospitalRepository hospitalRepository;
    private final HospitalImageRepository hospitalImageRepository;

    @Override
    public Hospital createHospital(HospitalDTO hospitalDTO) {
        // Mapping from DTO to Entity
        Hospital newHospital = Hospital.builder()
                .name(hospitalDTO.getName())
                .description(hospitalDTO.getDescription())
                .vote(hospitalDTO.getVote())
                .build();
        return hospitalRepository.save(newHospital);
    }

    @Override
    public Hospital getHospitalById(Integer id) {
        // Using orElseThrow to handle the case when a hospital is not found
        try {
            return hospitalRepository.findById(id)
                    .orElseThrow(() -> new DataNotFoundException("Hospital not found with ID: " + id));
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<HospitalResponses> getAllHospital(PageRequest pageRequest) {
        return hospitalRepository.findAll(pageRequest).map(hospital -> {
            HospitalResponses hospitalResponses = HospitalResponses.builder()
                    .name(hospital.getName())
                    .description(hospital.getDescription())
                    .vote(hospital.getVote())
                    .build();
            hospitalResponses.setCreatedAt(hospital.getCreatedAt());
            hospitalResponses.setUpdatedAt(hospital.getUpdatedAt());
            return hospitalResponses;
        });
    }

    @Override
    public Hospital updateHospital(Hospital hospital) {
        // Save the updated hospital
        return hospitalRepository.save(hospital);
    }

    @Override
    public void deleteHospital(Integer id) {
        // Check if the hospital exists before deleting
        Hospital hospital = getHospitalById(id);
        hospitalRepository.delete(hospital);
    }

    public HospitalImage createHospitalImage(Integer hospitalId, HospitalImageDTO hospitalImageDTO) throws Exception {
        // Fetch the hospital entity or throw an exception if not found
        Hospital existingHospital = hospitalRepository
                .findById(hospitalId)
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find hospital with id : " + hospitalId
                        ));

        // Create a new HospitalImage object
        HospitalImage newHospitalImage = HospitalImage.builder()
                .hospital(existingHospital)
                .imageUrl(hospitalImageDTO.getImageUrl())
                .build();

        // Check if the number of images exceeds the allowed limit
        int size = hospitalImageRepository.findByHospitalId(hospitalId).size();
        if (size >= 5) {
            throw new InvalidParamException("Number of images must be <= 5");
        }

        // Save the new image
        return hospitalImageRepository.save(newHospitalImage);
    }
}
