package com.epam.service;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

public class LDAPUtilService {
	private Attributes attributes = null;

	public LDAPUtilService() {
		setAttributes(new BasicAttributes(true));
	}

	public void addUserAttribute(String name, String value) {
		Attribute attr = new BasicAttribute(name);
		attr.add(value);
		getAttributes().put(attr);
	}// addUserAttribute

	public void addUserAttributes(String name, String[] value) {
		Attribute attr = new BasicAttribute(name);

		if (value != null) {
			for (int i = 0; i < value.length; i++)
				attr.add(value[i]);
		}
		getAttributes().put(attr);
	}// addUserAttribute

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

}
