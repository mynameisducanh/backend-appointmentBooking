package com.example.backend.services;

import com.example.backend.dtos.DoctorDTO;
import com.example.backend.exceptions.DataNotFoundException;
import com.example.backend.models.Doctor;
import com.example.backend.repositories.DoctorRepository;
import com.example.backend.responses.DoctorResponses;
import com.example.backend.responses.HospitalResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService implements IDoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    public Doctor createDoctor(DoctorDTO doctorDTO) throws DataNotFoundException {
        if (doctorDTO.getUserId() == null) {
            throw new IllegalArgumentException("User ID must be provided.");
        }

        // Create a new doctor without checking for existing doctor ID
        Doctor newDoctor = Doctor.builder()
                .userId(doctorDTO.getUserId())
                .name(doctorDTO.getName())
                .specialization(doctorDTO.getSpecialization())
                .qualification(doctorDTO.getQualification())
                .experienceYears(doctorDTO.getExperienceYears())
                .clinicAddress(doctorDTO.getClinicAddress())
                .build();

        return doctorRepository.save(newDoctor);
    }

    @Override
    public Doctor getDoctorById(Integer id) throws Exception{
        return doctorRepository.findById(id).
                orElseThrow(()->new DataNotFoundException(
                        "Cannot find doctor with id = "+ id));
    }

    @Override
    public Page<DoctorResponses> getAllDoctor(PageRequest pageRequest) {
        // Fetch all doctors with pagination and map each Doctor entity to a DoctorResponses DTO
        return doctorRepository.findAll(pageRequest).map(doctor -> {
            // Create a new DoctorResponses builder and populate it with Doctor entity fields
            DoctorResponses doctorResponses = DoctorResponses.builder()
                    .name(doctor.getName()) // Ensure the correct field mapping
                    .specialization(doctor.getSpecialization()) // Add necessary fields
                    .qualification(doctor.getQualification())
                    .experienceYears(doctor.getExperienceYears())
                    .clinicAddress(doctor.getClinicAddress())
                    .build();

            // Set createdAt and updatedAt fields from Doctor entity
            doctorResponses.setCreatedAt(doctor.getCreatedAt());
            doctorResponses.setUpdatedAt(doctor.getUpdatedAt());

            return doctorResponses;
        });
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        // Kiểm tra sự tồn tại của bác sĩ trước khi cập nhật
        if (!doctorRepository.existsById(doctor.getUserId())) {
            throw new IllegalArgumentException("Doctor with ID " + doctor.getUserId() + " does not exist.");
        }
        // Cập nhật bác sĩ
        return doctorRepository.save(doctor);
    }

    @Override
    public void deleteDoctor(Integer id) {
        if (!doctorRepository.existsById(id)) {
            throw new IllegalArgumentException("Doctor with ID " + id + " does not exist.");
        }
        doctorRepository.deleteById(id);
    }
}
