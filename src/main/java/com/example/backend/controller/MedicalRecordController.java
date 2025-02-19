package com.example.backend.controller;

import com.example.backend.dtos.MedicalRecordDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/medical-records")
public class MedicalRecordController {
    @PostMapping("/add")
    public ResponseEntity<?> addMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) {
        try {
            // Logic to add medical record
            return ResponseEntity.ok("Medical record added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
