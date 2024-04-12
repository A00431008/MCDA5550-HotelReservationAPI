package com.example.MCDA5550_HotelReservationAPI.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.web.bind.annotation.*;
import com.example.MCDA5550_HotelReservationAPI.models.*;
import java.util.List;

public class hotelsController {

    @PersistenceContext
    private EntityManager entityManager;

    // API to get the hotels - queries the hotels table and returns all the hotels as list
    @GetMapping(value = "/get_hotels")
    public List<Hotel> getHotels() {
        TypedQuery<Hotel> query = entityManager.createQuery("SELECT h from Hotel h", Hotel.class);
        return query.getResultList();
    }
    
}
