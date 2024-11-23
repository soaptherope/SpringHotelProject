package org.andersen.hotel.service.impl;

import org.andersen.hotel.exception.ApartmentWithIdNotFoundException;
import org.andersen.hotel.exception.IncorrectStatusOfApartmentException;
import org.andersen.hotel.exception.WrongNameForReleasingApartmentException;
import org.andersen.hotel.model.Apartment;
import org.andersen.hotel.model.ApartmentStatusEnum;
import org.andersen.hotel.repository.ApartmentRepository;
import org.andersen.hotel.service.ApartmentService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    public Apartment saveApartment(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    @Override
    public Optional<Apartment> getApartmentById(Long id) {
        return apartmentRepository.findById(id);
    }

    @Override
    public List<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

    @Override
    public void deleteApartment(Long id) {
        apartmentRepository.findById(id)
                .ifPresentOrElse(hotel -> apartmentRepository.deleteById(id), () -> {
                    throw new ApartmentWithIdNotFoundException();
                });
    }

    @Override
    public void reserveApartment(Long id, String nameOfClient) {
        apartmentRepository.findById(id)
                .map(apartment -> {
                    if (apartment.getApartmentStatus() == ApartmentStatusEnum.FREE) {
                        apartment.setApartmentStatus(ApartmentStatusEnum.RESERVED);
                        apartment.setNameOfClient(nameOfClient);
                        apartmentRepository.save(apartment);
                        return true;
                    }
                    throw new IncorrectStatusOfApartmentException();
                })
                .orElseThrow(ApartmentWithIdNotFoundException::new);
    }

    @Override
    public void releaseApartment(Long id, String nameOfClient) {
        apartmentRepository.findById(id)
                .map(apartment -> {
                    if (apartment.getApartmentStatus() == ApartmentStatusEnum.RESERVED) {
                        if (apartment.getNameOfClient().equals(nameOfClient)) {
                            apartment.setApartmentStatus(ApartmentStatusEnum.FREE);
                            apartment.setNameOfClient(null);
                            apartmentRepository.save(apartment);
                            return true;
                        } else {
                            throw new WrongNameForReleasingApartmentException();
                        }
                    }
                    throw new IncorrectStatusOfApartmentException();
                })
                .orElseThrow(ApartmentWithIdNotFoundException::new);
    }

    @Override
    public Apartment updateApartmentPrice(Long id, double newPrice) {
        return apartmentRepository.findById(id)
                .map(apartment -> {
                    apartment.setPrice(newPrice);
                    return apartmentRepository.save(apartment);
                })
                .orElseThrow(ApartmentWithIdNotFoundException::new);
    }

    @Override
    public List<Apartment> getAllApartmentsSorted(Sort sort) {
        return apartmentRepository.findAll(sort);
    }
}
