package com.hirekarma.serviceimpl;

import static java.util.stream.Collectors.toMap;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.EducationBean;
import com.hirekarma.beans.JobApplyResponseBean;
import com.hirekarma.beans.JobResponseBean;
import com.hirekarma.beans.UniversityJobShareToStudentBean;
import com.hirekarma.beans.UniversitySharedJobList;
import com.hirekarma.beans.UserBean;
import com.hirekarma.beans.UserBeanResponse;
import com.hirekarma.email.controller.EmailController;
import com.hirekarma.email.service.EmailSenderService;
import com.hirekarma.exception.StudentUserDefindException;
import com.hirekarma.exception.UniversityException;
import com.hirekarma.exception.UniversityJobShareToStudentException;
import com.hirekarma.model.CampusDriveResponse;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Education;
import com.hirekarma.model.Experience;
import com.hirekarma.model.Job;
import com.hirekarma.model.JobApply;
import com.hirekarma.model.Skill;
import com.hirekarma.model.Student;
import com.hirekarma.model.StudentBatch;
import com.hirekarma.model.StudentBranch;
import com.hirekarma.model.University;
import com.hirekarma.model.UniversityJobShareToStudent;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.CampusDriveResponseRepository;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.EducationRepository;
import com.hirekarma.repository.ExperienceRepository;
import com.hirekarma.repository.JobApplyRepository;
import com.hirekarma.repository.JobRepository;
import com.hirekarma.repository.ProjectRepository;
import com.hirekarma.repository.SkillRespository;
import com.hirekarma.repository.StreamRepository;
import com.hirekarma.repository.StudentBatchRepository;
import com.hirekarma.repository.StudentBranchRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.repository.UniversityJobShareRepository;
import com.hirekarma.repository.UniversityRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.SkillService;
import com.hirekarma.service.StudentService;
import com.hirekarma.utilty.ExcelUploadUtil;
import com.hirekarma.utilty.Utility;
import com.hirekarma.utilty.Validation;

