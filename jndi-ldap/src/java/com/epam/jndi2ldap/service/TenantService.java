package com.epam.jndi2ldap.service;

import java.util.List;

import com.epam.jndi2ldap.dao.impl.TenantDAO;
import com.epam.jndi2ldap.model.Tenant;

public class TenantService {
	private final static String GROUPS_CONTEXT = "ou=groups";
	private final static String BASE_CONTEXT = "cn=Tenant,ou=groups";
	private TenantDAO tenantDAO = new TenantDAO();;

	public void create(Tenant tenant) {
		this.tenantDAO.create(tenant, BASE_CONTEXT);
	}

	public Tenant read(String name) {
		Tenant tenant = this.tenantDAO.read(name);
		return tenant;
	}

	public void update(Tenant tenant) {
		this.tenantDAO.update(tenant, tenant.getName(), BASE_CONTEXT);
	}

	public List<Tenant> readAll() {
		List<Tenant> tenant = this.tenantDAO.readAll(GROUPS_CONTEXT,
				Tenant.class.getName());
		return tenant;
	}
}
