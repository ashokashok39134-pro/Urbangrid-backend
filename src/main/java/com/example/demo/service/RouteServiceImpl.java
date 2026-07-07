package com.example.demo.service;

import com.example.demo.entity.Route;
import com.example.demo.exception.RouteNotFoundException;
import com.example.demo.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public List<Route> getAll() {
        return routeRepository.findAll();
    }

    @Override
    public Route getById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new RouteNotFoundException("Route not found with id: " + id));
    }

    @Override
    public Route create(Route route) {
        return routeRepository.save(route);
    }

    @Override
    public Route update(Long id, Route route) {
        getById(id);
        route.setId(id);
        return routeRepository.save(route);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        routeRepository.deleteById(id);
    }
}
