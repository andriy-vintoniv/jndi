package com.epam.dao;

public interface GenericDAO<T> {

	void create(T object, String ou, String cn, String workContext);

	abstract T read();

	abstract void update(T object);

}
