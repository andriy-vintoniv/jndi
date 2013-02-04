package com.epam.service;

import com.epam.dao.impl.TenantDAO;
import com.epam.model.Tenant;
import com.epam.model.User;

public class TenantService {
	private TenantDAO tenantDAO = new TenantDAO();;

	public void create(Tenant tenant, String ou, String cn, String workContext) {
		this.tenantDAO.create(tenant, ou, cn, workContext);
	}

	public User read() {
		// TODO:
		return null;
	}

	public void update(User object) {
		// TODO:
	}
}
