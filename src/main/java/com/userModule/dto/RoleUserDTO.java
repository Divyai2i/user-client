package com.userModule.dto;

import java.io.Serializable;
import java.util.Set;

import com.userModule.model.User;

public class RoleUserDTO implements Serializable{
	private long roleId;
	private String name;
	private String description;
    private Set<User> users;
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
    
    
}
