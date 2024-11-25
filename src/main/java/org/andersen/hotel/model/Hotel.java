package org.andersen.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.andersen.starter.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels")
@NoArgsConstructor
@Getter
@Setter
public class Hotel extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Apartment> apartments = new ArrayList<>();

    public Hotel(String name) {
        this.name = name;
    }
}
