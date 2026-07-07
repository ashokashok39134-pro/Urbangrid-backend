package com.example.demo.controller;

import com.example.demo.entity.FavoriteRoute;
import com.example.demo.entity.Route;
import com.example.demo.entity.User;
import com.example.demo.repository.FavoriteRouteRepository;
import com.example.demo.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteRouteRepository favoriteRepository;

    @GetMapping
    public List<FavoriteRoute> getMyFavorites() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return favoriteRepository.findByCommuterId(userDetails.getId());
    }

    @PostMapping("/{routeId}")
    public FavoriteRoute addFavorite(@PathVariable Long routeId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User commuter = new User();
        commuter.setId(userDetails.getId());

        Route route = new Route();
        route.setId(routeId);

        FavoriteRoute favorite = new FavoriteRoute();
        favorite.setCommuter(commuter);
        favorite.setRoute(route);

        return favoriteRepository.save(favorite);
    }
}
