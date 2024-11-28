package org.andersen.hotel.repository;

import org.andersen.hotel.model.Hotel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HotelRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("hotel_database")
            .withUsername("hotel_admin")
            .withPassword("hotel_password");

    @Autowired
    private HotelRepository hotelRepository;

    @BeforeAll
    static void beforeAll() {
        postgresContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgresContainer.stop();
    }

    @Test
    void saveApartment() {
        Hotel hotel = new Hotel("Test Hotel");

        Hotel savedHotel = hotelRepository.save(hotel);

        Hotel foundHotel = hotelRepository.findById(savedHotel.getId()).orElseThrow();
        assertThat(foundHotel).isNotNull();
        assertThat(foundHotel.getName()).isEqualTo("Test Hotel");
    }

    @Test
    void updateApartment() {
        Hotel hotel = new Hotel("Test Hotel");

        Hotel savedHotel = hotelRepository.save(hotel);

        savedHotel.setName("Updated Hotel");
        Hotel updatedHotel = hotelRepository.save(savedHotel);

        Hotel foundHotel = hotelRepository.findById(updatedHotel.getId()).orElseThrow();
        assertThat(foundHotel.getName()).isEqualTo("Updated Hotel");
    }

    @Test
    void deleteApartment() {
        Hotel hotel = new Hotel("Test Hotel");

        Hotel savedHotel = hotelRepository.save(hotel);
        hotelRepository.delete(savedHotel);

        boolean exists = hotelRepository.existsById(savedHotel.getId());
        assertThat(exists).isFalse();
    }
}
