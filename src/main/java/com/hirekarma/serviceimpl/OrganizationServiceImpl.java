package com.hirekarma.serviceimpl;

import java.sql.Timestamp;
import java.util.Base64;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.OrganizationBean;
import com.hirekarma.exception.OrganizationUserDefindException;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Organization;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.OrganizationRepository;
import com.hirekarma.service.OrganizationService;

@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class);

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private CorporateRepository corporateRepository;

	@Override
	@Transactional
	public OrganizationBean updateOrganizationDetails(OrganizationBean bean, String token) throws Exception {
		LOGGER.debug("Inside OrganizationServiceImpl.updateOrganizationDetails(-)");
		Organization organization = null;
		Organization organizationReturn = null;
		OrganizationBean organizationBean = null;
		Corporate corporate = new Corporate();

		String[] chunks1 = token.split(" ");
		String[] chunks = chunks1[1].split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();

		String payload = new String(decoder.decode(chunks[1]));
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(payload);

		JSONObject jsonObject = (JSONObject) obj;

		String email = (String) jsonObject.get("sub");

		try {
			LOGGER.debug("Inside try block of OrganizationServiceImpl.updateOrganizationDetails(-)");

			corporate = corporateRepository.findByEmail(email);

			if (corporate != null) {

				organization = organizationRepository.findOrganizationByCorporateId(corporate.getCorporateId(), true);

				if (organization != null) {

					organization.setOrgName(bean.getOrgName());
					organization.setOrgEmail(bean.getOrgEmail());
					organization.setCinGstNum(bean.getCinGstNum());
					organization.setLogo(bean.getLogo());
					organization.setDescription(bean.getDescription());
					organization.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));

					organizationReturn = organizationRepository.save(organization);

					organizationBean = new OrganizationBean();
					BeanUtils.copyProperties(organizationReturn, organizationBean);

					LOGGER.info("Data Successfully updated using OrganizationServiceImpl.updateOrganizationDetails(-)");
				} else {
					throw new OrganizationUserDefindException("Organization Details Not Found !!");
				}
			} else {
				throw new OrganizationUserDefindException("Corporate Details Not Found !!");
			}
			return organizationBean;
		} catch (Exception e) {
			LOGGER.error("Error occured in OrganizationServiceImpl.updateOrganizationDetails(-): " + e);
			throw new OrganizationUserDefindException(e.getMessage());
		}
	}

	@Override
	public OrganizationBean findOrganizationByUserId(Long userId, String token) throws Exception {
		LOGGER.debug("Inside OrganizationServiceImpl.findOrganizationByCorporateId(-)");
		Organization organization = null;
		OrganizationBean organizationBean = null;
		Corporate corporate = new Corporate();

		String[] chunks1 = token.split(" ");
		String[] chunks = chunks1[1].split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();

		String payload = new String(decoder.decode(chunks[1]));
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(payload);

		JSONObject jsonObject = (JSONObject) obj;

		String email = (String) jsonObject.get("sub");

		try {
			LOGGER.debug("Inside try block of OrganizationServiceImpl.findOrganizationByCorporateId(-)");

			corporate = corporateRepository.findByEmail(email);

			if (corporate != null) {
				organization = organizationRepository.findOrganizationByUserId(corporate.getCorporateId(), true,
						userId);
				if (organization != null) {
					organizationBean = new OrganizationBean();
					BeanUtils.copyProperties(organization, organizationBean);
					LOGGER.info(
							"Data Successfully fetched using OrganizationServiceImpl.findOrganizationByCorporateId(-)");
				} else {
					throw new OrganizationUserDefindException("Organization Details Not Found !!");
				}
			} else {
				throw new OrganizationUserDefindException("Corporate Details Not Found !!");
			}
			return organizationBean;
		} catch (Exception e) {
			LOGGER.error("Error occured in OrganizationServiceImpl.findOrganizationByCorporateId(-): " + e);
			throw new OrganizationUserDefindException(e.getMessage());
		}
	}

}
