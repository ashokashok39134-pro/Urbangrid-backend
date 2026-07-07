package com.example.demo.controller;

import com.example.demo.entity.DelayAlert;
import com.example.demo.repository.DelayAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private DelayAlertRepository alertRepository;

    @GetMapping
    public List<DelayAlert> getAll() {
        return alertRepository.findAll();
    }

    @PostMapping
    public DelayAlert create(@RequestBody DelayAlert alert) {
        alert.setCreatedAt(LocalDateTime.now());
        return alertRepository.save(alert);
    }
}
