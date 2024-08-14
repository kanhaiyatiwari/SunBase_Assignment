package com.sunBase.contoller;


import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunBase.exception.AdminException;
import com.sunBase.model.Admin;
import com.sunBase.repository.AdminRepository;

@RestController
@RequestMapping(value = "/api/sunBase/auth")

public class AuthenticationController {
@Autowired
	private AdminRepository  adminRepository;
@Autowired
private PasswordEncoder passwordencoder;
@GetMapping("/login")
public ResponseEntity<String>   logInuser(Authentication auth){
	Optional<Admin> admin=  adminRepository.findByLoginId(auth.getName());
	if(admin.isEmpty())throw new  BadCredentialsException("invalid user details !");
	return new  ResponseEntity<>("user loggedin succesfully !",HttpStatus.OK);
}

@PostMapping("/singup")
public ResponseEntity<String>   singupuser(@RequestBody Admin admin){
	
	if(admin==null)throw new AdminException("can not register with empty data !");
	String id=UUID.randomUUID().toString();
	admin.setUuid(id);
	admin.setPassword(passwordencoder.encode(admin.getPassword()));
	adminRepository.save(admin);
	return new  ResponseEntity<>("user added succesfully !",HttpStatus.OK);
}
}
