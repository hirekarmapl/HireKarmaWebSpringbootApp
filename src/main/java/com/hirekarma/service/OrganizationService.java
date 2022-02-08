package com.hirekarma.service;

import com.hirekarma.beans.OrganizationBean;

public interface OrganizationService {
	public OrganizationBean updateOrganizationDetails(OrganizationBean bean, String token) throws Exception;
	public OrganizationBean findOrganizationByUserId(Long userId, String token) throws Exception;
}
