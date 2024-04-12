package com.example.MCDA5550_HotelReservationAPI.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Hotels")
public class Hotel {

    @Id
    private String id; // Using hotel code as the ID

    private String name;
    private double price;
    private boolean availability;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    // Generate hotel code based on the first letter of each word in the hotel name
    // combined with a unique identifier
    public void generateHotelCode() {
        StringBuilder codeBuilder = new StringBuilder();

        // Take the first letter of each word in the hotel name
        String[] words = this.name.split("\\s+");
        for (String word : words) {
            codeBuilder.append(word.charAt(0));
        }

        // If the hotel name has fewer than 5 words, append 'X' to meet the 5-character length
        while (codeBuilder.length() < 5) {
            codeBuilder.append("X");
        }

        // Truncate the code to 5 characters
        String generatedCode = codeBuilder.substring(0, 5).toUpperCase();

        // Append a unique identifier to the code (e.g., hotel ID)
        // For simplicity, let's use the ID, but in a real-world scenario, you might want to
        // use a hash or other unique identifier
        generatedCode += this.id;

        // Set the generated code as the ID
        this.id = generatedCode;
    }
}
