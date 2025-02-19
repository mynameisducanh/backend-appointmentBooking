package com.example.backend.controller;

import com.example.backend.dtos.ClinicHoursDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/clinic-hours")
public class ClinicHoursController {
    @PostMapping("/update")
    public ResponseEntity<?> updateClinicHours(@RequestBody ClinicHoursDTO clinicHoursDTO) {
        try {
            // Logic to update clinic hours
            return ResponseEntity.ok("Clinic hours updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
