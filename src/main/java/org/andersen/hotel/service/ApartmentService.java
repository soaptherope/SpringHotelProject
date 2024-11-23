package org.andersen.hotel.service;

import org.andersen.hotel.model.Apartment;

public interface ApartmentService extends CrudService<Apartment> {

    void reserveApartment(Long id, String nameOfClient);

    void releaseApartment(Long id, String nameOfClient);

    Apartment updateApartmentPrice(Long id, double newPrice);
}
