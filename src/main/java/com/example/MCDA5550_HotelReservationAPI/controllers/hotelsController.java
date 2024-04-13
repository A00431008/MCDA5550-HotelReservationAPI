package com.example.MCDA5550_HotelReservationAPI.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.MCDA5550_HotelReservationAPI.models.*;
import jakarta.persistence.PersistenceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HotelsController {

    @PersistenceContext
    private EntityManager entityManager;

    // API to get the hotels - queries the hotels table and returns all the hotels as list
    @GetMapping(value="/get_hotels")
    public List<Hotel> getHotels() {
        TypedQuery<Hotel> query = entityManager.createQuery("SELECT h from Hotel h", Hotel.class);
        return query.getResultList();
    }

    @Transactional
    @PostMapping(value="/add_hotel", consumes = "application/json")
    public ResponseEntity<String> addHotel(@RequestBody Hotel hotel) {
        try {
            entityManager.persist(hotel);
            return new ResponseEntity<>("Successfully added hotel !!!", HttpStatus.OK);
        } catch (PersistenceException e) {
            // Check if it's a constraint violation or other database-related error
            if (e.getCause() != null && e.getCause().getMessage().contains("constraint")) {
                return new ResponseEntity<>("Conflict: The resource already exists or violates a uniqueness constraint", HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>("Internal Server Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Bad Request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>("Internal Server Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
