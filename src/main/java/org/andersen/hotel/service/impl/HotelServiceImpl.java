package org.andersen.hotel.service.impl;

import org.andersen.hotel.exception.HotelWithIdNotFoundException;
import org.andersen.hotel.model.Hotel;
import org.andersen.hotel.repository.HotelRepository;
import org.andersen.hotel.service.HotelService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Optional<Hotel> getHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public void deleteHotel(Long id) {
        hotelRepository.findById(id)
                .ifPresentOrElse(hotel -> hotelRepository.deleteById(id), () -> {
                    throw new HotelWithIdNotFoundException();
                });
    }


    @Override
    public boolean hotelExists(Long id) {
        return hotelRepository.existsById(id);
    }

    @Override
    public Hotel updateHotelName(Long id, String newName) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    hotel.setName(newName);
                    return hotelRepository.save(hotel);
                })
                .orElseThrow(HotelWithIdNotFoundException::new);
    }

    @Override
    public List<Hotel> getAllHotelsSorted(Sort sort) {
        return hotelRepository.findAll(sort);
    }
}