@Service("studentServiceImpl")
public class StudentServiceImpl implements StudentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

	@Autowired
	private AWSS3Service awss3Service;
	
	@Autowired
	private StreamRepository streamRepository;
	@Autowired
	private CampusDriveResponseRepository campusDriveResponseRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private UniversityRepository universityRepository;

	@Autowired
	private ExcelUploadUtil excelUploadUtil;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UniversityJobShareRepository universityJobShareRepository;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private EmailController emailController;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ExperienceRepository experienceRepository;

	@Autowired
	private SkillService skillService;
	
	@Autowired
	private SkillRespository skillRespository;
	
	@Autowired
	private EducationRepository educationRepository;
	
	@Autowired
	private JobApplyRepository jobApplyRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	private StudentBatchRepository studentBatchRepository;
	
	@Autowired
	private StudentBranchRepository studentBranchRepository;
	
	@Autowired
	private CorporateRepository corporateRepository;
	


	public Boolean updateSkills(List<Skill> skills, String token) throws Exception {

		addAllSkillsToStudent(skills, token);

		return true;
	}

	
	public List<Skill> getAllSkillsOfStudent(String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile user = userRepository.findByEmail(email, "student");
		if (user == null) {
			throw new Exception("user or skill not found");
		}
		return user.getSkills();
	}
	
	public List<Education> getAllEducationsOfStudent(String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile user = userRepository.findByEmail(email, "student");
		if (user == null) {
			throw new Exception("user or skill not found");
		}
		return user.getEducations();
	}

	public List<Experience> getAllExperiencesOfStudent(String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile user = userRepository.findByEmail(email, "student");
		if (user == null) {
			throw new Exception("user or skill not found");
		}
		return user.getExperiences();
	}

	@Override
	public Map<String,Object> addAllSkillsToStudent(List<Skill> skills, String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findUserByEmail(email);
		Map<String,Object> result = new HashMap<String,Object>();
		for(Skill skill :skills) {
			skill.setName(skill.getName().toLowerCase());
			
			Skill skillExist = skillRespository.findByName(skill.getName());
			
			if(skillExist==null) {
				;
				skillExist = this.skillRespository.save(skill);
			}
			if(!userProfile.getSkills().contains(skill)){

				userProfile.getSkills().add(skillExist);
			}
			
		}
		
		userProfile = this.userRepository.save(userProfile);
		result.put("skills", userProfile.getSkills());
		return result;

	}

	@Override
	public List<Experience> addAllExperienceToStudent(List<Experience> experience, String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile user = userRepository.findByEmail(email, "student");
		if (user == null) {
			throw new Exception("user not found");
		}
		for (Experience e : experience) {
			e.setUserProfile(user);
		}

		return experienceRepository.saveAll(experience);
	}
	
	
	@Override
	public Experience addExperienceToAStudent(Experience experience, String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile user = userRepository.findUserByEmail(email);
		
		experience.setUserProfile(user);
		System.out.println(experience);
		return experienceRepository.save(experience);
	}

	@Override
	public List<Education> addAllEducationToStudent(List<EducationBean> educationBeans, String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findUserByEmail(email);
		List<Education> educations = new ArrayList<>();
		for(EducationBean educationBean: educationBeans) {
			if(educationBean.getBatchId()!=null && educationBean.getBranchId()!=null) {
				Optional<StudentBatch> studentBatch = this.studentBatchRepository.findById(educationBean.getBatchId());
				Optional<StudentBranch> studentBranch = this.studentBranchRepository.findById(educationBean.getBranchId());
				if(!studentBatch.isPresent() || !studentBranch.isPresent()) {
					throw new Exception("please check batch or branch id");
				}
				educationBean.setStudentBatch(studentBatch.get());
				educationBean.setStudentBranch(studentBranch.get());
			}
			
			Education education = new Education();
			BeanUtils.copyProperties(educationBean, education);
			education.setUserProfile(userProfile);
			educations.add(education);
		}
		return this.educationRepository.saveAll(educations);
	}

	@Override
	public void deleteEducationOfAStudentbyId(String token,int id)throws Exception {
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findUserByEmail(email);
		Optional<Education> education = educationRepository.findById(id);
		if(!education.isPresent()) {
			throw new Exception("invalid request - wrong id");
		}
		if(!userProfile.getEducations().contains(education.get())) {
			throw new Exception("invalid request - user profile doest not contain education");
		}
		this.educationRepository.delete(education.get());
	}
	@Override
	public Education addEducationToAStudent(EducationBean educationBean,String token) throws Exception{
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findUserByEmail(email);
		if(educationBean.getBatchId()!=null) {
			Optional<StudentBatch> studentBatch = this.studentBatchRepository.findById(educationBean.getBatchId());
			if(!studentBatch.isPresent()) {
				throw new Exception("invalid student batch");
				
			}
			educationBean.setStudentBatch(studentBatch.get());
		}
		if(educationBean.getBranchId()!=null) {
			Optional<StudentBranch> studentBranch = this.studentBranchRepository.findById(educationBean.getBranchId());
			if(studentBranch.isPresent()) {
				educationBean.setStudentBranch(studentBranch.get());
			}
			else {
				throw new Exception("invalid student Branch");
			}
		}
		
		Education education = new Education();
		BeanUtils.copyProperties(educationBean, education);
		education.setUserProfile(userProfile);
		return this.educationRepository.save(education);		
	}
	public Boolean addSkills(List<Skill> skills, String token) throws Exception {

		UserProfile user = null;
		Skill skill = null;
		LOGGER.debug("Inside StudentServicimpl.addSkill(-)");

//			getting email from token	
		String email = Validation.validateToken(token);
		user = userRepository.findByEmail(email, "student");
		if (user == null) {
			throw new Exception("user not found");
		}

//		saving the skill not present in SKILL table
		List<Skill> skillToBeSaved = new ArrayList<Skill>();
		for (int i = 0; i < skills.size(); i++) {
			Skill name = this.skillRespository.findByName(skills.get(i).getName());
			if (name == null) {
				skills.set(i, this.skillRespository.save(skills.get(i)));
			} else {
				skills.set(i, name);
			}
		}
		this.skillRespository.saveAll(skillToBeSaved);
		user.getSkills().addAll(skills);
//		 add skill to user

		this.userRepository.save(user);
		return true;

	}


	public UserProfile insert2(String email,String password,String name) throws Exception {
		try {
			email = email.toLowerCase();
			
			UserProfile userProfile =  new UserProfile();
			userProfile.setName(name);
			userProfile.setEmail(email);
			userProfile.setPassword(passwordEncoder.encode(password));
			userProfile.setStatus("Active");
			userProfile.setUserType("student");
			String resetPasswordToken = Utility.passwordTokenGenerator();
			userProfile.setResetPasswordToken(resetPasswordToken);
			UserProfile userProfileDB = this.userRepository.save(userProfile);
			
			Student student = new Student();
			student.setUserId(userProfileDB.getUserId());
			student.setStudentEmail(email);
			
			this.studentRepository.save(student);
			
			Map<String, String> body = null;
			body = new HashMap<String, String>();
			body.put("email", userProfileDB.getEmail());
			body.put("name", userProfileDB.getName());
			body.put("type", "student");
			emailController.welcomeAndOnBoardEmail(body);
			emailController.emaiVerification(resetPasswordToken, email, name);
			return userProfileDB;
		}
		catch(ConstraintViolationException ce) {
			throw new StudentUserDefindException("email id already exist");
		}
		catch(DataIntegrityViolationException de) {
			throw new StudentUserDefindException("a email id already in use");
		}
		
	}
	
	public UserProfile insertForExcel(UserProfile student,Long universityId,String universityName,List<String> existingStudents) {
		LOGGER.debug("Inside StudentServiceImpl.insert(-)");

		UserProfile studentReturn = null;
		HttpHeaders headers = null;
		Map<String, String> body = null;
		Student stud = new Student();

		String LowerCaseEmail = student.getEmail().toLowerCase();
		Long count = userRepository.getDetailsByEmail(LowerCaseEmail, "student");

		try {
			LOGGER.debug("Inside try block of StudentServiceImpl.insert(-)");
			if (count == 0) {

				student.setStatus("Active");
				student.setUserType("student");
				student.setEmail(LowerCaseEmail);
				student.setPhoneNo(student.getPhoneNo());
				student.setPassword(((universityName==null)?"Admin":universityName)+"@123");
				System.out.print("password:"+((universityName==null)?"Admin":universityName)+"@123");
				student.setPassword(passwordEncoder.encode(student.getPassword()));
				String resetPasswordToken = Utility.passwordTokenGenerator();
				student.setResetPasswordToken(resetPasswordToken);
				
				studentReturn = userRepository.save(student);
				
				stud.setUniversityId(universityId);
				stud.setUserId(studentReturn.getUserId());
				stud.setStudentEmail(LowerCaseEmail);
				stud.setStudentName(studentReturn.getName());
				
				studentRepository.save(stud);

				headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				body = new HashMap<String, String>();
				body.put("email", student.getEmail());
				body.put("name", studentReturn.getName());
				body.put("type", "student");
				emailController.welcomeAndOnBoardEmail(body);
				emailController.passwordGenerationForImportedStudent(resetPasswordToken, studentReturn.getEmail().toLowerCase(), studentReturn.getName()!=null ? studentReturn.getName() : "", universityName);

				LOGGER.info("Data successfully saved using StudentServiceImpl.insert(-)");
			} else {
				existingStudents.add(LowerCaseEmail);
			}

			return studentReturn;
		} 
		catch(ConstraintViolationException ce) {
			throw new StudentUserDefindException("a email id already in use");
		}
		catch(DataIntegrityViolationException de) {
			throw new StudentUserDefindException("a email id already in use");
		}
		catch (Exception e) {
			LOGGER.error("Data Insertion failed using StudentServiceImpl.insert(-): " + e);
			throw new StudentUserDefindException(e.getMessage());
		}
	}
	@Override
	public UserProfile insert(UserProfile student) {

		LOGGER.debug("Inside StudentServiceImpl.insert(-)");

		UserProfile studentReturn = null;
		HttpHeaders headers = null;
		Map<String, String> body = null;
		Student stud = new Student();

		String LowerCaseEmail = student.getEmail().toLowerCase();
		Long count = userRepository.getDetailsByEmail(LowerCaseEmail, "student");

		try {
			LOGGER.debug("Inside try block of StudentServiceImpl.insert(-)");
			if (count == 0) {

				student.setStatus("Active");
				student.setUserType("student");
				student.setEmail(LowerCaseEmail);
				student.setPassword(passwordEncoder.encode(student.getPassword()));

				studentReturn = userRepository.save(student);

				stud.setUserId(studentReturn.getUserId());
				stud.setStudentEmail(LowerCaseEmail);
				stud.setStudentName(studentReturn.getName());
				
				studentRepository.save(stud);

				headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				body = new HashMap<String, String>();
				body.put("email", student.getEmail());
				body.put("name", studentReturn.getName());
				body.put("type", "student");
				emailController.welcomeAndOnBoardEmail(body);

				LOGGER.info("Data successfully saved using StudentServiceImpl.insert(-)");
			} else {
				throw new StudentUserDefindException("This Email Is Already Present !!");
			}

			return studentReturn;
		} catch (Exception e) {
			LOGGER.error("Data Insertion failed using StudentServiceImpl.insert(-): " + e);
			throw new StudentUserDefindException(e.getMessage());
		}
	}
	
	public UserProfile createStudentIntoDatabaseFromExcel(UserProfile student,Long universityId) {

		LOGGER.debug("Inside StudentServiceImpl.insert(-)");

		UserProfile studentReturn = null;
		HttpHeaders headers = null;
		Map<String, String> body = null;
		Student stud = new Student();

		String LowerCaseEmail = student.getEmail().toLowerCase();
		Long count = userRepository.getDetailsByEmail(LowerCaseEmail, "student");

		try {
			LOGGER.debug("Inside try block of StudentServiceImpl.insert(-)");
			if (count == 0) {

				student.setStatus("Active");
				student.setUserType("student");
				student.setEmail(LowerCaseEmail);
				student.setPassword(passwordEncoder.encode(student.getPassword()));

				studentReturn = userRepository.save(student);

				stud.setUserId(studentReturn.getUserId());
				stud.setStudentEmail(LowerCaseEmail);
				stud.setStudentName(studentReturn.getName());
				stud.setUniversityId(universityId);
				studentRepository.save(stud);

				headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				body = new HashMap<String, String>();
				body.put("email", student.getEmail());
				body.put("name", studentReturn.getName());
				body.put("type", "student");
				emailController.welcomeAndOnBoardEmail(body);

				LOGGER.info("Data successfully saved using StudentServiceImpl.insert(-)");
			} else {
				throw new StudentUserDefindException("This Email Is Already Present !!");
			}

			return studentReturn;
		} catch (Exception e) {
			LOGGER.error("Data Insertion failed using StudentServiceImpl.insert(-): " + e);
			throw new StudentUserDefindException(e.getMessage());
		}
	}

	public UserProfile updateUserAttributeByBean(UserProfile user, UserBean userBean) {
		if(userBean.getName()!=null) {
			user.setName(userBean.getName());
		}
		if(userBean.getEmail()!=null) {
			user.setEmail(userBean.getEmail());
		}
		if(userBean.getPhoneNo()!=null) {
			user.setPhoneNo(userBean.getPhoneNo());
		}
		if(userBean.getAddress()!=null) {
			user.setAddress(userBean.getAddress());
		}
		if(userBean.getFile()!=null)
		{
			user.setImageUrl(awss3Service.uploadFile(userBean.getFile()));
		}
		user.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
	
		return user;
	}
	@Override
	public double getProfileUpdateStatusForStudentByStudentAndUserProfile(Student student,UserProfile studentUserProfile) {
		LOGGER.info("inside getProfileUpdateStatusForStudentByStudentAndUserProfile()");
		int total = 0;
		int completed = 0;
//		for mba mca
		if(student.getStream()!=null && (student.getStream().getId()==273 || student.getStream().getId()==281 )) {
			if(student.getBatch()==null) {
				total++;
			}
			else {
				completed++;
				total++;
			}
		}else {
			if(student.getBatch()==null) {
				total++;
			}
			else {
				completed++;
				total++;
			}
			if(student.getBranch()==null) {
				total++;
			}
			else {
				completed++;
				total++;
			}
		}
		if(student.getCgpa()==null) {
			total++;
		}
		else {
			completed++;
			total++;
		}
		if(studentUserProfile.getEducations()==null || studentUserProfile.getEducations().size()==0) {
			total++;
		}
		else {
			completed++;
			total++;
		}
		if(studentUserProfile.getSkills()==null || studentUserProfile.getSkills().size()==0) 
		{
			total++;
		}
		else {
			completed++;
			total++;
		}
		if(student.getStudentName()==null || student.getStudentName().equals("")) {
			total++;
		}
		else {
			completed++;
			total++;
		}
		if(studentUserProfile.getEmailVerfication()==true) {
			total++;
		}
		else {
			completed++;
			total++;
		}
		if(student.getStudentPhoneNumber()==null||student.getStudentPhoneNumber().equals("")) {
			total++;
		}
		else {
			completed++;
			total++;
		}
		double percentage = ((double)completed/(double)total);
		LOGGER.info("total : {} completed : {}, percentage :{}",total,completed,percentage);
		LOGGER.info("exiting getProfileUpdateStatusForStudentByStudentAndUserProfile()");
		
		return percentage;
	}
	
	Student updateStudentByUserProfile(Student student,UserProfile studentReturn,UserBean userBean,Optional<StudentBatch> studentBatch,Optional<StudentBranch> studentBranch) {

		student.setStudentName(studentReturn.getName());
		student.setStudentEmail(studentReturn.getEmail());
		if(studentReturn.getImageUrl()!=null) {
			student.setImageUrl(studentReturn.getImageUrl());
		}
		if(studentReturn.getPhoneNo()!=null) {
			student.setStudentPhoneNumber(Long.valueOf(studentReturn.getPhoneNo()));
		}
		student.setStatus(true);
		if(userBean.getUniversityId()!=null) {

			student.setUniversityId(userBean.getUniversityId());
		}
		student.setStudentAddress(studentReturn.getAddress());
		if(userBean.getBranch()!=null) {
			student.setBranch(userBean.getBranch());
		}
		if(userBean.getBatch()!=null) {
			student.setBatch(userBean.getBatch());
		}
		if(userBean.getCgpa()!=null) {
			student.setCgpa(userBean.getCgpa());
		}
		if(userBean.getBatch()!=null) {
				student.setBatch(studentBatch.get().getId());
			
		}
		if(userBean.getBranch()!=null) {
			student.setBranch(studentBranch.get().getId());
		}
		
		if(userBean.getStreamId()!=null) {
			student.setStream(streamRepository.getById(userBean.getStreamId()));
		}
		student.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
		return student;
		
	}
	
	@Override
	public UserBeanResponse updateStudentProfile2(UserBean userBean, String token) throws Exception{
		LOGGER.debug("Inside StudentServiceImpl.updateStudentProfile2(-)");
		Optional<StudentBranch> studentBranch = null;
		Optional<StudentBatch> studentBatch = null;
		String email = Validation.validateToken(token);
		Student student = studentRepository.findByStudentEmail(email);
		UserProfile user  = userRepository.findUserByEmail(email);
		
		//checking for token
		if(student==null) {
			throw new Exception("Invalid token");
		}

		
		if(userBean.getBatch()!=null) {
			System.out.print("inside student batch");
			studentBatch = studentBatchRepository.findById(userBean.getBatch());
			if(!studentBatch.isPresent()) {
				throw new Exception("invalid batch id");
			}
		}
		if(userBean.getBranch()!=null) {
			System.out.print("inside student branch");
			studentBranch = studentBranchRepository.findById(userBean.getBranch());
			if(!studentBranch.isPresent()) {
				throw new Exception("invalid batch id");
			}
		}
		UserProfile studentReturn = this.userRepository.save(updateUserAttributeByBean(user,userBean));
		student = updateStudentByUserProfile(student,studentReturn,userBean,studentBatch,studentBranch);
//		check for profile update
		student.setPercentageOfProfileCompletion(getProfileUpdateStatusForStudentByStudentAndUserProfile(student,studentReturn));
		LOGGER.info(" {}",student.getPercentageOfProfileCompletion());
		student.setProfileUpdationStatus(student.getPercentageOfProfileCompletion()!=1.0?false:true);
		student = studentRepository.save(student);

		
		UserBeanResponse studentBeanReturn = new UserBeanResponse();
		BeanUtils.copyProperties(studentReturn, studentBeanReturn);
		studentBeanReturn.setProfileUpdateStatus(student.getProfileUpdationStatus());
		studentBeanReturn.setStudentBatchName(userBean.getBatch()!=null?studentBatch.get().getBatchName():"");
		studentBeanReturn.setStudentBranchName(userBean.getBranch()!=null?studentBranch.get().getBranchName():"");
		studentBeanReturn.setUniversityName(student.getUniversityId()!=null?universityRepository.getById(student.getUniversityId()).getUniversityName():"");
		studentBeanReturn.setBatch(student.getBatch());
		studentBeanReturn.setBranch(student.getBranch());
		studentBeanReturn.setCgpa(student.getCgpa());
		studentBeanReturn.setUniversityId(student.getUniversityId());
		studentBeanReturn.setStream(student.getStream());
		studentBeanReturn.setImageUrl(student.getImageUrl());
		studentBeanReturn.setPercentageOfProfileCompletion(student.getPercentageOfProfileCompletion());
		LOGGER.info(
				"Data Successfully updated using StudentServiceImpl.updateStudentProfile(-)");
	
		return studentBeanReturn;
	}

