package com.depot.shopping.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kdm
 */
@Tag(name = "Depot Shop", description = "Depot Shop Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MainController {

    @GetMapping("/check")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok("SUCCESS");
    }
}
