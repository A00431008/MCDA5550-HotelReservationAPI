package com.example.MCDA5550_HotelReservationAPI.models;

import jakarta.persistence.*;

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private int hotel_id; // Using auto-incrementing primary key

    @Column(name = "hotel_name")
    private String hotel_name;

    @Column(name = "price")
    private double price;

    @Column(name = "availability")
    private boolean availability;

    // Default constructor
    public Hotel() {
        this.availability = false;
    }

    public int getId() {
        return this.hotel_id;
    }

    public void setId(int id) {
        this.hotel_id = id;
    }

    public String getName() {
        return this.hotel_name;
    }

    public void setName(String name) {
        this.hotel_name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return this.availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
