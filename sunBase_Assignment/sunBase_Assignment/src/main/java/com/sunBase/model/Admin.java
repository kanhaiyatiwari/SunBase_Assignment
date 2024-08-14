package com.sunBase.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
	   @Id
	 
	  private String  uuid;
	    
	    @NotNull
	    @NotEmpty
	    @Column(unique = true,name="login_id")
	    private String  loginId;
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	    private String password;
}
