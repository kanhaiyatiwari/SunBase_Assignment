package com.sunBase.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sunBase.model.Customer;
@Repository
public interface CustomerRepo extends JpaRepository<Customer, String> {
	
	public Optional<Customer> findByEmail(String email);
	
	  
	    @Query("SELECT c FROM Customer c WHERE LOWER(c.first_name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(c.last_name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
	    List<Customer> searchByFirstNameOrLastName(@Param("searchTerm")String searchTerm);

	    @Query("SELECT c FROM Customer c WHERE (LOWER(c.first_name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(c.last_name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND (:city IS NULL OR c.city = :city) AND (:state IS NULL OR c.state = :state) AND (:street IS NULL OR c.street = :street)")
	    List<Customer> filterAndSearchCustomers(
	        @Param("searchTerm") String searchTerm, 
	        @Param("city") String city, 
	        @Param("state") String state, 
	        @Param("street") String street
	    );

	    @Query("SELECT c FROM Customer c ORDER BY c.first_name ASC")
	    Page<Customer> findAllSortedAsc(Pageable pageable);

	    @Query("SELECT c FROM Customer c ORDER BY c.first_name DESC")
	    Page<Customer> findAllSortedDesc(Pageable pageable);
	
}