//	@Override
//	public UserBean updateStudentProfile(UserBean studentBean, String token) throws Exception {
//
//		LOGGER.debug("Inside StudentServiceImpl.updateStudentProfile(-)");
//
//		UserProfile student = null;
//		UserProfile studentReturn = null;
//		Optional<UserProfile> optional = null;
//		UserBean studentBeanReturn = null;
//		Student stud = new Student();
//		UserProfile userProfile = null;
//
//		String[] chunks1 = token.split(" ");
//		String[] chunks = chunks1[1].split("\\.");
//		Base64.Decoder decoder = Base64.getUrlDecoder();
//
//		String payload = new String(decoder.decode(chunks[1]));
//		JSONParser jsonParser = new JSONParser();
//		Object obj = jsonParser.parse(payload);
//
//		JSONObject jsonObject = (JSONObject) obj;
//
//		String email = (String) jsonObject.get("sub");
//
//		try {
//			LOGGER.debug("Inside try block of StudentServiceImpl.updateStudentProfile(-)");
//
//			userProfile = userRepository.findByEmail(email, "student");
//
//			if (userProfile != null) {
//
//				String LowerCaseEmail = studentBean.getEmail().toLowerCase();
//				Long count1 = userRepository.getDetailsByEmail(LowerCaseEmail, "student");
//
//				Long count2 = studentRepository.getDetailsByEmail(LowerCaseEmail);
//
//				Optional<University> university = universityRepository.findById(studentBean.getUniversityId());
//
//				if (count1 == 1 && count2 == 1) {
//
//					optional = userRepository.findById(userProfile.getUserId());
//
//					Optional<Student> studOptional = studentRepository.getStudentDetails(userProfile.getUserId());
//
//					if (university.isPresent()) {
//
//						if (!optional.isEmpty()) {
//
//							if (studOptional.isPresent()) {
//								student = optional.get();
//								stud = studOptional.get();
//
//								if (student != null) {
//
//									student.setName(studentBean.getName());
//									student.setEmail(studentBean.getEmail());
//									student.setPhoneNo(studentBean.getPhoneNo());
//									student.setImage(studentBean.getImage());
//									student.setAddress(studentBean.getAddress());
//									student.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
//
//									studentReturn = userRepository.save(student);
//
//									stud.setStudentName(studentReturn.getName());
//									stud.setStudentEmail(studentReturn.getEmail());
//									stud.setStudentImage(studentBean.getImage());
//									stud.setStudentPhoneNumber(Long.valueOf(studentBean.getPhoneNo()));
//									stud.setStatus(true);
//									stud.setUniversityId(studentBean.getUniversityId());
//									stud.setStudentAddress(studentBean.getAddress());
//									stud.setBranch(studentBean.getBranch());
//									stud.setBatch(studentBean.getBatch());
//									stud.setCgpa(studentBean.getCgpa());
//									stud.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
//
//									stud = studentRepository.save(stud);
//
//									studentBeanReturn = new UserBean();
//									BeanUtils.copyProperties(studentReturn, studentBeanReturn);
//
//									studentBeanReturn.setBatch(stud.getBatch());
//									studentBeanReturn.setBranch(stud.getBranch());
//									studentBeanReturn.setCgpa(stud.getCgpa());
//									studentBeanReturn.setUniversityId(stud.getUniversityId());
//
//									LOGGER.info(
//											"Data Successfully updated using StudentServiceImpl.updateStudentProfile(-)");
//								}
//							}
//						}
//					} else {
//						throw new StudentUserDefindException("This University Is Not Present !!");
//					}
//				} else {
//					throw new StudentUserDefindException("This Email Is Already Present !!");
//				}
//			} else {
//				throw new StudentUserDefindException("No Data Found !!");
//			}
//
//			return studentBeanReturn;
//		} catch (Exception e) {
//			LOGGER.error("Error occured in StudentServiceImpl.updateStudentProfile(-): " + e);
//			throw new StudentUserDefindException(e.getMessage());
//		}
//	}

