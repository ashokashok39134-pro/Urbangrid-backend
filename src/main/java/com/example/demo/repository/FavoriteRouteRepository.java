package com.example.demo.repository;

import com.example.demo.entity.FavoriteRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRouteRepository extends JpaRepository<FavoriteRoute, Long> {

    List<FavoriteRoute> findByCommuterId(Long commuterId);

    boolean existsByCommuterIdAndRouteId(Long commuterId, Long routeId);
}
