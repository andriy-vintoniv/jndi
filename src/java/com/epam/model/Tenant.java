package com.epam.model;

import java.io.Serializable;

public class Tenant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9097628235104953963L;
	private Integer id;
	private String name;
	private Integer usersNumber;

	public Integer getUsersNumber() {
		return usersNumber;
	}

	public void setUsersNumber(Integer usersNumber) {
		this.usersNumber = usersNumber;
	}

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

}