//	@Override
//	public StudentBean findStudentById(Long studentId) {
//		LOGGER.debug("Inside StudentServiceImpl.findStudentById(-)");
//		Student student=null;
//		Optional<Student> optional=null;
//		StudentBean studentBean=null;
//		try {
//			LOGGER.debug("Inside try block of StudentServiceImpl.findStudentById(-)");
//			optional=studentRepository.findById(studentId);
//			if(!optional.isEmpty()) {
//				student=optional.get();
//				if(student!=null) {
//					studentBean=new StudentBean();
//					BeanUtils.copyProperties(student, studentBean);
//					LOGGER.info("Data Successfully fetched using StudentServiceImpl.findStudentById(-)");
//				}
//			}
//			return studentBean;
//		}
//		catch (Exception e) {
//			LOGGER.error("Error occured in StudentServiceImpl.findStudentById(-): "+e);
//			throw new StudentUserDefindException(e.getMessage());
//		}
//	}

	@Override
	public UserBean findStudentById(Long studentId) {
		LOGGER.debug("Inside StudentServiceImpl.findStudentById(-)");
		UserProfile student = null;
		Optional<UserProfile> optional = null;
		UserBean studentBean = null;
		try {
			LOGGER.debug("Inside try block of StudentServiceImpl.findStudentById(-)");
			optional = userRepository.findById(studentId);
			if (!optional.isEmpty()) {
				student = optional.get();
				if (student != null) {
					studentBean = new UserBean();
					BeanUtils.copyProperties(student, studentBean);
					LOGGER.info("Data Successfully fetched using StudentServiceImpl.findStudentById(-)");
				}
			}
			return studentBean;
		} catch (Exception e) {
			LOGGER.error("Error occured in StudentServiceImpl.findStudentById(-): " + e);
			throw new StudentUserDefindException(e.getMessage());
		}
	}

	@Override
	public List<UserBean> getAllStudents(String token) throws Exception {
		String email = Validation.validateToken(token);
		University university = universityRepository.findByEmail(email);
		if(university==null) {
			throw new Exception("no university found");
		}
		
		LOGGER.debug("Inside StudentServiceImpl.getAllStudents()");
		List<UserProfile> students = null;
		UserBean studentBean = null;
		List<UserBean> studentBeans = null;
		System.out.println("universityId:"+university.getUniversityId());
		try {
			LOGGER.debug("Inside try block of StudentServiceImpl.getAllStudents()");
			students = userRepository.getAllStudents(university.getUniversityId());
//			System.out.println(students);
			if (students != null && students.size() > 0) {
				studentBeans = new ArrayList<UserBean>();
				for (UserProfile student : students) {
					studentBean = new UserBean();
					BeanUtils.copyProperties(student, studentBean);
					studentBeans.add(studentBean);
				}
			}
			LOGGER.info("Data Successfully fetched using StudentServiceImpl.getAllStudents()");
			return studentBeans;
		} catch (Exception e) {
			LOGGER.error("Error occured in StudentServiceImpl.getAllStudents(): " + e);
			throw new StudentUserDefindException(e.getMessage());
		}
	}

	@Override
	public Map<String,Object> importStudentDataExcel(MultipartFile file,String token) throws Exception {
		Map<String,Object> output = new HashMap<String,Object>();
		String email = Validation.validateToken(token);
		University university = universityRepository.findByEmail(email);
		LOGGER.debug("StudentServiceImpl.importStudentDataExcel(-)");
		Path path = null;
		File tempFile = null;
		Workbook workbook = null;
		Sheet sheet = null;
		Supplier<Stream<Row>> rowStreamSupplier = null;
		Row headerRow = null;
		List<Map<String, String>> studentLists = null;
		int passwordLength = 12;
		String generatedPassword = null;
		UserProfile studentProfile = null;
		UserProfile studentProfileReturn = null;
		List<UserBean> allStudentLists = null;
		UserBean studentReturnBean = null;

		try {
			LOGGER.debug("Inside try block of StudentServiceImpl.importStudentDataExcel(-)");
			path = Files.createTempDirectory("");
			tempFile = path.resolve(file.getOriginalFilename()).toFile();
			file.transferTo(tempFile);
			workbook = WorkbookFactory.create(tempFile);
			sheet = workbook.getSheetAt(0);
			rowStreamSupplier = excelUploadUtil.getRowStreamSupplier(sheet);
			headerRow = rowStreamSupplier.get().findFirst().get();
			List<String> headerCells = excelUploadUtil.getStream(headerRow).map(Cell::getStringCellValue)
					.collect(Collectors.toList());
			int colCount = headerCells.size();
			studentLists = rowStreamSupplier.get().skip(1).map(row -> {
				excelUploadUtil.getStream(row).forEach(cell -> {
					cell.setCellType(CellType.STRING);
				});
				List<String> cellList = excelUploadUtil.getStream(row).map(Cell::getStringCellValue)
						.collect(Collectors.toList());
				return excelUploadUtil.cellIteratorSupplier(colCount).get()
						.collect(toMap(headerCells::get, cellList::get));
			}).collect(Collectors.toList());
			// integrate with database to save all students are there in excel and sent them
			// email using MicroServices
			allStudentLists = new ArrayList<UserBean>();
			List<String> existingStudents = new ArrayList<>();
			for (Map<String, String> student : studentLists) {
				studentProfile = new UserProfile();
				studentProfile.setName(student.get("Name"));
				studentProfile.setEmail(student.get("Email"));
				studentProfile.setPhoneNo(student.get("Phone"));
				generatedPassword = generateRandomPassword(passwordLength);
				studentProfile.setPassword(generatedPassword);
				
				studentProfileReturn = insertForExcel(studentProfile,university.getUniversityId(),university.getUniversityName(),existingStudents);
				if(studentProfileReturn!=null) {
					studentReturnBean = new UserBean();
					BeanUtils.copyProperties(studentProfileReturn, studentReturnBean);
					// this password is not encrypted this is the original random password and
					// encrypted password is stored in database
					studentReturnBean.setPassword(generatedPassword);
					
					allStudentLists.add(studentReturnBean);
				}
				
			}
//			headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
//			reqBodyData = new ObjectMapper().writeValueAsString(allStudentLists);
//			requestEntity = new HttpEntity<String>(reqBodyData, headers);
//			emailController.welcomeEmailList(allStudentLists);
//			restTemplate.exchange(welcomeListUrl, HttpMethod.POST, requestEntity, String.class);
			LOGGER.info(
					"Student data import Successfully and mail sent using StudentServiceImpl.importStudentDataExcel(-)");
			output.put("existingStudents", existingStudents);
			output.put("importedStudents", allStudentLists);
			return output;
		} catch (Exception e) {
			LOGGER.error("Error occured in StudentServiceImpl.importStudentDataExcel(-)");
			throw new StudentUserDefindException(e.getMessage());
		}	}

	private String generateRandomPassword(int passwordLength) {
		Random random = null;
		StringBuilder stringBuilder = null;
		final String chars1 = "0123456789";
		final String chars2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String chars3 = "abcdefghijklmnopqrstuvwxyz!@#$%&";
		final String chars4 = "!@#$%&";
		random = new Random();
		stringBuilder = new StringBuilder(passwordLength);
		for (int i = 0; i < passwordLength / 4; i++) {
			stringBuilder.append(chars1.charAt(random.nextInt(chars1.length())));
			stringBuilder.append(chars2.charAt(random.nextInt(chars2.length())));
			stringBuilder.append(chars3.charAt(random.nextInt(chars3.length())));
			stringBuilder.append(chars4.charAt(random.nextInt(chars4.length())));
		}
		return stringBuilder.toString();
	}

	@Override
	public UniversityJobShareToStudentBean studentJobResponse(UniversityJobShareToStudentBean jobBean,String token) throws Exception {
		UniversityJobShareToStudentBean jobShareBean = new UniversityJobShareToStudentBean();
		UniversityJobShareToStudent universityJobShareToStudent = null;
		try {
			LOGGER.debug("Inside StudentServiceImpl.universityResponse(-)");
			Optional<UniversityJobShareToStudent> optional = universityJobShareRepository.findById(jobBean.getID());
			universityJobShareToStudent = new UniversityJobShareToStudent();
			universityJobShareToStudent = optional.get();
			CampusDriveResponse campusDriveResponse = this.campusDriveResponseRepository.findByUniversityIdAndJobId(universityJobShareToStudent.getUniversityId(), universityJobShareToStudent.getJobId());
			if(campusDriveResponse!=null) {
				throw new Exception("no longer accepting responses");
			}
				
			Student student = this.studentRepository.getById(universityJobShareToStudent.getStudentId());
			if(student.getProfileUpdationStatus()==null || !student.getProfileUpdationStatus() ) {
				throw new Exception("please update your profile first!");
			}
			UserProfile userProfile = this.userRepository.getById(student.getUserId());
			if(userProfile.getSkills()==null || userProfile.getSkills().isEmpty() ) {
				throw new Exception("please enter some skills!");
			}
			if(userProfile.getEducations()==null || userProfile.getEducations().isEmpty() ) {
				throw new Exception("please complete your education detials");
			}
				
			if (universityJobShareToStudent != null) {

				universityJobShareToStudent.setStudentResponseStatus(jobBean.getStudentResponseStatus());
				if(!jobBean.getFeedBack().equals("")) {

					universityJobShareToStudent.setFeedBack(jobBean.getFeedBack());
				}
				universityJobShareToStudent.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
				universityJobShareToStudent.setUpdatedBy("Biswa");

				universityJobShareRepository.save(universityJobShareToStudent);

				BeanUtils.copyProperties(universityJobShareToStudent, jobShareBean);

			}
			LOGGER.info("Data Updated Successfully In StudentServiceImpl.universityResponse(-)");

		} catch (NoSuchElementException e) {
			LOGGER.info("Data Updatation Failed In StudentServiceImpl.universityResponse(-)" + e);
			throw new UniversityJobShareToStudentException("Please Re-Check This Job May Not Be Available Now !!");
		} catch (Exception e) {
			jobShareBean.setResponse("FAILED");
			LOGGER.info("Data Updatation Failed In StudentServiceImpl.universityResponse(-)" + e);
			throw e;
		}
		return jobShareBean;
	}

	@Override
	public List<?> jobDetails(String token) throws ParseException {
		String[] chunks1 = token.split(" ");
		String[] chunks = chunks1[1].split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();

		String payload = new String(decoder.decode(chunks[1]));
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(payload);

		JSONObject jsonObject = (JSONObject) obj;

		String email = (String) jsonObject.get("sub");
		List<Student> studentList = new ArrayList<Student>();
		UniversitySharedJobList universitySharedJob = null;
		List<UniversitySharedJobList> universitySharedJobList = new ArrayList<UniversitySharedJobList>();

		studentList = studentRepository.getDetailsByEmail1(email);
//		student -> university id -> university
		if(studentList.get(0).getUniversityId()==null) {
			throw new NoSuchElementException("You are not associated with any university");
		}
		University university = universityRepository.getById(studentList.get(0).getUniversityId());
	
		try {
			if (studentList.size() == 1) {
				
				LOGGER.info("jobRepository.getStudentJobAllDetails {} {}",university.getUniversityId(),
						studentList.get(0).getStudentId());
				List<Object[]> list = jobRepository.getStudentJobAllDetails(university.getUniversityId(),
						studentList.get(0).getStudentId());

				if (list.size() != 0) {
					for (Object[] obj1 : list) {
						universitySharedJob = new UniversitySharedJobList();
						
						universitySharedJob.setJobId((Long) obj1[0]);
						universitySharedJob.setJobTitle((String) obj1[1]);
						universitySharedJob.setCategory((String) obj1[2]);
						universitySharedJob.setJobType((String) obj1[3]);
						universitySharedJob.setWfhCheckbox((Boolean) obj1[4]);
						universitySharedJob.setSkills((String) obj1[5]);
						universitySharedJob.setCity((String) obj1[6]);
						universitySharedJob.setOpenings((Integer) obj1[7]);
						universitySharedJob.setSalary((Double) obj1[8]);
						universitySharedJob.setAbout((String) obj1[9]);
						universitySharedJob.setDescription((String) obj1[10]);
						universitySharedJob.setSharedJobId((Long) obj1[11]);
						universityJobShareRepository.setStudentSeenById(universitySharedJob.getSharedJobId());
						universitySharedJob.setStudentResponse((Boolean) obj1[12]);
						Corporate corporate = this.corporateRepository.getById((Long) obj1[13]);
						universitySharedJob.setCorporate(corporate);
						universitySharedJobList.add(universitySharedJob);
					}

				} else {
					throw new UniversityException("Sorry, No Job's Shared For You !!");
				}
			} else {
				throw new UniversityException("Something Went Wrong !! Student Duplicte Data Found !!");
			}
		} catch (Exception e) {
			throw e;
		}

		return universitySharedJobList;
	}

	@Override
	public Map<String,Object> getAllJobApplicationsByStudent(Long studentId) {
		LOGGER.debug("Inside StudentServiceImpl.getAllJobApplicationsByStudent(-)");
		List<JobApply> jobApplies = null;
		List<JobApplyResponseBean> jobApplyResponseBeans = null;
		Map<String,Object> map = null;
		try {
			jobApplies = jobApplyRepository.getAllJobApplicationsByStudentId(studentId);
			if(jobApplies != null && jobApplies.size()>0) {
				
				jobApplyResponseBeans = new ArrayList<>();
				for(JobApply j:jobApplies)
				{
					JobApplyResponseBean jr = new JobApplyResponseBean();
					BeanUtils.copyProperties(j, jr);
					Corporate corporate = corporateRepository.getById(j.getCorporateId());
					Job job = jobRepository.getById(j.getJobId());
					jr.setJob(job);
					jr.setCorporate(corporate);
					jobApplyResponseBeans.add(jr);
				}
				map = new HashMap<String, Object>();
				map.put("status", "success");
				map.put("responseCode", 500);
				map.put("data", jobApplyResponseBeans);
				return map;
			}
			else {
				map = new HashMap<String, Object>();
				map.put("status", "Failed");
				map.put("responseCode", 500);
				map.put("message", "You are not applied for any job!!!");
				return map;
			}
		}
		catch (Exception e) {
			LOGGER.info("Data getting Failed In StudentServiceImpl.universityResponse(-)" + e);
			e.printStackTrace();
			map = new HashMap<String, Object>();
			map.put("status", "Failed");
			map.put("responseCode", 500);
			map.put("message", "Bad Request!!!");
			return map;
		}
	}

	
	@Override
	public Map<String,Object> addSkillToAStudent(Skill skill, String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findUserByEmail(email);
		Map<String,Object> result = new HashMap<String,Object>();
		
		skill.setName(skill.getName().toLowerCase());
		
		Skill skillExist = skillRespository.findByName(skill.getName());
		
		if(skillExist==null) {
			;
			skillExist = this.skillRespository.save(skill);
		}
		if(userProfile.getSkills().contains(skill)){
			throw new Exception("already skill exist");
		}
		
		userProfile.getSkills().add(skillExist);
		this.userRepository.save(userProfile);
		result.put("skill", skill.getName());
		return result;
	}

	@Override
	public Map<String, Object> deleteSkillOfAStudent(int id, String token) throws Exception {
		
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findUserByEmail(email);
		Map<String,Object> result = new HashMap<String,Object>();
		
		Skill skill = skillRespository.getById(id);
		if(skill==null) {
			throw new Exception("no such skill exist");
		}
		Boolean ans = userProfile.getSkills().remove(skill);
		this.userRepository.save(userProfile);
		
		return result;
		
	}
	@Override
	public void deleteExperienceOfAStudent(int id,String token) throws Exception{
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findUserByEmail(email);
		Optional<Experience> optional = experienceRepository.findById(id);
		if(optional.isEmpty()) {
			throw new Exception("no such experience found");
		}
		Experience experience = optional.get();
		if(experience.getUserProfile().getUserId() != userProfile.getUserId())
		{
			throw new Exception("unauthorized");
		}
		this.experienceRepository.delete(experience);
	}
	
	@Override
	public List<Student> getAllStudentsAccoridngToBranchBatchCgpaFilter(Long branchId,Long batchId,Double cgpa,Long universityId){

		List<Student> students = studentRepository.findAll(new Specification<Student>() {

			@Override
			public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate p = criteriaBuilder.conjunction();
				if(Objects.nonNull(branchId)){
					p = criteriaBuilder.and(p,criteriaBuilder.equal(root.get("branch"),branchId));
				}
				if(Objects.nonNull(batchId)) {
					p = criteriaBuilder.and(p,criteriaBuilder.equal(root.get("batch"),batchId));
				}
				if(Objects.nonNull(cgpa)) {
					p = criteriaBuilder.and(p,criteriaBuilder.greaterThanOrEqualTo(root.get("cgpa"),cgpa));
				}
				if(Objects.nonNull(universityId)) {
					p = criteriaBuilder.and(p,criteriaBuilder.equal(root.get("universityId"),universityId));
				}
				return p;
			}
		});
		return students;
	
	}
	@Override
	public List<Student> getAllStudentsAccoridngToStreamBranchBatchCgpaFilter(com.hirekarma.model.Stream stream,Long branchId,Long batchId,Double cgpa,Long universityId){

		List<Student> students = studentRepository.findAll(new Specification<Student>() {

			@Override
			public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate p = criteriaBuilder.conjunction();
				if(Objects.nonNull(branchId)){
					p = criteriaBuilder.and(p,criteriaBuilder.equal(root.get("branch"),branchId));
				}
				if(Objects.nonNull(stream)) {
					p = criteriaBuilder.and(p,criteriaBuilder.equal(root.get("stream"),stream));
				}
				if(Objects.nonNull(batchId)) {
					p = criteriaBuilder.and(p,criteriaBuilder.equal(root.get("batch"),batchId));
				}
				if(Objects.nonNull(cgpa)) {
					p = criteriaBuilder.and(p,criteriaBuilder.greaterThanOrEqualTo(root.get("cgpa"),cgpa));
				}
				if(Objects.nonNull(universityId)) {
					p = criteriaBuilder.and(p,criteriaBuilder.equal(root.get("universityId"),universityId));
				}
				return p;
			}
		});
		return students;
	
	}
	
