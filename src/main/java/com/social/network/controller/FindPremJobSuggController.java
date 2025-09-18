package com.social.network.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to handle requests for finding premium job suggestions.
 */
@RestController
@RequestMapping("/api/v1/find-premium-job-suggestions")
public class FindPremJobSuggController {
    @GetMapping("/information")
    public ResponseEntity<String> getPremiumJobSuggestions() {
        return ResponseEntity.ok("Este es un controlador de prueba.");
    }
}
