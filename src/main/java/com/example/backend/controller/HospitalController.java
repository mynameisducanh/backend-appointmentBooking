package com.example.backend.controller;

import com.example.backend.dtos.HospitalDTO;
import com.example.backend.dtos.HospitalImageDTO;
import com.example.backend.models.Hospital;
import com.example.backend.models.HospitalImage;
import com.example.backend.responses.HospitalListResponses;
import com.example.backend.responses.HospitalResponses;
import com.example.backend.services.IHospitalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/hospital")
@RequiredArgsConstructor
public class HospitalController {

    private final IHospitalService hospitalService;

    @PostMapping("")
    public ResponseEntity<?> createHospital(
            @Valid @ModelAttribute HospitalDTO hospitalDTO,
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
            // Process hospital creation
            Hospital newHospital = hospitalService.createHospital(hospitalDTO);
            return ResponseEntity.ok(newHospital);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Collections.singletonList("Error creating hospital"));
        }
    }

    @PostMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @RequestParam("files") MultipartFile[] files,
            @PathVariable("id") Integer id
    ) {
        try {
            Hospital existingHospital = hospitalService.getHospitalById(id);

            if (files == null || files.length == 0) {
                return ResponseEntity.badRequest().body("No files provided");
            }

            List<HospitalImage> hospitalImages = new ArrayList<>();

            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    // Check file size
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
                    String filename = storeFile(file);

                    HospitalImage hospitalImage = hospitalService.createHospitalImage(
                            existingHospital.getId(),
                            HospitalImageDTO.builder()
                                    .imageUrl(filename)
                                    .build()
                    );
                    hospitalImages.add(hospitalImage);
                }
            }

            return ResponseEntity.ok(hospitalImages);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList("Error storing files"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(Collections.singletonList("Error processing images"));
        }
    }

    @GetMapping("")
    public ResponseEntity<HospitalListResponses> getAllHospital(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<HospitalResponses> hospitalPage = hospitalService.getAllHospital(pageRequest);
        int totalPages = hospitalPage.getTotalPages();
        List<HospitalResponses> hospitalList = hospitalPage.getContent();
        HospitalListResponses responses = HospitalListResponses
                .builder()
                .hospitalList(hospitalList)
                .totalPages(totalPages)
                .build();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHospitalById(@PathVariable("id") int hospitalId) {
        try {

            Hospital hospital = hospitalService.getHospitalById(hospitalId);
            return ResponseEntity.ok(hospital);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateHospital(
            @PathVariable int id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("vote") int vote,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        try {
            Hospital existingHospital = hospitalService.getHospitalById(id);
            existingHospital.setName(name);
            existingHospital.setDescription(description);
            existingHospital.setVote(vote);

            if (file != null && !file.isEmpty()) {
                String contentType = file.getContentType();
                if (contentType != null && contentType.startsWith("image/")) {
                    String storedFilename = storeFile(file);
                    // Handle storing file paths in your Hospital model if needed
                    // existingHospital.setFile(storedFilename);
                } else {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
            }

            hospitalService.updateHospital(existingHospital);
            return ResponseEntity.ok("Update hospital successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error updating hospital");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHospital(@PathVariable int id) {
        try {
            hospitalService.deleteHospital(id);
            return ResponseEntity.ok(String.format("Delete hospital with id %d successfully", id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("Hospital with id %d not found", id));
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path destination = uploadDir.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
}
