package com.userModule.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.userModule.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	 
}
