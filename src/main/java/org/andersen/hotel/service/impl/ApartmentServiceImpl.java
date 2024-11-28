package org.andersen.hotel.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.andersen.hotel.model.Apartment;
import org.andersen.hotel.model.ApartmentStatusEnum;
import org.andersen.hotel.repository.ApartmentRepository;
import org.andersen.hotel.service.ApartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApartmentServiceImpl extends CrudServiceImpl<Apartment> implements ApartmentService {

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository) {
        super(apartmentRepository);
    }

    @Override
    public void reserveApartment(Long id, String nameOfClient) {
        repository.findById(id)
                .map(apartment -> {
                    if (apartment.getApartmentStatus() == ApartmentStatusEnum.FREE) {
                        apartment.setApartmentStatus(ApartmentStatusEnum.RESERVED);
                        apartment.setNameOfClient(nameOfClient);
                        repository.save(apartment);
                        return true;
                    }
                    throw new IllegalStateException("Apartment is already reserved.");
                })
                .orElseThrow(() -> new EntityNotFoundException("Apartment not found with ID: " + id));
    }

    @Override
    public void releaseApartment(Long id, String nameOfClient) {
        repository.findById(id)
                .map(apartment -> {
                    if (apartment.getApartmentStatus() == ApartmentStatusEnum.RESERVED) {
                        if (apartment.getNameOfClient().equals(nameOfClient)) {
                            apartment.setApartmentStatus(ApartmentStatusEnum.FREE);
                            apartment.setNameOfClient(null);
                            repository.save(apartment);
                            return true;
                        } else {
                            throw new IllegalArgumentException("Client name does not match.");
                        }
                    }
                    throw new IllegalStateException("Apartment is not reserved.");
                })
                .orElseThrow(() -> new EntityNotFoundException("Apartment not found with ID: " + id));
    }

    @Override
    public Apartment updateApartmentPrice(Long id, double newPrice) {
        return repository.findById(id)
                .map(apartment -> {
                    apartment.setPrice(newPrice);
                    return repository.save(apartment);
                })
                .orElseThrow(() -> new EntityNotFoundException("Apartment not found with ID: " + id));
    }
}
