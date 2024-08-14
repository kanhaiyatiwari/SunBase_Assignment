package com.sunBase.contoller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunBase.model.Customer;
import com.sunBase.service.CustomerServices;
import com.sunBase.service.SyncApiService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/sunBase")

public class CustomerController {
	
	@Autowired
	private CustomerServices customerServices;
	
	 @Autowired
	 private SyncApiService syncapiService;
	

	
		
	@GetMapping("/getCustomer/{id}")
	public ResponseEntity<Customer> getCutomerByid(@PathVariable("id") String id){
		
		
		Customer customer = customerServices.getCustomerBy_id(id);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	
	@GetMapping("/getCustomers")
	public ResponseEntity<List<Customer>> getAllCutomers(){
	
		List<Customer> customer = customerServices.CustomerList();
		return new ResponseEntity<List<Customer>>(customer, HttpStatus.OK);
	}
	
	@PostMapping("/addCustomer")
	public ResponseEntity<String> createCustomer(@Valid @RequestBody Customer customer){
		String id=UUID.randomUUID().toString();
		customer.setUuid(id);
		String string = customerServices.createCustomer(customer);
		return new ResponseEntity<String>(string, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/updateCustomer/{id}")
	public ResponseEntity<String> updateCustomer(@RequestBody Customer customer, @PathVariable("id") String id){
	
		String string = customerServices.updateCustomer(customer, id);
		return new ResponseEntity<String>(string, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteCustomer/{id}")
	 public ResponseEntity<String> deleteCustomer(@PathVariable("id") String id){
		
		
		String string = customerServices.deleteCustomer(id);
		return new ResponseEntity<String>(string, HttpStatus.OK);
	}
	



	
	 @GetMapping("/sync-customers")
	    public ResponseEntity<String> syncCustomers() {
		 
	        String result = syncapiService.authenticateFetchAndSyncCustomerList();
	        return new ResponseEntity<>(result, HttpStatus.OK);
	    }
	



	 @GetMapping("/search")
	    public ResponseEntity<Page<Customer>> searchCustomers(
	        @RequestParam(value = "searchTerm", required = false) String searchTerm,
	        @RequestParam(value = "city", required = false) String city,
	        @RequestParam(value = "state", required = false) String state,
	        @RequestParam(value = "email", required = false) String email,
	        @RequestParam(value = "page", defaultValue = "0") int page,
	        @RequestParam(value = "size", defaultValue = "10") int size,
	        @RequestParam(value = "sort", defaultValue = "first_name") String sort,
	        @RequestParam(value = "dir", defaultValue = "asc") String dir
	       
	    ) {
	        Sort.Direction direction = dir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
	        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
	        Page<Customer> customers = customerServices.searchAndFilterCustomers(searchTerm, city, state, email, pageable);
	        return new ResponseEntity<>(customers, HttpStatus.OK);
	    }

}
