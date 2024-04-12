package com.example.MCDA5550_HotelReservationAPI.controllers;

import com.example.MCDA5550_HotelReservationAPI.models.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ReservationsController {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @PostMapping("/create_reservation")
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequest reservationRequest) {
        // Validate request
        if (reservationRequest == null || reservationRequest.getHotelName() == null ||
                reservationRequest.getCheckInDate() == null || reservationRequest.getCheckOutDate() == null ||
                reservationRequest.getGuestsList() == null || reservationRequest.getGuestsList().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid request body");
        }

        // Retrieve hotel based on name - Hotel names are assumed to be unique
        // TypedQuery<Hotel> query = entityManager.createQuery("SELECT h from Hotel h WHERE hotel_name= :hotelName", Hotel.class);
        // query.setParameter("hotelName", reservationRequest.getHotelName());
        // Hotel hotel = query.getSingleResult();
        // if (hotel == null) {
        //     return ResponseEntity.badRequest().body("Hotel not found");
        // }

        // Create reservation
        Reservation reservation = new Reservation();

        Hotel hotel = new Hotel();
        hotel.setName("Example Hotel");
        hotel.setPrice(100.0); // Set the price
        hotel.setAvailability(true); // Set availability


        reservation.setHotel(hotel);
        reservation.setCheckInDate(reservationRequest.getCheckInDate());
        reservation.setCheckOutDate(reservationRequest.getCheckOutDate());
        reservation.setPrimaryGuest(reservationRequest.getGuestsList().get(0));
        reservation.setConfirmationNumber(); // set a confirmation number: Uniqueness handled by model

        // Save reservation
        // entityManager.persist(reservation);

        // for (Guest guestRequest : reservationRequest.getGuestsList()) {
        //     // Check if the guest already exists by email (email is unique)
        //     Guest existingGuest = findGuestByEmail(guestRequest.getEmail());
        //     if (existingGuest == null) {
        //         // If guest doesn't exist, create a new one
        //         Guest newGuest = new Guest();
        //         newGuest.setName(guestRequest.getName());
        //         newGuest.setEmail(guestRequest.getEmail());
        //         newGuest.setGender(guestRequest.getGender());
        //         newGuest.setAge(guestRequest.getAge());
        //         entityManager.persist(newGuest);;
        //     } else {
        //         continue;
        //     }
        // }    

        // Set the confirmation number as a responseBody for sending response as JSON object
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("confirmationNumber", reservation.getConfirmationNumber());

        // Return confirmation number JSON Object as Response Entity
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    // Function to find guest by email from the Guests table
    // private Guest findGuestByEmail(String email) {
    //     TypedQuery<Guest> query = entityManager.createQuery(
    //             "SELECT * FROM Guests WHERE email = :email", Guest.class);
    //     query.setParameter("email", email);
    //     try {
    //         return (Guest) query.getSingleResult();
    //     } catch (Exception e) {
    //         return null; // Return null if no guest is found
    //     }
    // }
}

