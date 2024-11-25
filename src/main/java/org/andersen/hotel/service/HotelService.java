package org.andersen.hotel.service;

import org.andersen.hotel.model.Hotel;
import org.andersen.starter.BaseService;

public interface HotelService extends BaseService<Hotel> {

    Hotel updateHotelName(Long id, String newName);
}
