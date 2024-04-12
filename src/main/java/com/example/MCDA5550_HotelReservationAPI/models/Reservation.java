package com.example.MCDA5550_HotelReservationAPI.models;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

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

    // Constructors
    public Reservation() {
    }

    public Reservation(String confirmationNumber, Hotel hotel, String checkInDate, String checkOutDate, Guest primaryGuest) {
        this.confirmationNumber = confirmationNumber;
        this.hotel = hotel;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.primaryGuest = primaryGuest;
    }

    // Getters and Setters
    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
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

    // Autowire the ReservationRepository
    @Autowired
    private EntityManager entityManager;

    // Method to generate confirmation number with count suffix
    public String generateConfirmationNumber() {
        // Get the hotel ID
        int hotelId = this.hotel.getId();

        // Get the guest ID
        int guestId = this.primaryGuest.getId();

        // Get the base confirmation number
        String baseConfirmationNumber = "RH" + hotelId + "G" + guestId + "-";

        // Count the number of existing reservations with the same base confirmation number prefix
        long count = countByConfirmationNumberStartingWith(baseConfirmationNumber);

        // Generate confirmation number by appending count suffix
        this.confirmationNumber = baseConfirmationNumber + (count + 1);

        return this.confirmationNumber;
    }

    // Method to count reservations by confirmation number prefix
    private long countByConfirmationNumberStartingWith(String confirmationNumberPrefix) {
        Query query = entityManager.createQuery("SELECT COUNT(r) FROM Reservation r WHERE r.confirmationNumber LIKE :prefix");
        query.setParameter("prefix", confirmationNumberPrefix + "%");
        return (long) query.getSingleResult();
    }
}
