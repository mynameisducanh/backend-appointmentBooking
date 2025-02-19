package com.example.backend.controller;

import com.example.backend.dtos.SpecializationDTO;
import com.example.backend.models.Specialization;
import com.example.backend.services.ISpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/specializations")
@RequiredArgsConstructor
public class SpecializationController {

    private final ISpecializationService specializationService;

    @PostMapping("/create")
    public ResponseEntity<String> createSpecialization(@RequestBody SpecializationDTO specializationDTO) {
        try {
            specializationService.createSpecialization(specializationDTO);
            return ResponseEntity.ok("Specialization added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specialization> getSpecializationById(@PathVariable Integer id) {
        try {
            Specialization specialization = specializationService.getSpecializationById(id);
            return ResponseEntity.ok(specialization);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Specialization>> getAllSpecializations() {
        List<Specialization> specializations = specializationService.getAllSpecialization();
        return ResponseEntity.ok(specializations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSpecialization(@PathVariable Integer id, @RequestBody SpecializationDTO specializationDTO) {
        try {
            Specialization existingSpecialization = specializationService.getSpecializationById(id);
            existingSpecialization.setName(specializationDTO.getName());
            existingSpecialization.setDescription(specializationDTO.getDescription());
            specializationService.updateSpecialization(existingSpecialization);
            return ResponseEntity.ok("Specialization updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSpecialization(@PathVariable Integer id) {
        try {
            specializationService.deleteSpecialization(id);
            return ResponseEntity.ok("Specialization deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Specialization not found");
        }
    }
}
