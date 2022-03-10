package com.hirekarma.serviceimpl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.InternshipBean;
import com.hirekarma.exception.InternshipException;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Internship;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.InternshipRepository;
import com.hirekarma.service.InternshipService;
import com.hirekarma.utilty.Validation;

@Service("internshipService")
public class InternshipServiceImpl implements InternshipService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InternshipServiceImpl.class);

	@Autowired
	private InternshipRepository internshipRepository;

	@Autowired
	private CorporateRepository corporateRepository;

	@Autowired
	private AWSS3Service awss3Service;

	Internship copyPropertiesFromInternshipBeanToInternshipForNotNull(InternshipBean internshipBean,
			Internship internship) {
		if (internshipBean.getInternshipTitle() != null) {
			internship.setInternshipTitle(internshipBean.getInternshipTitle());
		}
		if (internshipBean.getInternshipType() != null) {
			internship.setInternshipTitle(internshipBean.getInternshipType());
		}
		if (internshipBean.getSkills() != null) {
			internship.setSkills(internshipBean.getSkills());
		}
		if (internshipBean.getCity() != null) {
			internship.setCity(internshipBean.getCity());
		}
		if (internshipBean.getOpenings() != null) {
			internship.setOpenings(internshipBean.getOpenings());
		}
		if (internshipBean.getSalary() != null) {
			internship.setSalary(internshipBean.getSalary());
		}
		if (!internshipBean.getAbout().equals("") && internshipBean.getAbout() != null) {
			internship.setAbout(internshipBean.getAbout());
		}
		if (!internshipBean.getDescription().equals("") && internshipBean.getDescription() != null) {
			internship.setDescription(internshipBean.getDescription());
		}
		if (internshipBean.getFile() != null && !internshipBean.getFile().isEmpty()) {
			internship.setDescriptionFileUrl(this.awss3Service.uploadFile(internshipBean.getFile()));
		}

		return internship;
	}

	@Override
	public InternshipBean insert(InternshipBean internshipBean, String token) throws Exception {
		LOGGER.debug("Inside InternshipServiceImpl.insert()");
		Internship internship = null;
		Internship internshipReturn = null;
		InternshipBean bean = null;
		String email = Validation.validateToken(token);
		Corporate corporate = corporateRepository.findByEmail(email);
		internshipBean.setStatus(false);
		internshipBean.setDeleteStatus(false);
		internshipBean.setCorporateId(null);
		internshipBean.setCorporateId(corporate.getCorporateId());
		internshipBean.setCreatedOn(Timestamp.from(Instant.now()));
		internship = new Internship();
		internship = internshipRepository
				.save(copyPropertiesFromInternshipBeanToInternshipForNotNull(internshipBean, internship));
		InternshipBean responseBean = new InternshipBean();
		BeanUtils.copyProperties(internship, responseBean);
		return responseBean;

	}

	@Override
	public List<InternshipBean> findInternshipsByUserId(String token) {
		LOGGER.debug("Inside InternshipServiceImpl.findInternshipsByCorporateId(-)");
		List<Internship> internships = null;
		List<InternshipBean> internshipBeans = null;
		InternshipBean internshipBean = null;
		boolean flag = false;
		Corporate corporate = null;

		try {

			String[] chunks1 = token.split(" ");
			String[] chunks = chunks1[1].split("\\.");
			Base64.Decoder decoder = Base64.getUrlDecoder();

			String payload = new String(decoder.decode(chunks[1]));
			JSONParser jsonParser = new JSONParser();
			Object obj = jsonParser.parse(payload);

			JSONObject jsonObject = (JSONObject) obj;

			String email = (String) jsonObject.get("sub");

			corporate = corporateRepository.findByEmail(email);

			if (corporate != null) {

				LOGGER.debug("Inside try block of InternshipServiceImpl.findInternshipsByCorporateId(-)");

				internships = internshipRepository.findInternshipsUserId(corporate.getCorporateId(), false);

				if (internships != null) {

					if (internships.size() > 0) {

						internshipBeans = new ArrayList<InternshipBean>();

						for (Internship internship : internships) {
							internshipBean = new InternshipBean();
							BeanUtils.copyProperties(internship, internshipBean);
							internshipBeans.add(internshipBean);
						}
						flag = true;
					} else {
						flag = false;
					}
				} else {
					flag = false;
					throw new InternshipException("No Internship Found !!");
				}
				if (flag) {
					LOGGER.info(
							"Data successfully fetched using InternshipServiceImpl.findInternshipsByCorporateId(-)");
					return internshipBeans;
				} else {
					LOGGER.info(
							"No internships are there. Get Result using InternshipServiceImpl.findInternshipsByCorporateId(-)");
					return internshipBeans;
				}
			} else {
				throw new InternshipException("Corporate Data Not Found !!");
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in InternshipServiceImpl.findInternshipsByCorporateId(-): " + e);
			throw new InternshipException(e.getMessage());
		}
	}

	@Override
	public InternshipBean findInternshipById(Long internshipId, String token) {
		LOGGER.debug("Inside InternshipServiceImpl.findInternshipById(-)");
		Internship internship = null;
		Optional<Internship> optional = null;
		InternshipBean internshipBean = null;
		Corporate corporate = null;

		try {

			String[] chunks1 = token.split(" ");
			String[] chunks = chunks1[1].split("\\.");
			Base64.Decoder decoder = Base64.getUrlDecoder();

			String payload = new String(decoder.decode(chunks[1]));
			JSONParser jsonParser = new JSONParser();
			Object obj = jsonParser.parse(payload);

			JSONObject jsonObject = (JSONObject) obj;

			String email = (String) jsonObject.get("sub");

			corporate = corporateRepository.findByEmail(email);

			if (corporate != null) {

				LOGGER.debug("Inside try block of InternshipServiceImpl.findInternshipById(-)");

				optional = internshipRepository.getInternshipDetails(internshipId, corporate.getCorporateId());

				if (!optional.isEmpty()) {

					internship = optional.get();
					if (internship != null) {
						internshipBean = new InternshipBean();
						BeanUtils.copyProperties(internship, internshipBean);
						LOGGER.info("Data Successfully fetched using InternshipServiceImpl.findInternshipById(-)");
					} else {
						throw new InternshipException("This Internship Has Been Removed !!");
					}
				} else {
					throw new InternshipException("No Internships Found With This Corporate!!");
				}
			} else {
				throw new InternshipException("No Corporate Found !!");
			}
			return internshipBean;
		} catch (Exception e) {
			LOGGER.error("Error occured in InternshipServiceImpl.findInternshipById(-): " + e);
			throw new InternshipException(e.getMessage());
		}
	}

	@Override
	public List<InternshipBean> deleteInternshipById(Long internshipId, String token) {
		LOGGER.debug("Inside InternshipServiceImpl.deleteInternshipById(-)");
		Internship internship = null;
		Optional<Internship> optional = null;
//		Long corpUserId = null;
		List<InternshipBean> internshipBeans = null;
		Corporate corporate = null;

		try {
			LOGGER.debug("Inside try block of InternshipServiceImpl.deleteInternshipById(-)");
			String[] chunks1 = token.split(" ");
			String[] chunks = chunks1[1].split("\\.");
			Base64.Decoder decoder = Base64.getUrlDecoder();

			String payload = new String(decoder.decode(chunks[1]));
			JSONParser jsonParser = new JSONParser();
			Object obj = jsonParser.parse(payload);

			JSONObject jsonObject = (JSONObject) obj;

			String email = (String) jsonObject.get("sub");

			corporate = corporateRepository.findByEmail(email);

			if (corporate != null) {

				optional = internshipRepository.getInternshipDetails(internshipId, corporate.getCorporateId());

				if (!optional.isEmpty()) {

					internship = optional.get();

					if (internship != null) {

						internship.setDeleteStatus(true);
						internship.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));

						internshipRepository.save(internship);
						internshipBeans = findInternshipsByUserId(token);
					}
				} else {
					throw new InternshipException("No Internships In This Corporate !!");
				}
			} else {
				throw new InternshipException("No Corporate Found !!");
			}
			LOGGER.debug("Data Successfully Deleted using InternshipServiceImpl.findInternshipById(-)");
			return internshipBeans;
		} catch (Exception e) {
			LOGGER.error("Error occured in InternshipServiceImpl.deleteInternshipById(-): " + e);
			throw new InternshipException(e.getMessage());
		}
	}

	@Override
	public InternshipBean updateInternshipById(InternshipBean internshipBean, String token) throws Exception{
		String email  = Validation.validateToken(token);
		Corporate corporate = this.corporateRepository.findByEmail(email);
		Optional<Internship> optional = this.internshipRepository.findById(internshipBean.getInternshipId());
		if(!optional.isPresent())
		{
			throw new Exception("invalid internship id");
		}
		Internship internship = optional.get();
		internship.setUpdatedOn(Timestamp.from(Instant.now()));
		internship  = this.internshipRepository.save(copyPropertiesFromInternshipBeanToInternshipForNotNull(internshipBean,internship));
		InternshipBean responseBean = new InternshipBean();
		BeanUtils.copyProperties(internship, responseBean);
		return responseBean;
		
	}

	@Override
	public void activateInternship(String token, Long internshipId, boolean active) throws Exception {

		Optional<Internship> internshipOptional = internshipRepository.findById(internshipId);
		if (internshipOptional.isEmpty()) {
			throw new Exception("no internship found");

		}
		Internship internship = internshipOptional.get();
		internship.setStatus(active);
		internshipRepository.save(internship);
	}
}
