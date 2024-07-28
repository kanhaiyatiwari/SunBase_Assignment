package com.sunBase.service;

import java.util.List;
import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunBase.exception.CustomerException;
import com.sunBase.exception.TokenException;
import com.sunBase.jwtConfig.JwtTokenGeneratorFilter;
import com.sunBase.jwtConfig.JwtTokenValidatorFilter;
import com.sunBase.model.Customer;
import com.sunBase.model.LoginDTO;
import com.sunBase.repository.CustomerRepo;

import io.jsonwebtoken.Claims;

@Service
public class CustomerServiceImpl implements CustomerServices{
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private JwtTokenGeneratorFilter jwtTokenGeneratorFilter;
	
	@Autowired
	private JwtTokenValidatorFilter jwtTokenValidatorFilter;

	@Override
	public Customer getCustomerBy_id(String id,String jwtToken){
		Claims claims = jwtTokenValidatorFilter.tokenValidatingforCustomar(jwtToken);
		if (claims == null) {
			throw new TokenException("Please login");
		}
		// TODO Auto-generated method stub
		Optional<Customer> customer = customerRepo.findById(id);
		if(customer.isEmpty()) {
			throw new CustomerException("This costomer is not present in database");
		}
		
		return customer.get();
	}

	@Override
	public String createCustomer(Customer customer) {
		// TODO Auto-generated method stub
		Optional<Customer> c = customerRepo.findByEmail(customer.getEmail());
		if(!c.isEmpty()) {
			throw new CustomerException("This costomer is allready present in database");
		}
		
		Customer customer2 =  customerRepo.save(customer);
		if(customer2 == null) {
			throw new CustomerException("This customer not added ");
		}
		return "customer added successfully";
	}

	@Override
	public String updateCustomer(Customer customer, String id,String jwtToken) {
		Claims claims = jwtTokenValidatorFilter.tokenValidatingforCustomar(jwtToken);
		if (claims == null) {
			throw new TokenException("Please login");
		}
		// TODO Auto-generated method stub
		Optional<Customer> c = customerRepo.findById(id);
		if(c.isEmpty()) {
			throw new CustomerException("This costomer is not present in database");
		}
		
		if(customer == null) {
			throw new CustomerException("This customer  is not vaild");
		}
		c.get().setFirst_name(customer.getFirst_name());
		c.get().setLast_name(customer.getLast_name());
		c.get().setAdderess(customer.getAdderess());
		c.get().setCity(customer.getCity());
		c.get().setState(customer.getState());
		c.get().setStreet(customer.getStreet());
		c.get().setPhone(customer.getAdderess());
		c.get().setEmail(customer.getEmail());
		
		Customer customer2 =  customerRepo.save(c.get());
		if(customer2 == null) {
			throw new CustomerException("This customer not updated ");
		}
		
		
		return "customer updated successfully";
	}

	@Override
	public String deleteCustomer(String id,String token) {
		// TODO Auto-generated method stub
		Claims claims = jwtTokenValidatorFilter.tokenValidatingforCustomar(token);
		if (claims == null) {
			throw new TokenException("Please login");
		}
		Optional<Customer> c = customerRepo.findById(id);
		if(c.isEmpty()) {
			throw new CustomerException("This costomer is not present in database");
		}
		
        customerRepo.deleteById(id);
        
        return "customer deletd successfully";
	}

	@Override
	public String forLoginCustomer(LoginDTO loginDTO) throws CustomerException, TokenException {
		
		Optional<Customer> customer = customerRepo.findByEmail(loginDTO.getEmail());

		if (customer.isEmpty())
			throw new CustomerException("Please enter a valid email");

		if ( !customer.get().getPassword().equals(loginDTO.getPassword())) {
			throw new CustomerException("Invalid Password...");
		}

		String token = jwtTokenGeneratorFilter.tokenGerneratorForCustomer(customer.get());

		return token;
	}

	@Override
	public Customer currentCustomer(String token) throws CustomerException, TokenException {
		Claims claims = jwtTokenValidatorFilter.tokenValidatingforCustomar(token);
		if (claims == null) {
			throw new TokenException("Please login");
		}
		Customer existingCustomer = customerRepo.findById(claims.get("customertId", String.class))
				.orElseThrow(() -> new CustomerException("Please login as Customer"));
		
		return existingCustomer;
	}

	@Override
	public List<Customer> CustomerList(String jwtToken) throws  CustomerException, TokenException {
		Claims claims = jwtTokenValidatorFilter.tokenValidatingforCustomar(jwtToken);
		if (claims == null) {
			throw new TokenException("Please login");
		}
		// TODO Auto-generated method stub
		return customerRepo.findAll();
	}

	@Override
	public String syncCustomersApi(List<Customer> customers) throws CustomerException {
		// TODO Auto-generated method stub
		
		
		for (Customer customer : customers) {
            Optional<Customer> existingCustomer = customerRepo.findByEmail(customer.getEmail());
            if (existingCustomer.isPresent()) {
                // Update existing customer
                Customer updateCustomer = existingCustomer.get();
                updateCustomer.setFirst_name(customer.getFirst_name());
                updateCustomer.setLast_name(customer.getLast_name());
                updateCustomer.setStreet(customer.getStreet());
                updateCustomer.setAdderess(customer.getAdderess());
                updateCustomer.setCity(customer.getCity());
                updateCustomer.setState(customer.getState());
                updateCustomer.setPhone(customer.getPhone());
                customerRepo.save(updateCustomer);
            } else {
                // Insert new customer
                customerRepo.save(customer);
            }
        }
		return "Customers synced successfully!";
				
	}

}
