package org.andersen.hotel.controller;

import org.andersen.hotel.model.Hotel;
import org.andersen.hotel.service.HotelService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        Hotel savedHotel = hotelService.save(hotel);
        return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getAll();
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Hotel>> getAllHotelsSorted(@RequestParam String sortBy) {
        Sort sort = Sort.by(sortBy);
        return ResponseEntity.ok(hotelService.getAllSorted(sort));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotelName(@PathVariable Long id, @RequestParam String newName) {
        Hotel updatedHotel = hotelService.updateHotelName(id, newName);
        return ResponseEntity.ok(updatedHotel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
