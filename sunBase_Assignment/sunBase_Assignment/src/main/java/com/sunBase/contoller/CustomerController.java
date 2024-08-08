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
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.sunBase.model.LoginDTO;
import com.sunBase.service.ApiService;
import com.sunBase.service.CustomerServices;

@RestController
@RequestMapping(value = "/api/sunBase")
@CrossOrigin(value = "*")
public class CustomerController {
	
	@Autowired
	private CustomerServices customerServices;
	
	 @Autowired
	 private ApiService apiService;
	

	@GetMapping("/getCustomer/{id}")
	public ResponseEntity<Customer> getCutomerByid(@PathVariable("id") String id,@RequestHeader("Authorization") String authorizationHeader){
		String jwtToken = extractJwtToken(authorizationHeader);
		
		Customer customer = customerServices.getCustomerBy_id(id,jwtToken);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	
	@GetMapping("/getCustomers")
	public ResponseEntity<List<Customer>> getAllCutomers(@RequestHeader("Authorization") String authorizationHeader){
		String jwtToken = extractJwtToken(authorizationHeader);
		List<Customer> customer = customerServices.CustomerList(jwtToken);
		return new ResponseEntity<List<Customer>>(customer, HttpStatus.OK);
	}
	
	@PostMapping("/addCustomer")
	public ResponseEntity<String> createCustomer(@RequestBody Customer customer){
		String id=UUID.randomUUID().toString();
		customer.setUuid(id);
		String string = customerServices.createCustomer(customer);
		return new ResponseEntity<String>(string, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/updateCustomer/{id}")
	public ResponseEntity<String> updateCustomer(@RequestBody Customer customer, @PathVariable("id") String id,@RequestHeader("Authorization") String authorizationHeader){
		String jwtToken = extractJwtToken(authorizationHeader);
		String string = customerServices.updateCustomer(customer, id,jwtToken);
		return new ResponseEntity<String>(string, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteCustomer/{id}")
	 public ResponseEntity<String> deleteCustomer(@PathVariable("id") String id,@RequestHeader("Authorization") String authorizationHeader){
		String jwtToken = extractJwtToken(authorizationHeader);
		
		String string = customerServices.deleteCustomer(id,jwtToken );
		return new ResponseEntity<String>(string, HttpStatus.OK);
	}
	
	@PostMapping("/loginCustomer")
	public ResponseEntity<String> loginCustomer(@RequestBody LoginDTO loginDTO){
		
		String string = customerServices.forLoginCustomer(loginDTO);
		
		return new ResponseEntity<String>(string, HttpStatus.OK);
	}
	
	@GetMapping("/currentCustomer")
	public ResponseEntity<Customer> currentCustomer(@RequestHeader("Authorization") String authorizationHeader){
		String jwtToken = extractJwtToken(authorizationHeader);
		Customer customer = customerServices.currentCustomer(jwtToken);

		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	public ResponseEntity<List<Customer>> customers;
	
	private String extractJwtToken(String authorizationHeader) {
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7);
		}
		return null;
	}
	
	
	
	
	
	 @GetMapping("/sync-customers")
	    public ResponseEntity<String> syncCustomers(@RequestHeader("Authorization") String authorizationHeader) {
		 String jwtToken = extractJwtToken(authorizationHeader);
	        String result = apiService.authenticateFetchAndSyncCustomerList(jwtToken);
	        return new ResponseEntity<>(result, HttpStatus.OK);
	    }
	

	
	

    @GetMapping("/search")
    public Page<Customer> searchCustomers(
        @RequestParam(value = "searchTerm", required = false) String searchTerm,
        @RequestParam(value = "city", required = false) String city,
        @RequestParam(value = "state", required = false) String state,
        @RequestParam(value = "street", required = false) String street,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "sort", defaultValue = "first_name") String sort,
        @RequestParam(value = "dir", defaultValue = "asc") String dir,
        @RequestHeader("Authorization") String authorizationHeader
    ) {
        Sort.Direction direction = dir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        String jwtToken = extractJwtToken(authorizationHeader);
        return customerServices.searchAndFilterCustomers(searchTerm, city, state, street, pageable,jwtToken);
    }
  
    
    
}
