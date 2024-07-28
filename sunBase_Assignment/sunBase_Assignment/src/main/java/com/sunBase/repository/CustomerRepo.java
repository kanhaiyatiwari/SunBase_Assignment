package com.sunBase.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sunBase.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, String> {
	
	public Optional<Customer> findByEmail(String email);
}
