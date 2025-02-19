package com.example.backend.services;

import com.example.backend.dtos.PatientDTO;
import com.example.backend.exceptions.DataNotFoundException;
import com.example.backend.models.Patient;
import com.example.backend.models.User;
import com.example.backend.repositories.PatientRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.responses.PatientResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Patient createPatient(PatientDTO patientDTO) throws Exception {
        // Kiểm tra giá trị userId từ PatientDTO
        if (patientDTO.getUserId() == null) {
            throw new IllegalArgumentException("userId must not be null");
        }

        // Lấy thông tin user
        Optional<User> userOpt = userRepository.findById(patientDTO.getUserId());
        if (userOpt.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }

        // Tạo đối tượng Patient từ DTO
        Patient patient = Patient.builder()
                .userId(patientDTO.getUserId())
                .dateOfBirth(LocalDate.parse(patientDTO.getDateOfBirth()))
                .address(patientDTO.getAddress())
                .gender(patientDTO.getGender())
                .medicalHistory(patientDTO.getMedicalHistory())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        // Lấy tất cả bệnh nhân từ cơ sở dữ liệu
        return patientRepository.findAll();
    }

    @Override
    public PatientResponses getPatientById(Integer id) throws Exception {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Patient not found"));

        return modelMapper.map(patient, PatientResponses.class);
    }

    @Override
    public Page<PatientResponses> getAllPatients(PageRequest pageRequest) {
        return patientRepository.findAll(pageRequest)
                .map(patient -> modelMapper.map(patient, PatientResponses.class));
    }

    @Override
    public void updatePatient(int id, PatientDTO patientDTO) throws Exception {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Patient not found"));

        if (patientDTO.getDateOfBirth() != null) {
            existingPatient.setDateOfBirth(LocalDate.parse(patientDTO.getDateOfBirth()));
        }
        if (patientDTO.getAddress() != null) {
            existingPatient.setAddress(patientDTO.getAddress());
        }
        if (patientDTO.getGender() != null) {
            existingPatient.setGender(patientDTO.getGender());
        }
        if (patientDTO.getMedicalHistory() != null) {
            existingPatient.setMedicalHistory(patientDTO.getMedicalHistory());
        }

        existingPatient.setUpdatedAt(LocalDateTime.now());

        patientRepository.save(existingPatient);
    }

    @Override
    public void deletePatient(int id) throws Exception {
        if (!patientRepository.existsById(id)) {
            throw new DataNotFoundException("Patient not found");
        }
        patientRepository.deleteById(id);
    }

    @Override
    public void registerPatient(PatientDTO patientDTO) throws Exception {
        // Logic to register a patient (similar to createPatient)
        createPatient(patientDTO);
    }
}
