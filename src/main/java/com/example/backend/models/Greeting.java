package com.example.backend.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Greeting {
    private long id;
    private String content;
}
