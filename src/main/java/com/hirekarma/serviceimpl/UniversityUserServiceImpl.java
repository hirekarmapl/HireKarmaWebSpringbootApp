package com.hirekarma.serviceimpl;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.UserBean;
import com.hirekarma.email.controller.EmailController;
import com.hirekarma.exception.StudentUserDefindException;
import com.hirekarma.exception.UniversityUserDefindException;
import com.hirekarma.model.University;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.UniversityRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.UniversityUserService;

@Service("universityUserService")
public class UniversityUserServiceImpl implements UniversityUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UniversityUserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UniversityRepository universityRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailController emailController;

	@Override
	public UserProfile insert(UserProfile universityUser) {
		LOGGER.debug("Inside UniversityUserServiceImpl.insert(-)");
		UserProfile sityUser = null;
		HttpHeaders headers = null;
		Map<String, String> body = null;
		University university = new University();

		String LowerCaseEmail = universityUser.getEmail().toLowerCase();
		Long count = userRepository.getDetailsByEmail(LowerCaseEmail, "university");

		try {
			LOGGER.debug("Inside try block of UniversityUserServiceImpl.insert(-)");
			if (count == 0) {

				universityUser.setUserType("university");
				universityUser.setStatus("Active");
				universityUser.setEmail(LowerCaseEmail);
				universityUser.setPassword(passwordEncoder.encode(universityUser.getPassword()));

				sityUser = userRepository.save(universityUser);

				university.setUserId(universityUser.getUserId());
				university.setUniversityEmail(LowerCaseEmail);
				university.setUniversityName(universityUser.getName());
				university.setStatus(true);

				universityRepository.save(university);

				headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				body = new HashMap<String, String>();
				body.put("email", universityUser.getEmail());

				emailController.welcomeEmail(body);
				emailController.letsGetStarted(body);

				LOGGER.info("Data successfully saved using UniversityUserServiceImpl.insert(-)");
			} else {
				throw new StudentUserDefindException("This Email Is Already Present !!");
			}
			return sityUser;

		} catch (Exception e) {
			LOGGER.error("Data Insertion failed using UniversityUserServiceImpl.insert(-): " + e);
			throw new UniversityUserDefindException(e.getMessage());
		}
	}

// email address are used to check if only one email is used for both email and workemail and rest is for updating
	@Override
	public UserBean updateUniversityUserProfile(UserBean universityUserBean, String jwtToken) {
		LOGGER.debug("Inside UniversityUserServiceImpl.updateUniversityUserProfile(-)");
		UserProfile universityUser = null;
		UserProfile universityUserReturn = null;
		Optional<UserProfile> optional = null;
		UserBean universityUserBeanReturn = null;
		University university = new University();

		String LowerCaseEmail = universityUserBean.getEmail().toLowerCase();
		String universityEmail = universityUserBean.getUniversityEmailAddress().toLowerCase();
		Long count1 = userRepository.getDetailsByEmail(LowerCaseEmail, "university");

		Long count2 = universityRepository.getDetailsByEmail(universityEmail);
		

		try {
			LOGGER.debug("Inside try block of UniversityUserServiceImpl.updateUniversityUserProfile(-)");

			if (count1 == 1 && count2 == 0) {

				String[] chunks1 = jwtToken.split(" ");
				String[] chunks = chunks1[1].split("\\.");
				Base64.Decoder decoder = Base64.getUrlDecoder();
				String payload = new String(decoder.decode(chunks[1]));
				
				JSONParser jsonParser = new JSONParser();
				Object obj = jsonParser.parse(payload);

				JSONObject jsonObject = (JSONObject) obj;
				String email = (String) jsonObject.get("sub");

				System.out.println("         \njsonobject"+jsonObject.toJSONString()+"        email "+email);
				university = universityRepository.findByEmail(email);

				optional = userRepository.findById(university.getUserId());
//				Optional<University> universityOptional = universityRepository
//						.getUniversityDetails(universityUserBean.getUserId());

				if (!optional.isEmpty()) {
					if (university != null) {
						universityUser = optional.get();
//						university = universityOptional.get();

						if (universityUser != null) {

							universityUser.setName(universityUserBean.getName());
//							universityUser.setEmail(universityUserBean.getEmail());
							universityUser.setPhoneNo(universityUserBean.getPhoneNo());
							universityUser.setUniversityEmailAddress(universityUserBean.getUniversityEmailAddress());
							universityUser.setImage(universityUserBean.getImage());
							universityUser.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
							universityUser.setAddress(universityUserBean.getAddress());

							universityUserReturn = userRepository.save(universityUser);

							universityUserBeanReturn = new UserBean();

							university.setUniversityName(universityUserReturn.getName());
							university.setUniversityEmail(universityUserReturn.getEmail());
							university.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
							university.setUniversityAddress(universityUserBean.getAddress());
							university.setUniversityImage(universityUserBean.getImage());
							university.setUniversityPhoneNumber(Long.valueOf(universityUserBean.getPhoneNo()));

							universityRepository.save(university);

							BeanUtils.copyProperties(universityUserReturn, universityUserBeanReturn);
							LOGGER.info(
									"Data Successfully updated using UniversityUserServiceImpl.updateUniversityUserProfile(-)");
						}
					}
				}
			} else {
				throw new Exception();
			}
			return universityUserBeanReturn;
		} catch (Exception e) {
			LOGGER.error("Error occured in UniversityUserServiceImpl.updateUniversityUserProfile(-): " + e);
			throw new UniversityUserDefindException(e.getMessage());
		}
	}

	@Override
	public UserBean findUniversityById(Long universityId) {
		LOGGER.debug("Inside UniversityUserServiceImpl.findUniversityById(-)");
		UserProfile universityUser = null;
		Optional<UserProfile> optional = null;
		UserBean universityUserBean = null;
		try {
			LOGGER.debug("Inside try block of UniversityUserServiceImpl.findUniversityById(-)");
			optional = userRepository.findById(universityId);
			if (!optional.isEmpty()) {
				universityUser = optional.get();
				if (universityUser != null) {
					universityUserBean = new UserBean();
					BeanUtils.copyProperties(universityUser, universityUserBean);
					LOGGER.info("Data Successfully fetched using UniversityUserServiceImpl.findUniversityById(-)");
				}
			}
			return universityUserBean;
		} catch (Exception e) {
			LOGGER.error("Error occured in UniversityUserServiceImpl.findUniversityById(-): " + e);
			throw new UniversityUserDefindException(e.getMessage());
		}
	}

