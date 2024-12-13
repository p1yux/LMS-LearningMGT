package com.lms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Public endpoint works!";
    }

    @GetMapping("/authenticated")
    public String authenticatedEndpoint() {
        return "Authenticated endpoint works!";
    }
} 