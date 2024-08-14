package com.sunBase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sunBase.model.Admin;
import com.sunBase.repository.AdminRepository;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
   private  AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String loginid) throws UsernameNotFoundException {
        Optional<Admin> opt= adminRepository.findByLoginId(loginid);

        if(opt.isPresent()) {

            Admin user= opt.get();

            List<GrantedAuthority> authorities= new ArrayList<>();



            return new User(user.getLoginId(), user.getPassword(), authorities);



        }else
            throw new BadCredentialsException("user is not fount with these  Credentials !:- "+loginid);
    }
}