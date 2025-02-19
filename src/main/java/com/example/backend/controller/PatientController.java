package com.example.backend.controller;

import com.example.backend.dtos.PatientDTO;
import com.example.backend.models.Patient;
import com.example.backend.responses.PatientListResponses;
import com.example.backend.responses.PatientResponses;
import com.example.backend.services.IPatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/patients")
@RequiredArgsConstructor
public class PatientController {

    private final IPatientService patientService;

    @PostMapping("/add")
    public ResponseEntity<?> createPatient(@Valid @RequestBody PatientDTO patientDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            if (patientDTO.getUserId() == null) {
                return ResponseEntity.badRequest().body("userId must not be null");
            }

            Patient newPatient = patientService.createPatient(patientDTO);
            return ResponseEntity.ok(newPatient);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Collections.singletonList("Error adding patient"));
        }
    }
    @GetMapping("")
    public ResponseEntity<PatientListResponses> getAllPatients(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(page, limit);

        Page<PatientResponses> patientPage = patientService.getAllPatients(pageRequest);

        int totalPages = patientPage.getTotalPages();
        List<PatientResponses> patientList = patientPage.getContent();

        PatientListResponses response = PatientListResponses.builder()
                .patientList(patientList)
                .totalPages(totalPages)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponses> getPatientById(@PathVariable("id") int patientId) {
        try {
            PatientResponses patient = patientService.getPatientById(patientId);
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatient(
            @PathVariable int id,
            @RequestBody PatientDTO patientDTO
    ) {
        try {
            patientService.updatePatient(id, patientDTO);
            return ResponseEntity.ok("Patient updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error updating patient");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable int id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.ok("Patient with id " + id + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
        }
    }

    // Default registration method for patient
    @PostMapping("/register")
    public ResponseEntity<String> registerPatient(@Valid @RequestBody PatientDTO patientDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(String.valueOf(errorMessages));
        }

        try {
            patientService.registerPatient(patientDTO);
            return ResponseEntity.ok("Patient registered successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error registering patient");
        }
    }
}
