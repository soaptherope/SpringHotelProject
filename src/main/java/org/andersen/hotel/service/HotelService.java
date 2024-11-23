package org.andersen.hotel.service;

import org.andersen.hotel.model.Hotel;

public interface HotelService extends CrudService<Hotel> {

    Hotel updateHotelName(Long id, String newName);
}
