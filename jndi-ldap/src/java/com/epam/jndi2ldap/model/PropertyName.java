package com.epam.jndi2ldap.model;

public enum PropertyName {
	NAME("name"), EMAIL("email"), BIRTHDAY("birthday"), PASSWORD("userPassword");

	private String value;

	private PropertyName(String value) {
		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	private void setValue(String value) {
		this.value = value;
	}
}
