package org.andersen.hotel.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.andersen.hotel.model.Apartment;
import org.andersen.hotel.model.ApartmentStatusEnum;
import org.andersen.hotel.service.ApartmentService;
import org.andersen.starter.BaseRepository;
import org.andersen.starter.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApartmentServiceImpl extends BaseServiceImpl<Apartment> implements ApartmentService {


    public ApartmentServiceImpl(BaseRepository<Apartment> repository) {
        super(repository);
    }

    @Override
    public void reserveApartment(Long id, String nameOfClient) {
        baseRepository.findById(id)
                .map(apartment -> {
                    if (apartment.getApartmentStatus() == ApartmentStatusEnum.FREE) {
                        apartment.setApartmentStatus(ApartmentStatusEnum.RESERVED);
                        apartment.setNameOfClient(nameOfClient);
                        baseRepository.save(apartment);
                        return true;
                    }
                    throw new IllegalStateException();
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void releaseApartment(Long id, String nameOfClient) {
        baseRepository.findById(id)
                .map(apartment -> {
                    if (apartment.getApartmentStatus() == ApartmentStatusEnum.RESERVED) {
                        if (apartment.getNameOfClient().equals(nameOfClient)) {
                            apartment.setApartmentStatus(ApartmentStatusEnum.FREE);
                            apartment.setNameOfClient(null);
                            baseRepository.save(apartment);
                            return true;
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                    throw new IllegalStateException();
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Apartment updateApartmentPrice(Long id, double newPrice) {
        return baseRepository.findById(id)
                .map(apartment -> {
                    apartment.setPrice(newPrice);
                    return baseRepository.save(apartment);
                })
                .orElseThrow(EntityNotFoundException::new);
    }
}
