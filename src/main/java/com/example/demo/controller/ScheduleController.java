package com.example.demo.controller;

import com.example.demo.entity.Schedule;
import com.example.demo.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERATOR', 'ADMIN')")
    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Schedule create(@RequestBody Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Schedule update(@PathVariable Long id, @RequestBody Schedule schedule) {
        schedule.setId(id);
        return scheduleRepository.save(schedule);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        scheduleRepository.deleteById(id);
        return ResponseEntity.ok("Schedule deleted successfully.");
    }
}
