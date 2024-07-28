package com.sunBase.jwtConfig;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.sunBase.model.Customer;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenGeneratorFilter {


	public String tokenGerneratorForCustomer(Customer customer) {

		Date now = new Date();
		Date expirationDate = new Date(now.getTime() + 3600 * 1000); // 1 hour

		// Generate the token
		String token = Jwts.builder().setSubject("customer")
				.claim("customertId", customer.getUuid()).claim("mobileNumber", customer.getEmail())
				.claim("name", customer.getFirst_name()).setIssuedAt(now).setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS256, SecurityConstants.JWT_KEY_CUSTOMER).compact();

		// Print the generated token
		System.out.println(token);
		return token;
	}

}
