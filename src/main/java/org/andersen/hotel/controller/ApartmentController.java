package org.andersen.hotel.controller;

import org.andersen.hotel.exception.ApartmentWithIdNotFoundException;
import org.andersen.hotel.exception.IncorrectStatusOfApartmentException;
import org.andersen.hotel.exception.WrongNameForReleasingApartmentException;
import org.andersen.hotel.model.Apartment;
import org.andersen.hotel.service.ApartmentService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apartments")
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @PostMapping
    public ResponseEntity<Apartment> createApartment(@RequestBody Apartment apartment) {
        Apartment savedApartment = apartmentService.saveApartment(apartment);
        return ResponseEntity.ok(savedApartment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apartment> getApartmentById(@PathVariable Long id) {
        Optional<Apartment> apartment = apartmentService.getApartmentById(id);
        return apartment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Apartment> getAllApartments() {
        return apartmentService.getAllApartments();
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Apartment>> getAllApartmentsSorted(@RequestParam String sortBy) {
        try {
            Sort sort = Sort.by(sortBy);
            List<Apartment> sortedApartments = apartmentService.getAllApartmentsSorted(sort);
            return ResponseEntity.ok(sortedApartments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/reserve")
    public ResponseEntity<Void> reserveApartment(@PathVariable Long id, @RequestParam String nameOfClient) {
        try {
            apartmentService.reserveApartment(id, nameOfClient);
            return ResponseEntity.ok().build();
        } catch (ApartmentWithIdNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IncorrectStatusOfApartmentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/release")
    public ResponseEntity<Void> releaseApartment(@PathVariable Long id, @RequestParam String nameOfClient) {
        try {
            apartmentService.releaseApartment(id, nameOfClient);
            return ResponseEntity.ok().build();
        } catch (ApartmentWithIdNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IncorrectStatusOfApartmentException | WrongNameForReleasingApartmentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/price")
    public ResponseEntity<Apartment> updateApartmentPrice(@PathVariable Long id, @RequestParam double newPrice) {
        try {
            Apartment updatedApartment = apartmentService.updateApartmentPrice(id, newPrice);
            return ResponseEntity.ok(updatedApartment);
        } catch (ApartmentWithIdNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable Long id) {
        try {
            apartmentService.deleteApartment(id);
            return ResponseEntity.noContent().build();
        } catch (ApartmentWithIdNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
