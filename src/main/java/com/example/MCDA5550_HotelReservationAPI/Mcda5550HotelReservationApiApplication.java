package com.example.MCDA5550_HotelReservationAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.MCDA5550_HotelReservationAPI.database.DatabaseSeeder;

@SpringBootApplication
public class Mcda5550HotelReservationApiApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Mcda5550HotelReservationApiApplication.class, args);
        DatabaseSeeder seeder = context.getBean(DatabaseSeeder.class);
        seeder.seedDatabase();
	}

}
