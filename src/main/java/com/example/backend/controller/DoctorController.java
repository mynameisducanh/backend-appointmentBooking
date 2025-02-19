package com.example.backend.controller;

import com.example.backend.dtos.DoctorDTO;
import com.example.backend.models.Doctor;
import com.example.backend.responses.DoctorListResponses;
import com.example.backend.responses.DoctorResponses;
import com.example.backend.services.IDoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final IDoctorService doctorService;

    @PostMapping("/create")
    public ResponseEntity<?> createDoctor(
            @Valid @ModelAttribute DoctorDTO doctorDTO,
            BindingResult result
    ) {
        // Validate the input
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            // Process doctor creation
            Doctor newDoctor = doctorService.createDoctor(doctorDTO);
            return ResponseEntity.ok(newDoctor);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Collections.singletonList("Error creating doctor"));
        }
    }


    @PostMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadDoctorImage(
            @RequestParam("file") MultipartFile file,
            @PathVariable("id") Integer id
    ) {
        try {
            // Check if the doctor exists
            Doctor existingDoctor = doctorService.getDoctorById(id);

            if (existingDoctor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonList("Doctor not found"));
            }

            // Validate file presence
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonList("No file provided"));
            }

            // Check file size (10 MB limit)
            if (file.getSize() > 10 * 1024 * 1024) {
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .body(Collections.singletonList("File is too large! Maximum size is 10MB"));
            }

            // Check file type
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body(Collections.singletonList("File must be an image"));
            }

            // Store the file
            String storedFilename = storeFile(file);
            existingDoctor.setFile(storedFilename);
            doctorService.updateDoctor(existingDoctor);

            // Return the stored filename in a list for consistency
            return ResponseEntity.ok(Collections.singletonList(storedFilename));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList("Error storing file"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(Collections.singletonList("Error uploading image"));
        }
    }

    @GetMapping("")
    public ResponseEntity<DoctorListResponses> getAllDoctors(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        // Create PageRequest object with page and limit
        PageRequest pageRequest = PageRequest.of(page, limit);

        // Fetch the paginated list of doctors
        Page<DoctorResponses> doctorPage = doctorService.getAllDoctor(pageRequest);

        // Extract the total pages and list of doctors from the page
        int totalPages = doctorPage.getTotalPages();
        List<DoctorResponses> doctorList = doctorPage.getContent();

        // Create a response wrapper for the doctors list
        DoctorListResponses response = DoctorListResponses.builder()
                .doctorList(doctorList)
                .totalPages(totalPages)
                .build();

        // Return the response entity with the wrapped doctors list
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable("id") int doctorId) throws Exception {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (doctor != null) {
            return ResponseEntity.ok(doctor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateDoctor(
            @PathVariable int id,
            @RequestParam("name") String name,
            @RequestParam("specialization") String specialization,
            @RequestParam("qualification") String qualification,
            @RequestParam("experienceYears") int experienceYears,
            @RequestParam("clinicAddress") String clinicAddress,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        try {
            Doctor existingDoctor = doctorService.getDoctorById(id);
            if (existingDoctor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
            }

            existingDoctor.setName(name);
            existingDoctor.setSpecialization(specialization);
            existingDoctor.setQualification(qualification);
            existingDoctor.setExperienceYears(experienceYears);
            existingDoctor.setClinicAddress(clinicAddress);

            if (file != null && !file.isEmpty()) {
                String contentType = file.getContentType();
                if (contentType != null && contentType.startsWith("image/")) {
                    String storedFilename = storeFile(file);
                    existingDoctor.setFile(storedFilename);
                } else {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
            }

            doctorService.updateDoctor(existingDoctor);
            return ResponseEntity.ok("Doctor updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error updating doctor");
        }
    }

    @GetMapping("/img/{imgName}")
    public ResponseEntity<?> viewImg(@PathVariable String imgName){
        try{
            java.nio.file.Path imgPath = Paths.get("uploads/" + imgName);
            UrlResource resource = new UrlResource(imgPath.toUri());
            if(resource.exists()){
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }else{
                return ResponseEntity.notFound().build();
            }
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable int id) {
        try {
            doctorService.deleteDoctor(id);
            return ResponseEntity.ok("Doctor with id " + id + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        // Add UUID to filename to avoid collisions
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Full path to the file
        Path destination = uploadDir.resolve(uniqueFilename);
        // Copy file to the destination directory
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
}