//	@Override
//	public StudentBean checkLoginCredentials(String email, String password) {
//		LOGGER.debug("Inside StudentServiceImpl.checkLoginCredentials(-,-)");
//		StudentBean studentBean=null;
//		Student student=null;
//		HirekarmaPasswordVerifier verifier= null;
//		String encryptedPassword=null;
//		try {
//			LOGGER.debug("Inside try block of StudentServiceImpl.checkLoginCredentials(-,-)");
//			verifier= new HirekarmaPasswordVerifier();
//			encryptedPassword=verifier.getEncriptedString(password);
//			student=studentRepository.checkLoginCredentials(email, encryptedPassword);
//			if(student!=null) {
//				LOGGER.info("student credential match using StudentServiceImpl.checkLoginCredentials(-,-)");
//				studentBean=new StudentBean();
//				BeanUtils.copyProperties(student, studentBean);
//				return studentBean;
//			}
//			else {
//				LOGGER.info("student credential does not match using StudentServiceImpl.checkLoginCredentials(-,-)");
//				return null;
//			}
//		}
//		catch (Exception e) {
//			LOGGER.info("Error occured in StudentServiceImpl.checkLoginCredentials(-,-): "+e);
//			throw new StudentUserDefindException(e.getMessage());
//		}
//	}

