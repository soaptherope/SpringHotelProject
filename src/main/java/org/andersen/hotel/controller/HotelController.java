package org.andersen.hotel.controller;

import org.andersen.hotel.exception.HotelWithIdNotFoundException;
import org.andersen.hotel.model.Hotel;
import org.andersen.hotel.service.HotelService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        Hotel savedHotel = hotelService.saveHotel(hotel);
        return ResponseEntity.ok(savedHotel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        Optional<Hotel> hotel = hotelService.getHotelById(id);
        return hotel.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Hotel>> getAllHotelsSorted(@RequestParam String sortBy) {
        try {
            Sort sort = Sort.by(sortBy);
            return ResponseEntity.ok(hotelService.getAllHotelsSorted(sort));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotelName(@PathVariable Long id, @RequestParam String newName) {
        try {
            Hotel updatedHotel = hotelService.updateHotelName(id, newName);
            return ResponseEntity.ok(updatedHotel);
        } catch (HotelWithIdNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        try {
            hotelService.deleteHotel(id);
            return ResponseEntity.noContent().build();
        } catch (HotelWithIdNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
