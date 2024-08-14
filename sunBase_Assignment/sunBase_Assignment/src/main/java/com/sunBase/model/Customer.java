package com.sunBase.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
	@NotEmpty(message = "first name should be not Empty and not null! ")
	@NotNull
	@JsonProperty("first_name")
	private String first_name;
	@NotNull
	@NotEmpty(message = "Last name should be not Empty and not null! ")
	@JsonProperty("last_name")
	private String last_name;
	@NotNull
	@NotEmpty(message = "Last name should be not Empty and not null!")
	private String street;
	@NotNull
	@NotEmpty(message = "Last name should be not Empty and not null!")
	private String address;
	@NotNull
	@NotEmpty(message = "Last name should be not Empty and not null!")
	private String city;
	@NotNull
	@NotEmpty(message = "Last name should be not Empty and not null!")
	private String state;
	@Email(message = "please write email format and not null!")
	private String email;
	@NotNull
	@NotEmpty(message = "Last name should be not Empty and not null!")
	private String phone;
}
