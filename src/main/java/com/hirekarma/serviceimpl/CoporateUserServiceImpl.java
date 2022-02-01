package com.hirekarma.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.CampusDriveResponseBean;
import com.hirekarma.beans.StudentDetails;
import com.hirekarma.beans.UserBean;
import com.hirekarma.email.controller.EmailController;
import com.hirekarma.exception.CoporateUserDefindException;
import com.hirekarma.exception.StudentUserDefindException;
import com.hirekarma.model.CampusDriveResponse;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Organization;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.CampusDriveResponseRepository;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.OrganizationRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.CoporateUserService;

@Service("coporateUserService")
public class CoporateUserServiceImpl implements CoporateUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CoporateUserServiceImpl.class);

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CorporateRepository corporateRepository;

	@Autowired
	private CampusDriveResponseRepository campusDriveResponseRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailController emailController;

	@Override
	public UserProfile insert(UserProfile userProfile) {
		LOGGER.debug("Inside CoporateUserServiceImpl.insert(-)");
		UserProfile user = null;
		Organization organization = null;
		Map<String, String> body = null;
		Corporate corporate = new Corporate();

		String LowerCaseEmail = userProfile.getEmail().toLowerCase();
		Long count = userRepository.getDetailsByEmail(LowerCaseEmail, "corporate");

		try {
			LOGGER.debug("Inside try block of CoporateUserServiceImpl.insert(-)");
			if (count == 0) {

				userProfile.setUserType("corporate");
				userProfile.setStatus("Active");
				userProfile.setPassword(passwordEncoder.encode(userProfile.getPassword()));

				user = userRepository.save(userProfile);

				corporate.setCorporateEmail(LowerCaseEmail);
				corporate.setCorporateName(user.getName());
				corporate.setStatus("Active");

				corporateRepository.save(corporate);

				organization = new Organization();
				organization.setUserId(user.getUserId());
				organization.setStatus("Active");

				organizationRepository.save(organization);

				body = new HashMap<String, String>();
				body.put("email", userProfile.getEmail());

				emailController.welcomeEmail(body);
				emailController.letsGetStarted(body);

			} else {
				throw new StudentUserDefindException("This Email Is Already Present !!");
			}

			LOGGER.info("Data successfully saved using CoporateUserServiceImpl.insert(-)");
			return user;
		} catch (Exception e) {
			LOGGER.error("Data Insertion failed using CoporateUserServiceImpl.insert(-): " + e);
			throw new CoporateUserDefindException(e.getMessage());
		}
	}

	@Override
	public UserBean updateCoporateUserProfile(UserBean bean) {
		LOGGER.debug("Inside CoporateUserServiceImpl.updateCoporateUserProfile(-)");
		UserProfile user = null;
		UserProfile userReturn = null;
		Optional<UserProfile> optional = null;
		UserBean userBean = null;
		try {
			LOGGER.debug("Inside try block of CoporateUserServiceImpl.updateCoporateUserProfile(-)");
			optional = userRepository.findById(bean.getUserId());
			if (!optional.isEmpty()) {
				user = optional.get();
				if (user != null) {
					user.setName(bean.getName());
					user.setEmail(bean.getEmail());
					user.setPhoneNo(bean.getPhoneNo());
					user.setImage(bean.getImage());
					user.setAddress(bean.getAddress());
					user.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
					userReturn = userRepository.save(user);
					userBean = new UserBean();
					BeanUtils.copyProperties(userReturn, userBean);
					LOGGER.info("Data Successfully updated using CoporateUserServiceImpl.updateCoporateUserProfile(-)");
				}
			}
			return userBean;
		} catch (Exception e) {
			LOGGER.error("Error occured in CoporateUserServiceImpl.updateCoporateUserProfile(-): " + e);
			throw new CoporateUserDefindException(e.getMessage());
		}
	}

	@Override
	public UserBean findCorporateById(Long userId) {
		LOGGER.debug("Inside CoporateUserServiceImpl.findCorporateById(-)");
		UserProfile user = null;
		Optional<UserProfile> optional = null;
		UserBean userBean = null;
		try {
			LOGGER.debug("Inside try block of CoporateUserServiceImpl.findCorporateById(-)");
			optional = userRepository.findById(userId);
			if (!optional.isEmpty()) {
				user = optional.get();
				if (user != null) {
					userBean = new UserBean();
					BeanUtils.copyProperties(user, userBean);
					LOGGER.info("Data Successfully fetched using CoporateUserServiceImpl.findCorporateById(-)");
				}
			}
			return userBean;
		} catch (Exception e) {
			LOGGER.error("Error occured in CoporateUserServiceImpl.findCorporateById(-): " + e);
			throw new CoporateUserDefindException(e.getMessage());
		}
	}

	@Override
	public List<StudentDetails> corporateCampusResponse(CampusDriveResponseBean campus) {

		CampusDriveResponseBean driveResponseBean = new CampusDriveResponseBean();
		CampusDriveResponse driveResponse = null;
		StudentDetails studentDetails = null;
		List<StudentDetails> StudentDetailsList = new ArrayList<StudentDetails>();

		try {
			LOGGER.debug("Inside CoporateUserServiceImpl.corporateCampusResponse(-)");

			if (campus != null) {
				Optional<CampusDriveResponse> optional = campusDriveResponseRepository
						.findById(campus.getCampusDriveId());
				driveResponse = new CampusDriveResponse();
				driveResponse = optional.get();

				if (driveResponse != null) {

					driveResponse.setCorporateResponse(campus.getCorporateResponse());
					driveResponse.setCorporateResponseOn(new Timestamp(new java.util.Date().getTime()));

					campusDriveResponseRepository.save(driveResponse);

					if (driveResponse.getCorporateResponse() && driveResponse.getUniversityAsk()) {
						List<Object[]> list = studentRepository.findApplyStudentDetails(driveResponse.getUniversityId(),
								driveResponse.getJobId());

						if (list.size() != 0) {
							for (Object[] obj : list) {

								studentDetails = new StudentDetails();

								studentDetails.setName((String) obj[0]);
								studentDetails.setEmail((String) obj[1]);
								studentDetails.setAddress((String) obj[2]);
								studentDetails.setPhone(String.valueOf(obj[3]));
								studentDetails.setBatch((String) obj[4]);
								studentDetails.setBranch((String) obj[5]);
								studentDetails.setCgpa(String.valueOf(obj[6]));

								StudentDetailsList.add(studentDetails);
							}
						} else {
							throw new CoporateUserDefindException("No Student Found !!");
						}
					}

					BeanUtils.copyProperties(driveResponse, driveResponseBean);

				} else {
					throw new CoporateUserDefindException("No Data Found !!");
				}

			} else {
				throw new CoporateUserDefindException("Request can't Be Null !!");
			}

			LOGGER.info("Data Updated Successfully In CoporateUserServiceImpl.corporateCampusResponse(-)");

		} catch (Exception e) {

			LOGGER.info("Data Updatation Failed In CoporateUserServiceImpl.corporateCampusResponse(-)" + e);
			throw e;
		}
		return StudentDetailsList;
	}

	@Override
	public List<Corporate> corporateList() {
		List<Corporate> corporate = new ArrayList<Corporate>();
		try {
			LOGGER.debug("Inside CoporateUserServiceImpl.corporateList(-)");
			corporate = corporateRepository.findAll();

		} catch (Exception e) {
			LOGGER.error("Data Fetching Failed At CoporateUserServiceImpl.corporateList(-)");
			throw e;
		}
		return corporate;
	}

	@Override
	public List<StudentDetails> applyStudentDetails(CampusDriveResponseBean campus, String token) {
		CampusDriveResponseBean driveResponseBean = new CampusDriveResponseBean();
		CampusDriveResponse driveResponse = null;
		StudentDetails studentDetails = null;
		List<StudentDetails> StudentDetailsList = new ArrayList<StudentDetails>();

		try {
			LOGGER.debug("Inside CoporateUserServiceImpl.applyStudentDetails(-)");

			if (campus != null) {
				Optional<CampusDriveResponse> optional = campusDriveResponseRepository
						.findById(campus.getCampusDriveId());
				driveResponse = new CampusDriveResponse();
				driveResponse = optional.get();

				if (driveResponse != null) {

					if (driveResponse.getUniversityAsk()) {
						List<Object[]> list = studentRepository.findApplyStudentDetails(driveResponse.getUniversityId(),
								driveResponse.getJobId());

						if (list.size() != 0) {
							for (Object[] obj : list) {

								studentDetails = new StudentDetails();

								studentDetails.setName((String) obj[0]);
								studentDetails.setEmail((String) obj[1]);
								studentDetails.setAddress((String) obj[2]);
								studentDetails.setPhone(String.valueOf(obj[3]));
								studentDetails.setBatch((String) obj[4]);
								studentDetails.setBranch((String) obj[5]);
								studentDetails.setCgpa(String.valueOf(obj[6]));

								StudentDetailsList.add(studentDetails);
							}
						}
					}

					BeanUtils.copyProperties(driveResponse, driveResponseBean);

				} else {
					throw new CoporateUserDefindException("No Data Found !!");
				}

			} else {
				throw new CoporateUserDefindException("Request can't Be Null !!");
			}

			LOGGER.info("Data Updated Successfully In CoporateUserServiceImpl.applyStudentDetails(-)");

		} catch (Exception e) {

			LOGGER.info("Data Updatation Failed In CoporateUserServiceImpl.applyStudentDetails(-)" + e);
			throw e;
		}
		return StudentDetailsList;
	}

