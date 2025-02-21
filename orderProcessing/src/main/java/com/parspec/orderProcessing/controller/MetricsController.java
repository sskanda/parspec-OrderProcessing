package com.parspec.orderProcessing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parspec.orderProcessing.service.MetricsService;

import java.util.Map;

@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
public class MetricsController {
    private final MetricsService metricsService;
    
    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getMetrics() {
        return ResponseEntity.ok(metricsService.getMetrics());
    }
}