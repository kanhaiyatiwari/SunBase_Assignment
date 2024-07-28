package com.sunBase.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Customer {
	
	@Id
	
	private String uuid; 
	
	private String first_name;
	
	private String last_name;
	
	private String street;
	
	private String adderess;
	
	private String city;
	
	private String state;
	@Column(unique = true)
	@NotBlank
	@NotNull
	private String email;
	
	private String phone;
	
	private String password;
}
