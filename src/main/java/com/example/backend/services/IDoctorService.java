package com.example.backend.services;

import com.example.backend.dtos.DoctorDTO;
import com.example.backend.exceptions.DataNotFoundException;
import com.example.backend.models.Doctor;
import com.example.backend.responses.DoctorResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;


public interface IDoctorService {
    public Doctor createDoctor(DoctorDTO doctorDTO) throws DataNotFoundException;
    Doctor getDoctorById(Integer id) throws Exception;
    Page<DoctorResponses> getAllDoctor(PageRequest pageRequest);
    Doctor updateDoctor(Doctor doctor);
    void deleteDoctor(Integer id);


}
