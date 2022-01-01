package com.hirekarma.service;

import com.hirekarma.beans.OrganizationBean;

public interface OrganizationService {
	public OrganizationBean updateOrganizationDetails(OrganizationBean bean);
	public OrganizationBean findOrganizationByUserId(Long userId);
}
