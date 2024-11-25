package org.andersen.hotel.service;

import org.andersen.hotel.model.Apartment;
import org.andersen.starter.BaseService;

public interface ApartmentService extends BaseService<Apartment> {

    void reserveApartment(Long id, String nameOfClient);

    void releaseApartment(Long id, String nameOfClient);

    Apartment updateApartmentPrice(Long id, double newPrice);
}
