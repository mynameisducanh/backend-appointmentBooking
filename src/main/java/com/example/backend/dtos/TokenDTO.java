package com.example.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    private Integer id;
    @JsonProperty("token")
    private String token;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expiration_date")
    private String expirationDate;
    @JsonProperty("revoked")
    private boolean revoked;
    @JsonProperty("expired")
    private boolean expired;
    @JsonProperty("user_id")
    private Integer userId;
}
