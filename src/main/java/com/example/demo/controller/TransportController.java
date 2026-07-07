package com.example.demo.controller;

import com.example.demo.entity.Transport;
import com.example.demo.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/transports")
public class TransportController {

    @Autowired
    private TransportRepository transportRepository;

    @GetMapping
    public List<Transport> getAll() {
        return transportRepository.findAll();
    }

    @PostMapping
    public Transport create(@RequestBody Transport transport) {
        return transportRepository.save(transport);
    }
}
