package com.turkcellcamp.rentacar.entities.concretes;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ordered_additional_services")
public class OrderedAdditionalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordered_additional_service_id")
    private int orderedAdditionalServiceId;

    @ManyToOne()
    @JoinColumn(name = "additional_service_id")
    private AdditionalService additionalService;
    

    @ManyToOne()
    @JoinColumn(name = "rental_car_id")
    private RentalCar rentalCar;



}