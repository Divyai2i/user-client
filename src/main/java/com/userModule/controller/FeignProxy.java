package com.userModule.controller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.userModule.dto.UserRoleDTO;

@FeignClient(name="user-search", url="http://localhost:8080")
public interface FeignProxy {

   @RequestMapping("/user/find/{id}")
   public UserRoleDTO findById(@PathVariable(value="id") Long id);

   @RequestMapping("/user/findall")
   public List<UserRoleDTO> findAll();
}
