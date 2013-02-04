package com.epam.dao.impl;

import com.epam.dao.GenericDAO;
import com.epam.model.Tenant;

public class TenantDAO extends GenericDAO<Tenant> {

	public TenantDAO() {
	}

	@Override
	public Tenant read(String cn, String baseContext, String javaClassName) {
		Tenant tenant = super.read(cn, baseContext, javaClassName);
		return tenant;
	}

	@Override
	public void update(Tenant tenant, String cn, String baseContext) {
		super.update(tenant, cn, baseContext);
	}

	@Override
	public void create(Tenant tenant, String cn, String baseContext) {
		super.create(tenant, cn, baseContext);
	}
}
