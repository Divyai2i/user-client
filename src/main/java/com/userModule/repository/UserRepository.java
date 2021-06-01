package com.userModule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.userModule.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	 
}
