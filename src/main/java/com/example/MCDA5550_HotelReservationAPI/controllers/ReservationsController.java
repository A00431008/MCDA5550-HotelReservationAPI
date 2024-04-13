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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ReservationsController {


    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @PostMapping("/create_reservation")
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequest reservationRequest) {
        // Validate request and send badrequest response if not valid
        if (reservationRequest == null || reservationRequest.getHotelName() == null ||
                reservationRequest.getCheckInDate() == null || reservationRequest.getCheckOutDate() == null ||
                reservationRequest.getGuestsList() == null || reservationRequest.getGuestsList().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid request body");
        }

        // Retrieve hotel based on name - Hotel names are unique as they are seeded at start of the springboot application
        TypedQuery<Hotel> query = entityManager.createQuery("SELECT h from Hotel h WHERE hotel_name= :hotelName", Hotel.class);
        query.setParameter("hotelName", reservationRequest.getHotelName());
        Hotel hotel = query.getSingleResult();
        if (hotel == null) {
            return ResponseEntity.badRequest().body("Hotel not found");
        }

        // Create reservation
        Reservation reservation = new Reservation();
        reservation.setHotel(hotel.getId());
        reservation.setCheckInDate(reservationRequest.getCheckInDate());
        reservation.setCheckOutDate(reservationRequest.getCheckOutDate());
        reservation.setPrimaryGuest(reservationRequest.getGuestsList().get(0).getId());
        reservation.generateConfirmationNumber(); // set a confirmation number: Uniqueness handled by model

        // Save reservation
        entityManager.persist(reservation);

        // save the list of guests in guests table if not present
        // then all the guests in the list to be added to the reservationguests table
        
        for (Guest guest : reservationRequest.getGuestsList()) {
            // Check if the guest already exists by email (email is unique)
            Guest existingGuest = findGuestByEmail(guest.getEmail());
            if (existingGuest == null) {
                // If guest doesn't exist, create a new one
                Guest newGuest = new Guest();
                newGuest.setName(guest.getName());
                newGuest.setEmail(guest.getEmail());
                newGuest.setGender(guest.getGender());
                newGuest.setAge(guest.getAge());
                entityManager.persist(newGuest);;   
            } 
        }    

        List<ReservationGuest> reservationGuestList = new ArrayList<>();
        for (Guest guest : reservationRequest.getGuestsList()) {
            ReservationGuest newReservationGuest = new ReservationGuest();
            newReservationGuest.setConfirmationNumber(reservation.getConfirmationNumber());
            Guest existingGuest = findGuestByEmail(guest.getEmail()); // it will return guest ID as we just persisted on database for guest
            newReservationGuest.setGuestId(existingGuest.getId());
            reservationGuestList.add(newReservationGuest);
        }

        // add reservationguest list to reservation_guest table
        // added outside of for loop to avoid Transient Object Exception
        for (ReservationGuest reservationGuest : reservationGuestList) {
            entityManager.persist(reservationGuest);
        }

        // Set the confirmation number as a responseBody for sending response as JSON object
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("confirmationNumber", reservation.getConfirmationNumber());

        // Return confirmation number JSON Object as Response Entity
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    // Function to find guest by email from the Guests table
    private Guest findGuestByEmail(String email) {
        TypedQuery<Guest> query = entityManager.createQuery(
                "SELECT g FROM Guest g WHERE g.email = :email", Guest.class);
        query.setParameter("email", email);
        try {
            return (Guest) query.getSingleResult();
        } catch (Exception e) {
            return null; // Return null if no guest is found
        }
    }
}

