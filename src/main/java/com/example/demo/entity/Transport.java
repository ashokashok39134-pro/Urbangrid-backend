package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transports")
public class Transport {

    public enum TransportType {
        BUS,
        TRAIN,
        METRO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String transportNumber;

    @Enumerated(EnumType.STRING)
    private TransportType type;

    private int capacity;

    public Transport() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransportNumber() {
        return transportNumber;
    }

    public void setTransportNumber(String transportNumber) {
        this.transportNumber = transportNumber;
    }

    public TransportType getType() {
        return type;
    }

    public void setType(TransportType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
