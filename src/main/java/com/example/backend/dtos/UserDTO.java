package com.example.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String fullName;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    private String address;
    @NotBlank(message = "password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @NotBlank(message = "retypePassword is required")
    private String retypePassword;

    private String createdAt;

    private String updatedAt;

    private boolean isActive;
    private String dateOfBirth;

    private Integer facebookAccountId;

    private Integer googleAccountId;
    private Integer roleId;
}
