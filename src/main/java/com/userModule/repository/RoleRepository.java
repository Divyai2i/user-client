package com.userModule.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userModule.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	 
}
