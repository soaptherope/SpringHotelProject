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

    boolean apartmentExists(Long id);

    boolean reserveApartment(Long id);

    boolean releaseApartment(Long id);

    Apartment updateApartmentPrice(Long id, double newPrice);

    List<Apartment> getAllApartmentsSorted(Sort sort);
}