//	@Override
//	public CoporateUserBean findCorporateById(Long corpUserId) {
//		LOGGER.debug("Inside CoporateUserServiceImpl.findCorporateById(-)");
//		CoporateUser coporateUser=null;
//		Optional<CoporateUser> optional=null;
//		CoporateUserBean coporateUserBean=null;
//		try {
//			LOGGER.debug("Inside try block of CoporateUserServiceImpl.findCorporateById(-)");
//			optional=coporateUserRepository.findById(corpUserId);
//			if(!optional.isEmpty()) {
//				coporateUser=optional.get();
//				if(coporateUser!=null) {
//					coporateUserBean=new CoporateUserBean();
//					BeanUtils.copyProperties(coporateUser, coporateUserBean);
//					LOGGER.info("Data Successfully fetched using CoporateUserServiceImpl.findCorporateById(-)");
//				}
//			}
//			return coporateUserBean;
//		}
//		catch (Exception e) {
//			LOGGER.error("Error occured in CoporateUserServiceImpl.findCorporateById(-): "+e);
//			throw new CoporateUserDefindException(e.getMessage());
//		}
//	}
//	@Override
//	public CoporateUserBean checkLoginCredentials(String email, String password) {
//		LOGGER.debug("Inside CoporateUserServiceImpl.checkLoginCredentials(-,-)");
//		CoporateUserBean coporateUserBean=null;
//		CoporateUser coporateUser=null;
//		HirekarmaPasswordVerifier verifier= null;
//		String encryptedPassword=null;
//		try {
//			LOGGER.debug("Inside try block of CoporateUserServiceImpl.checkLoginCredentials(-,-)");
//			verifier= new HirekarmaPasswordVerifier();
//			encryptedPassword=verifier.getEncriptedString(password);
//			coporateUser=coporateUserRepository.checkLoginCredentials(email, encryptedPassword);
//			if(coporateUser!=null) {
//				LOGGER.info("user credential match using CoporateUserServiceImpl.checkLoginCredentials(-,-)");
//				coporateUserBean=new CoporateUserBean();
//				BeanUtils.copyProperties(coporateUser, coporateUserBean);
//				return coporateUserBean;
//			}
//			else {
//				LOGGER.info("user credential does not match using CoporateUserServiceImpl.checkLoginCredentials(-,-)");
//				return null;
//			}
//		}
//		catch (Exception e) {
//			LOGGER.error("Error occured in CoporateUserServiceImpl.checkLoginCredentials(-,-): "+e);
//			throw new CoporateUserDefindException(e.getMessage());
//		}
//	}
//	@Override
//	public CoporateUserBean updateCoporateUserProfile(CoporateUserBean bean) {
//		LOGGER.debug("Inside CoporateUserServiceImpl.updateCoporateUserProfile(-)");
//		CoporateUser coporateUser=null;
//		CoporateUser coporateUserReturn=null;
//		Optional<CoporateUser> optional=null;
//		CoporateUserBean coporateUserBean=null;
//		try {
//			LOGGER.debug("Inside try block of CoporateUserServiceImpl.updateCoporateUserProfile(-)");
//			optional=coporateUserRepository.findById(bean.getCorpUserId());
//			coporateUser=optional.get();
//			if(!optional.isEmpty()) {
//				if(coporateUser!=null) {
//					coporateUser.setName(bean.getName());
//					coporateUser.setEmail(bean.getEmail());
//					coporateUser.setPhoneNO(bean.getPhoneNO());
//					coporateUser.setProfileImage(bean.getProfileImage());
//					coporateUser.setAddress(bean.getAddress());
//					coporateUser.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
//					coporateUserReturn=coporateUserRepository.save(coporateUser);
//					coporateUserBean=new CoporateUserBean();
//					BeanUtils.copyProperties(coporateUserReturn, coporateUserBean);
//					LOGGER.info("Data Successfully updated using CoporateUserServiceImpl.updateCoporateUserProfile(-)");
//				}
//			}
//			return coporateUserBean;
//		}
//		catch (Exception e) {
//			LOGGER.error("Error occured in CoporateUserServiceImpl.updateCoporateUserProfile(-): "+e);
//			throw new CoporateUserDefindException(e.getMessage());
//		}
//	}

}
