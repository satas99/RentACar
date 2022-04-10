package com.turkcellcamp.rentacar.dataaccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.entities.concretes.Invoice;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Integer>{
	Invoice getByInvoiceId(int id);
	List<Invoice> getByCustomer_customerId(int id);
	List<Invoice> findByCreateDateBetween(LocalDate startDate, LocalDate finishDate);
	boolean existsByInvoiceNo(String invoiceNo);
	List<Invoice>getByRentalCar_rentalCarId(int id);
}
