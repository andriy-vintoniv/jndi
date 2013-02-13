package com.epam.jndi2ldap.model;

import java.io.Serializable;

public class UserSettings implements Serializable {

	private static final long serialVersionUID = -3985067520117920573L;

	private Integer recordId;
	private Integer userId;
	private PropertyName propertyName;
	private String propertyValue;

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public PropertyName getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(PropertyName propertyName) {
		this.propertyName = propertyName;
	}
}
