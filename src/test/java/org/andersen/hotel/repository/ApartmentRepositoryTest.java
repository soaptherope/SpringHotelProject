package org.andersen.hotel.repository;

import org.andersen.hotel.model.Apartment;
import org.andersen.hotel.model.ApartmentStatusEnum;
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
public class ApartmentRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("hotel_database")
            .withUsername("hotel_admin")
            .withPassword("hotel_password");

    @Autowired
    private ApartmentRepository apartmentRepository;

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
        Apartment apartment = new Apartment(100);
        apartment.setApartmentStatus(ApartmentStatusEnum.FREE);

        Apartment savedApartment = apartmentRepository.save(apartment);

        Apartment foundApartment = apartmentRepository.findById(savedApartment.getId()).orElseThrow();
        assertThat(foundApartment).isNotNull();
        assertThat(foundApartment.getApartmentStatus()).isEqualTo(ApartmentStatusEnum.FREE);
    }

    @Test
    void updateApartment() {
        Apartment apartment = new Apartment(100);
        apartment.setApartmentStatus(ApartmentStatusEnum.FREE);

        Apartment savedApartment = apartmentRepository.save(apartment);

        savedApartment.setApartmentStatus(ApartmentStatusEnum.RESERVED);
        Apartment updatedApartment = apartmentRepository.save(savedApartment);

        Apartment foundApartment = apartmentRepository.findById(updatedApartment.getId()).orElseThrow();
        assertThat(foundApartment.getApartmentStatus()).isEqualTo(ApartmentStatusEnum.RESERVED);
    }

    @Test
    void deleteApartment() {
        Apartment apartment = new Apartment(100);
        apartment.setApartmentStatus(ApartmentStatusEnum.FREE);

        Apartment savedApartment = apartmentRepository.save(apartment);
        apartmentRepository.delete(savedApartment);

        boolean exists = apartmentRepository.existsById(savedApartment.getId());
        assertThat(exists).isFalse();
    }
}
