package com.example.MCDA5550_HotelReservationAPI.models;

import jakarta.persistence.*;

@Entity
@Table(name = "guests")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id")
    private int guest_id; // Using auto-incrementing primary key

    @Column(name = "guest_name")
    private String guest_name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private int age;

    public int getId() {
        return guest_id;
    }

    public void setId(int guestId) {
        this.guest_id = guestId;
    }

    public String getName() {
        return guest_name;
    }

    public void setName(String name) {
        this.guest_name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
