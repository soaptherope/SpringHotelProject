package org.andersen.hotel.service;

import org.andersen.hotel.model.Hotel;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface HotelService {

    Hotel saveHotel(Hotel hotel);

    Optional<Hotel> getHotelById(Long id);

    List<Hotel> getAllHotels();

    void deleteHotel(Long id);

    boolean hotelExists(Long id);

    Hotel updateHotelName(Long id, String newName);

    List<Hotel> getAllHotelsSorted(Sort sort);
}
