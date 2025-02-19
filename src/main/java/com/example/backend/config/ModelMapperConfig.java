package com.example.backend.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Nếu cần, cấu hình các mapping tùy chỉnh ở đây
        // Ví dụ:
        // modelMapper.typeMap(AppointmentDTO.class, Appointment.class)
        //         .addMappings(mapper -> {
        //             mapper.map(src -> src.getSomeField(), Appointment::setSomeField);
        //         });

        return modelMapper;
    }
}
