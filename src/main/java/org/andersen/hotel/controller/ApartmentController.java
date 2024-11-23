package org.andersen.hotel.controller;

import org.andersen.hotel.model.Apartment;
import org.andersen.hotel.service.ApartmentService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apartments")
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @PostMapping
    public ResponseEntity<Apartment> createApartment(@RequestBody Apartment apartment) {
        Apartment savedApartment = apartmentService.save(apartment);
        return new ResponseEntity<>(savedApartment, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Apartment> getAllApartments() {
        return apartmentService.getAll();
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Apartment>> getAllApartmentsSorted(@RequestParam String sortBy) {
        Sort sort = Sort.by(sortBy);
        return ResponseEntity.ok(apartmentService.getAllSorted(sort));
    }

    @PutMapping("/{id}/reserve")
    public ResponseEntity<Void> reserveApartment(@PathVariable Long id, @RequestParam String nameOfClient) {
        apartmentService.reserveApartment(id, nameOfClient);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/release")
    public ResponseEntity<Void> releaseApartment(@PathVariable Long id, @RequestParam String nameOfClient) {
        apartmentService.releaseApartment(id, nameOfClient);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/price")
    public ResponseEntity<Apartment> updateApartmentPrice(@PathVariable Long id, @RequestParam double newPrice) {
        Apartment updatedApartment = apartmentService.updateApartmentPrice(id, newPrice);
        return ResponseEntity.ok(updatedApartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable Long id) {
        apartmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