//	@Override
//	public UniversityUserBean findUniversityById(Long universityId) {
//		LOGGER.debug("Inside UniversityUserServiceImpl.findUniversityById(-)");
//		UniversityUser universityUser=null;
//		Optional<UniversityUser> optional=null;
//		UniversityUserBean universityUserBean=null;
//		try {
//			LOGGER.debug("Inside try block of UniversityUserServiceImpl.findUniversityById(-)");
//			optional=universityUserRepository.findById(universityId);
//			if(!optional.isEmpty()) {
//				universityUser=optional.get();
//				if(universityUser!=null) {
//					universityUserBean=new UniversityUserBean();
//					BeanUtils.copyProperties(universityUser, universityUserBean);
//					LOGGER.info("Data Successfully fetched using UniversityUserServiceImpl.findUniversityById(-)");
//				}
//			}
//			return universityUserBean;
//		}
//		catch (Exception e) {
//			LOGGER.error("Error occured in UniversityUserServiceImpl.findUniversityById(-): "+e);
//			throw new UniversityUserDefindException(e.getMessage());
//		}
//	}

//	@Override
//	public UniversityUserBean checkLoginCredentials(String email, String password) {
//		
//		LOGGER.debug("Inside UniversityUserServiceImpl.insert(-)");
//		UniversityUserBean sityUserBean=null;
//		UniversityUser sityUser=null;
//		HirekarmaPasswordVerifier verifier= null;
//		String encryptedPassword=null;
//		try {
//			LOGGER.debug("Inside try block of UniversityUserServiceImpl.checkLoginCredentials(-,-)");
//			verifier= new HirekarmaPasswordVerifier();
//			encryptedPassword=verifier.getEncriptedString(password);
//			sityUser= universityUserRepository.checkLoginCredentials(email, encryptedPassword);
//			if(sityUser!=null) {
//				LOGGER.info("user credential match using UniversityUserServiceImpl.checkLoginCredentials(-,-)");
//				sityUserBean=new UniversityUserBean();
//				BeanUtils.copyProperties(sityUser, sityUserBean);
//				return sityUserBean;
//			}
//			else {
//				LOGGER.info("user credential does not match using UniversityUserServiceImpl.checkLoginCredentials(-,-)");
//				return null;
//			} 
//		}
//		catch(Exception e ) {
//			LOGGER.info("Error occured in UniversityUserServiceImpl.checkLoginCredentials(-,-): "+e);
//			throw e;
//		}
//	
//	}
//	@Override
//	public UniversityUserBean updateUniversityUserProfile(UniversityUserBean universityUserBean) {
//		LOGGER.debug("Inside UniversityUserServiceImpl.updateUniversityUserProfile(-)");
//		UniversityUser universityUser=null;
//		UniversityUser universityUserReturn=null;
//		Optional<UniversityUser> optional=null;
//		UniversityUserBean universityUserBeanReturn=null;
//		try {
//			LOGGER.debug("Inside try block of UniversityUserServiceImpl.updateUniversityUserProfile(-)");
//			optional=universityUserRepository.findById(universityUserBean.getUniversityId());
//			if(!optional.isEmpty()) {
//				universityUser=optional.get();
//				if(universityUser!=null) {
//					universityUser.setUniversityName(universityUserBean.getUniversityName());
//					universityUser.setEmailAddress(universityUserBean.getEmailAddress());
//					universityUser.setPhoneNo(universityUserBean.getPhoneNo());
//					universityUser.setUniversityEmailAddress(universityUserBean.getUniversityEmailAddress());
//					universityUser.setUniversityImage(universityUserBean.getUniversityImage());
//					universityUser.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
//					universityUser.setUniversityAddress(universityUserBean.getUniversityAddress());
//					universityUserReturn=universityUserRepository.save(universityUser);
//					universityUserBeanReturn=new UniversityUserBean();
//					BeanUtils.copyProperties(universityUserReturn, universityUserBeanReturn);
//					LOGGER.info("Data Successfully updated using UniversityUserServiceImpl.updateUniversityUserProfile(-)");
//				}
//			}
//			return universityUserBeanReturn;
//		}
//		catch (Exception e) {
//			LOGGER.error("Error occured in UniversityUserServiceImpl.updateUniversityUserProfile(-): "+e);
//			throw new UniversityUserDefindException(e.getMessage());
//		}
//	}

}