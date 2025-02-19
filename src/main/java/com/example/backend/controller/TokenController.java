package com.example.backend.controller;

import com.example.backend.dtos.TokenDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/tokens")
public class TokenController {
    @PostMapping("/generate")
    public ResponseEntity<?> generateToken(@RequestBody TokenDTO tokenDTO) {
        try {
            // Logic to generate token
            return ResponseEntity.ok("Token generated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
