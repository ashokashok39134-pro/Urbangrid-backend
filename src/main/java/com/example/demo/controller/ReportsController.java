package com.example.demo.controller;

import com.example.demo.entity.Schedule;
import com.example.demo.entity.Transport;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private DelayAlertRepository alertRepository;

    @GetMapping("/stats")
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new LinkedHashMap<>();
        stats.put("totalRoutes", routeRepository.count());
        stats.put("totalSchedules", scheduleRepository.count());
        stats.put("totalUsers", userRepository.count());
        stats.put("totalTransports", transportRepository.count());
        stats.put("totalAlerts", alertRepository.count());
        return stats;
    }

    @GetMapping("/volume")
    public List<Map<String, Object>> getVolume() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<String> days = List.of("Monday", "Tuesday", "Wednesday");
        List<Map<String, Object>> result = new ArrayList<>();

        for (String day : days) {
            long count = schedules.stream()
                    .filter(s -> s.getDepartureTime() != null
                            && s.getDepartureTime().getDayOfWeek()
                                    .getDisplayName(TextStyle.FULL, Locale.ENGLISH).equals(day))
                    .count();
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("day", day);
            entry.put("count", count);
            result.add(entry);
        }
        return result;
    }

    @GetMapping("/share")
    public List<Map<String, Object>> getShare() {
        List<Transport> transports = transportRepository.findAll();
        Map<Transport.TransportType, Long> grouped = transports.stream()
                .collect(Collectors.groupingBy(Transport::getType, Collectors.counting()));

        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<Transport.TransportType, Long> e : grouped.entrySet()) {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("type", e.getKey().name());
            entry.put("count", e.getValue());
            result.add(entry);
        }
        return result;
    }
}
