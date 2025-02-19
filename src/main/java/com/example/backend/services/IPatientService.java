package com.example.backend.services;

import com.example.backend.dtos.PatientDTO;
import com.example.backend.exceptions.DataNotFoundException;
import com.example.backend.models.Patient;
import com.example.backend.responses.PatientResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IPatientService {
    Patient createPatient(PatientDTO patientDTO) throws Exception;

    PatientResponses getPatientById(Integer id) throws Exception;

    List<Patient> getAllPatients();

    Page<PatientResponses> getAllPatients(PageRequest pageRequest);

    void updatePatient(int id, PatientDTO patientDTO) throws Exception;

    void deletePatient(int id) throws Exception;

    void registerPatient(PatientDTO patientDTO) throws Exception;
}
