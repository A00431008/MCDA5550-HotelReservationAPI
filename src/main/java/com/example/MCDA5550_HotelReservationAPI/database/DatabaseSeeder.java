package com.example.MCDA5550_HotelReservationAPI.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.MCDA5550_HotelReservationAPI.models.Hotel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DatabaseSeeder {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void seedDatabase() {
        
        Query query = entityManager.createNativeQuery("select * from hotels");
        if (query.getResultList().isEmpty()) {
            seedHotels();
        }
    }

    @Transactional
    private void seedHotels() {
        try {
            int totalHotels = 20;
            int availableHotelsCount = 15;

            String[] hotelNames = {
                "Grand Hyatt", "Marriott Marquis", "Hilton Garden Inn", "Holiday Inn Express",
                "Sheraton", "Radisson Blu", "Fairmont", "Ritz-Carlton", "Westin", "Four Seasons",
                "Waldorf Astoria", "InterContinental", "Hyatt Regency", "Courtyard by Marriott",
                "DoubleTree by Hilton", "Omni Hotels", "JW Marriott", "Wyndham", "Best Western",
                "Ramada"
            };

            // Create a list of hotels to seed
            List<Hotel> hotels = new ArrayList<>();

            // Set properties of the hotels
            for (int i = 0; i < totalHotels; i++) {
                Hotel hotel = new Hotel();
                hotel.setName(hotelNames[i % hotelNames.length]); // Use modulo to cycle through hotel names
                hotel.setPrice(getRandomPrice());
                hotel.setAvailability(i < availableHotelsCount);
                hotels.add(hotel);

            }

            // Shuffle for random availability
            Collections.shuffle(hotels);

            // insert the hotels into the database
            for (Hotel hotel : hotels) {
                entityManager.persist(hotel);
            }
        } catch (Exception e) {
            // Log or handle the exception
            System.err.println("Error occurred while seeding hotels: " + e.getMessage());
        }
    }

    private double getRandomPrice() {
        // Generate random price between $50 and $500
        double randomPrice = 50 + Math.random() * (500 - 50);
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(randomPrice));
    }
}
