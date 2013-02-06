package com.epam.model;

import java.io.Serializable;
import java.util.List;

public class Tenant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9097628235104953963L;
	private Integer id;
	private String name;
	private List<User> users;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
