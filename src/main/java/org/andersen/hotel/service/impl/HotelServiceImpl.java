package org.andersen.hotel.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.andersen.hotel.model.Hotel;
import org.andersen.hotel.repository.HotelRepository;
import org.andersen.hotel.service.HotelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HotelServiceImpl extends CrudServiceImpl<Hotel> implements HotelService {

    public HotelServiceImpl(HotelRepository hotelRepository) {
        super(hotelRepository);
    }

    @Override
    public Hotel updateHotelName(Long id, String newName) {
        return repository.findById(id)
                .map(hotel -> {
                    hotel.setName(newName);
                    return repository.save(hotel);
                })
                .orElseThrow(EntityNotFoundException::new);
    }
}
