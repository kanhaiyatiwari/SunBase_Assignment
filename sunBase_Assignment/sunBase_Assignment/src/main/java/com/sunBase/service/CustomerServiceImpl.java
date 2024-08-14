package com.sunBase.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sunBase.exception.CustomerException;
import com.sunBase.model.Customer;
import com.sunBase.repository.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerServices{
	
	@Autowired
	private CustomerRepo customerRepo;
	
	

	@Override
	public Customer getCustomerBy_id(String id){
		
		Optional<Customer> customer = customerRepo.findById(id);
		if(customer.isEmpty()) {
			throw new CustomerException("This costomer is not present in database");
		}
		
		return customer.get();
	}

	@Override
	public String createCustomer(Customer customer) {
	
		Optional<Customer> c = customerRepo.findById(customer.getUuid());
		if(!c.isEmpty()) {
			throw new CustomerException("This costomer is allready present in database");
		}
		
		Customer savedCustomer = customerRepo.save(customer);
		if(savedCustomer == null) {
			throw new CustomerException("Failed to add customer.");
		}
		 return "Customer added successfully.";
	}

	@Override
	public String updateCustomer(Customer customer, String id) {
		 Customer existingCustomer = customerRepo.findById(id)
	                .orElseThrow(() -> new CustomerException("Customer not found with ID: " + id));

		 updateCustomerDetails(existingCustomer, customer);

	        Customer updatedCustomer = customerRepo.save(existingCustomer);
	        if (updatedCustomer == null) {
	            throw new CustomerException("Failed to update customer.");
	        }
	        return "Customer updated successfully.";
	    }

	    private void updateCustomerDetails(final Customer existingCustomer, final Customer newDetails) {
	        if (newDetails.getFirst_name() != null && !newDetails.getFirst_name().isBlank()) {
	            existingCustomer.setFirst_name(newDetails.getFirst_name());
	        }
	        if (newDetails.getFirst_name() != null && !newDetails.getFirst_name().isBlank()) {
	            existingCustomer.setFirst_name(newDetails.getFirst_name());
	        }
	        if (newDetails.getAddress() != null && !newDetails.getAddress().isBlank()) {
	            existingCustomer.setAddress(newDetails.getAddress());
	        }
	        if (newDetails.getCity() != null && !newDetails.getCity().isBlank()) {
	            existingCustomer.setCity(newDetails.getCity());
	        }
	        if (newDetails.getState() != null && !newDetails.getState().isBlank()) {
	            existingCustomer.setState(newDetails.getState());
	        }
	        if (newDetails.getStreet() != null && !newDetails.getStreet().isBlank()) {
	            existingCustomer.setStreet(newDetails.getStreet());
	        }
	        if (newDetails.getPhone() != null && !newDetails.getPhone().isBlank()) {
	            existingCustomer.setPhone(newDetails.getPhone());
	        }
	        if (newDetails.getEmail() != null && !newDetails.getEmail().isBlank()) {
	            existingCustomer.setEmail(newDetails.getEmail());
	        }
	    }

	    
	    
	    
	@Override
	public String deleteCustomer(final String id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new CustomerException("Customer not found with ID: " + id));
        
        customerRepo.deleteById(id);
        return "Customer deleted successfully.";
    }

	@Override
	public List<Customer> CustomerList() throws  CustomerException{
		 List<Customer> customers = customerRepo.findAll();
	        if (customers.isEmpty()) {
	            throw new CustomerException("No customers found.");
	        }
	        return customers;
	}

	@Override
	public String syncCustomersApi(List<Customer> customers) throws CustomerException {
	
		 for (Customer customer : customers) {
	            Optional<Customer> existingCustomer = customerRepo.findById(customer.getUuid());
	            if (existingCustomer.isPresent()) {
	                updateCustomerDetails(existingCustomer.get(), customer);
	                customerRepo.save(existingCustomer.get());
	            } else {
	                customerRepo.save(customer);
	            }
	        }
	        return "Customers synced successfully!";
				
	}



	@Override
	public Page<Customer> searchAndFilterCustomers(String searchTerm, String city, String state, String email, Pageable pageable) throws CustomerException {
        Page<Customer> customers = customerRepo.filterAndSearchCustomers(searchTerm, city, state, email, pageable);
        if (customers.isEmpty()) {
            throw new CustomerException("No customers found matching the given criteria.");
        }
        return customers;
    }
}
