package com.userModule.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userModule.role.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	 
}
