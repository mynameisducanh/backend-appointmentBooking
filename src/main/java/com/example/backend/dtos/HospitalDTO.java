package com.example.backend.dtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDTO {
    private String name;
    private String description;
    private int vote;
//    private List<MultipartFile> files = new ArrayList<>();
}
