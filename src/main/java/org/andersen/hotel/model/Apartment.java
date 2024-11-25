package org.andersen.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.andersen.starter.BaseEntity;

@Entity
@Table(name = "apartments")
@NoArgsConstructor
@Getter
@Setter
public class Apartment extends BaseEntity {

    @Column(name = "price", nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "apartment_status", nullable = false)
    private ApartmentStatusEnum apartmentStatus;

    @Column(name = "client_name", length = 40)
    private String nameOfClient;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public Apartment(double price) {
        this.price = price;
        this.apartmentStatus = ApartmentStatusEnum.FREE;
    }
}