//	@Override
//	public UserBean updateStudentProfile(UserBean studentBean) {
//		LOGGER.debug("Inside StudentServiceImpl.updateStudentProfile(-)");
//		Student student=null;
//		Student studentReturn=null;
//		Optional<Student> optional=null;
//		StudentBean studentBean=null;
//		try {
//			LOGGER.debug("Inside try block of StudentServiceImpl.updateStudentProfile(-)");
//			optional=studentRepository.findById(bean.getStudentId());
//			student=optional.get();
//			if(!optional.isEmpty()) {
//				if(student!=null) {
//					student.setName(bean.getName());
//					student.setEmail(bean.getEmail());
//					student.setPhoneNO(bean.getPhoneNO());
//					student.setProfileImage(bean.getProfileImage());
//					student.setAddress(bean.getAddress());
//					student.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
//					studentReturn=studentRepository.save(student);
//					studentBean=new StudentBean();
//					BeanUtils.copyProperties(studentReturn, studentBean);
//					LOGGER.info("Data Successfully updated using StudentServiceImpl.updateStudentProfile(-)");
//				}
//			}
//			return studentBean;
//		}
//		catch (Exception e) {
//			LOGGER.error("Error occured in StudentServiceImpl.updateStudentProfile(-): "+e);
//			throw new StudentUserDefindException(e.getMessage());
//		}
//	}

}
