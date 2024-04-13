package com.example.MCDA5550_HotelReservationAPI.models;

import jakarta.persistence.*;

@Entity
@Table(name = "reservation_guests")
public class ReservationGuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "confirmation_number")
    private String confirmationNumber;

    @Column(name = "guest_id")
    private int guest_id;

    // Constructor
    public ReservationGuest() {
    }

    // Getter method for id
    public int getId() {
        return id;
    }

    // Setter method for id
    public void setId(int id) {
        this.id = id;
    }

    // Getters and Setters
    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public int getGuestId() {
        return guest_id;
    }

    public void setGuestId(int guest_id) {
        this.guest_id = guest_id;
    }
}
