package com.sunBase.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunBase.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, String>{
Optional<Admin> findByLoginId(String loginid);
}
