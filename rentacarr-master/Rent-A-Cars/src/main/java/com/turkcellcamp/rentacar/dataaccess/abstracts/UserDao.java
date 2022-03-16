package com.turkcellcamp.rentacar.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.entities.concretes.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
	User getByUserId(int id);
}
