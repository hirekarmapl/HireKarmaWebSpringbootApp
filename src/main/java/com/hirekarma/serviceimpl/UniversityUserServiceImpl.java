package com.hirekarma.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.hirekarma.utilty.Validation;

@Service("universityUserService")
public class UniversityUserServiceImpl implements UniversityUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UniversityUserServiceImpl.class);
	@Autowired
	private AWSS3Service awss3Service;
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
				body.put("name", universityUser.getName());
				body.put("type", "university");
				emailController.welcomeAndOnBoardEmail(body);

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

	public UserProfile copyPropertiesForUniversityFromUserBeanToUserForNotNull(UserBean userBean,UserProfile userProfile) {

		if(userBean.getName()!=null && !userBean.getName().equals("")) {
			userProfile.setName(userBean.getName());
		}
		if(userBean.getPhoneNo()!=null && !userBean.getPhoneNo().equals("")) {
			userProfile.setPhoneNo(userBean.getPhoneNo());
		}
		if(userBean.getUniversityEmailAddress()!=null && !userBean.getUniversityEmailAddress().equals("")) {
			userProfile.setUniversityEmailAddress(userBean.getUniversityEmailAddress());
		}
		if(userBean.getFile()!=null ) {
			userProfile.setImageUrl(awss3Service.uploadFile(userBean.getFile()));
			System.out.println(userProfile.getImageUrl());
		}
		if(userBean.getAddress()!=null && !userBean.getAddress().equals("")) {
			userProfile.setAddress(userBean.getAddress());
		}
		userProfile.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
		
		return userProfile;
	}
// email address are used to check if only one email is used for both email and workemail and rest is for updating
	@Override
	public UserBean updateUniversityUserProfile(UserBean universityUserBean, String jwtToken) {
		UserProfile universityUser = null;
		UserProfile universityUserReturn =  new UserProfile();
		Optional<UserProfile> optional = null;
		UserBean universityUserBeanReturn = null;
		University university = new University();
		try {
		
		LOGGER.debug("Inside UniversityUserServiceImpl.updateUniversityUserProfile(-)");
		
		
		String LowerCaseEmail = Validation.validateToken(jwtToken).toLowerCase();
		university = universityRepository.findByEmail(LowerCaseEmail);
		System.out.println("unviersity"+university);
		optional = userRepository.findById(university.getUserId());
		universityUser = optional.get();
		universityUserReturn = userRepository.save(copyPropertiesForUniversityFromUserBeanToUserForNotNull(universityUserBean, universityUser));
		System.out.println("univeristy return"+ universityUserReturn);
		
		universityUserBeanReturn = new UserBean();
		System.out.println("setting value in univesrtity");
		university.setUniversityName(universityUserReturn.getName());
		university.setUniversityEmail(universityUserReturn.getEmail());
		university.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
		university.setUniversityAddress(universityUserReturn.getAddress());
		university.setUniversityImageUrl(universityUserReturn.getImageUrl());
//		System.out.println(Long.parseLong(universityUserReturn.getPhoneNo()));
		System.out.println("getting inside phone");
		if(universityUserReturn.getPhoneNo()!=null && !universityUserReturn.getPhoneNo().equals(""))
		{
			System.out.println("insdie"+universityUserReturn.getPhoneNo());
			System.out.println();
			university.setUniversityPhoneNumber(Long.parseLong(universityUserReturn.getPhoneNo()));
			System.out.println("done");
		}
		System.out.println("phone no issue resolved");
//		university.setUniversityPhoneNumber((universityUserReturn.getPhoneNo()!=null || !universityUserReturn.getPhoneNo().equals(""))?:null);
		university.setProfileUpdationStatus(true);
		System.out.println("university before saving");
		university = universityRepository.save(university);
		System.out.println("university"+university);
		LOGGER.info("university saved properly");
		universityUserBeanReturn = new UserBean();
		universityUserBeanReturn.setImageUrl(universityUserReturn.getImageUrl());
		universityUserBeanReturn.setName(universityUserReturn.getName());
		universityUserBeanReturn.setAddress(universityUserReturn.getAddress());
		universityUserBeanReturn.setPhoneNo(universityUserReturn.getPhoneNo());
		universityUserBeanReturn.setUniversityEmailAddress(university.getUniversityEmail());
		universityUserBeanReturn.setProfileUpdationStatus(university.getProfileUpdationStatus());
		System.out.println(universityUserReturn);
		LOGGER.info("bean copied ssucesful");		

		return universityUserBeanReturn;
		}
		catch(Exception e){
			LOGGER.debug("Data updation failed in UniversityUserServiceImpl.updateUniversityUserProfile(-)");
		}
		System.out.println("somewthing went wrong");
		return null;
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


	@Override
	public ResponseEntity<Resource> getDummyExcelForStudentImport() {
		String filename = "import_student.xlsx";
		   String[] HEADERs = {"Name", "Email", "Phone" };
		   String SHEET="all_Student";
	    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	      Sheet sheet = workbook.createSheet(SHEET);
	      // Header
	      Row headerRow = sheet.createRow(0);
	      for (int col = 0; col < HEADERs.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(HEADERs[col]);
	      }
	      
	      workbook.write(out);
	      ByteArrayInputStream in =  new ByteArrayInputStream(out.toByteArray());
	  	InputStreamResource files = new InputStreamResource(in);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(files);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
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