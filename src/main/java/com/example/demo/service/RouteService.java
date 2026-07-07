package com.example.demo.service;

import com.example.demo.entity.Route;

import java.util.List;

public interface RouteService {

    List<Route> getAll();

    Route getById(Long id);

    Route create(Route route);

    Route update(Long id, Route route);

    void delete(Long id);
}
