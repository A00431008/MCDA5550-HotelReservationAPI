package com.example.MCDA5550_HotelReservationAPI.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Reservations", uniqueConstraints = @UniqueConstraint(columnNames = {"confirmation_number"}))
public class Reservation {

    @Id
    @Column(name = "confirmation_number")
    private String confirmationNumber;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Column(name = "check_in_date")
    private String checkInDate;

    @Column(name = "check_out_date")
    private String checkOutDate;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest primaryGuest;

    // Constructor to initialize reservation from rest-controller
    public Reservation() {
    }

    // Getters and Setters
    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber() {
        this.confirmationNumber = generateConfirmationNumber();
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
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

    public Guest getPrimaryGuest() {
        return primaryGuest;
    }

    public void setPrimaryGuest(Guest primaryGuest) {
        this.primaryGuest = primaryGuest;
    }

    // Method to generate confirmation number with count suffix
    public String generateConfirmationNumber() {
        // Get the hotel ID
        int hotelId = this.hotel.getId();

        // Get the guest ID
        int guestId = this.primaryGuest.getId();

        // Get the base confirmation number
        String baseConfirmationNumber = "R" + hotelId + "G" + guestId + "-";

        // Get current date and time
        LocalDateTime now = LocalDateTime.now();

        // Format the current date and time as YYMMDDHHmmss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        String timestamp = now.format(formatter);

        // Generate confirmation number by appending timestamp
        // the format will be R<hotel_id>G<guest_id>-yyMMddHHmmss - example R1G1024-240412160000
        this.confirmationNumber = baseConfirmationNumber + timestamp;

        return this.confirmationNumber;
    }
}
