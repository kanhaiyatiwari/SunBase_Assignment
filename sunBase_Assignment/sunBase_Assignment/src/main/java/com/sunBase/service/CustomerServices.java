package com.sunBase.service;

import java.util.List;

import com.sunBase.exception.CustomerException;
import com.sunBase.exception.TokenException;
import com.sunBase.model.Customer;
import com.sunBase.model.LoginDTO;

public interface CustomerServices {
	
	public Customer getCustomerBy_id(String id,String jwtToken) throws CustomerException;
	
	public String createCustomer(Customer customer) throws CustomerException;
	public String syncCustomersApi(List<Customer> customer) throws CustomerException;
	public String updateCustomer(Customer customer, String id,String jwtToken) throws CustomerException;
	
	public String deleteCustomer(String id ,String token) throws CustomerException;
	public List<Customer>CustomerList(String jwtToken)throws CustomerException,TokenException;
	
	public String forLoginCustomer(LoginDTO loginDTO) throws CustomerException, TokenException;
	
	public Customer currentCustomer(String token) throws CustomerException, TokenException;
	
}