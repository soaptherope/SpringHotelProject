package org.andersen.hotel.service;

import org.andersen.hotel.model.Apartment;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ApartmentService {

    Apartment saveApartment(Apartment apartment);

    Optional<Apartment> getApartmentById(Long id);

    List<Apartment> getAllApartments();

    void deleteApartment(Long id);

    void reserveApartment(Long id, String nameOfClient);

    void releaseApartment(Long id, String nameOfClient);

    Apartment updateApartmentPrice(Long id, double newPrice);

    List<Apartment> getAllApartmentsSorted(Sort sort);
}
