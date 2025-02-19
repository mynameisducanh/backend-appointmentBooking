package com.example.backend.config; // Sửa đổi package theo cấu trúc dự án của bạn

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Áp dụng cấu hình CORS cho tất cả các endpoint
                .allowedOrigins("http://localhost:8080") // Địa chỉ của frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Các phương thức HTTP cho phép
                .allowedHeaders("*") // Cho phép tất cả các tiêu đề
                .allowCredentials(true); // Cho phép gửi cookie và thông tin xác thực
    }
}
