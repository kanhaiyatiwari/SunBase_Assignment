package com.sunBase.service;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.sunBase.model.Customer;

@Service
public class SyncApiService {

    private final RestTemplate restTemplate;
    private final CustomerServices customerServices;

    @Autowired
    public SyncApiService(RestTemplate restTemplate, CustomerServices customerServices) {
        this.restTemplate = restTemplate;
        this.customerServices = customerServices;
    }

    public String authenticate() {
        String url = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";

        
        Map<String, String> requestBody = Map.of(
            "login_id", "test@sunbasedata.com",
            "password", "Test@123"
        );

       
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

   
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

       
        if (response.getBody() != null) {
            return (String) response.getBody().get("access_token");
        }
        return null;
    }

    public List<Customer> getCustomerList(String token) {
        String url = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp";

       
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("cmd", "get_customer_list");

        
        HttpEntity<Void> entity = new HttpEntity<>(headers);

      
        ResponseEntity<Customer[]> response = restTemplate.exchange(
            builder.toUriString(), 
            HttpMethod.GET, 
            entity, Customer[].class
        );

       
        return Arrays.asList(response.getBody());
    }

    public String authenticateFetchAndSyncCustomerList() {
        String token = authenticate();
        if (token != null) {
            List<Customer> customerList = getCustomerList(token);
            return customerServices.syncCustomersApi(customerList);
        } else {
            throw new RuntimeException("Failed to authenticate and retrieve token");
        }
    }
}
