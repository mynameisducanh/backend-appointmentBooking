package com.example.backend.controller;

import org.springframework.web.bind.annotation.*;
import com.example.backend.models.Greeting;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("${api.prefix}")
public class CustomerGreetingController {

    private static final String greetingTemplate = " Hello %s %s";
    private final AtomicLong counter = new AtomicLong();
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value="gender",defaultValue = "0") boolean gender,
                             @RequestParam(value="customerName",defaultValue = "Custumer") String customerName){
        return Greeting.builder()
                .id(counter.incrementAndGet())
                .content(String.format(greetingTemplate,gender?"Mr.":"Ms.",customerName))
                .build();
    }
}
