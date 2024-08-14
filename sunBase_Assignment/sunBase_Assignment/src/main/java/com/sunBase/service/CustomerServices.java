package com.sunBase.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sunBase.exception.CustomerException;
import com.sunBase.model.Customer;

public interface CustomerServices {

	public Customer getCustomerBy_id(String id) throws CustomerException;

	public String createCustomer(Customer customer) throws CustomerException;

	public String syncCustomersApi(List<Customer> customer) throws CustomerException;

	public String updateCustomer(Customer customer, String id) throws CustomerException;

	public String deleteCustomer(String id) throws CustomerException;

	public List<Customer> CustomerList() throws CustomerException;

	
public 	Page<Customer> searchAndFilterCustomers(String searchTerm, String city, String state, String email,
			Pageable pageable) throws CustomerException;
}