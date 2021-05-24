package com.userModule.user.controller;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.userModule.user.dto.UserRoleDTO;

@FeignClient(name="UserSearch", url="localhost:8000")
public interface FeignProxy {

   @RequestMapping("/user/find/{id}")
   public UserRoleDTO findById(@PathVariable(value="id") Long id);

   @RequestMapping("/user/findall")
   public Collection<UserRoleDTO> findAll();
}
