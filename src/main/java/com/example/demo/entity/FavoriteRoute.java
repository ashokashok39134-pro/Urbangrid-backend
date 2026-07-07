package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "favorite_routes")
public class FavoriteRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commuter_id")
    private User commuter;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    public FavoriteRoute() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCommuter() {
        return commuter;
    }

    public void setCommuter(User commuter) {
        this.commuter = commuter;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
