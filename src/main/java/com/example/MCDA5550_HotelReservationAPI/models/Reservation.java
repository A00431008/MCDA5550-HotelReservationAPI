package com.example.MCDA5550_HotelReservationAPI.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 255) // Adjust length as per your requirements
    private String confirmationNumber;

    @Column(name = "hotel_id")
    private int hotel_id;

    @Column(name = "check_in_date")
    private String checkInDate;

    @Column(name = "check_out_date")
    private String checkOutDate;

    @Column(name = "primary_guest_id")
    private int primaryGuest_id;

    // Constructor to initialize reservation from rest-controller
    public Reservation() {
    }

    // Getters and Setters
    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public int getHotel() {
        return hotel_id;
    }

    public void setHotel(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getPrimaryGuest() {
        return primaryGuest_id;
    }

    public void setPrimaryGuest(int primaryGuest_Id) {
        this.primaryGuest_id = primaryGuest_Id;
    }

    // Method to generate confirmation number with count suffix
    public void generateConfirmationNumber() {
        String baseConfirmationNumber = "R" + this.hotel_id + "G" + this.primaryGuest_id + "-";

        // Get current date and time
        LocalDateTime now = LocalDateTime.now();

        // Format the current date and time as YYMMDDHHmmss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        String timestamp = now.format(formatter);

        // Generate confirmation number by appending timestamp
        // the format will be R<hotel_id>G<guest_id>-yyMMddHHmmss - example R1G1024-240412160000
        this.confirmationNumber = baseConfirmationNumber + timestamp;
    }
}
