package com.turkcellcamp.rentacar.entities.concretes;

import java.time.LocalDate;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rental_cars")
@Entity
public class RentalCar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rental_car_id")
	private int rentalCarId;

	@Column(name = "rent_date")
	private LocalDate rentDate;

	@Column(name = "return_date")
	private LocalDate returnDate;

	@Column(name = "total_daily_price")
	private double totalPrice;
	
    @Column(name = "starting_kilometer")
    private double startingKilometer;

    @Column(name = "return_kilometer")
    private double returnKilometer;

	@ManyToOne
	@JoinColumn(name = "rent_city_id")
	private City rentCity;

	@ManyToOne
	@JoinColumn(name = "return_city_id")
	private City returnCity;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;

	@OneToMany(mappedBy = "rentalCar")
	private List<OrderedAdditionalService> orderedAdditionalService;
	
	@OneToOne(mappedBy = "rentalCar")
	private Invoice invoice;
}
