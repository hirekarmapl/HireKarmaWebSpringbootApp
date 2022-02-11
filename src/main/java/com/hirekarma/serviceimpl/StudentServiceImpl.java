package com.hirekarma.serviceimpl;

import static java.util.stream.Collectors.toMap;
import com.hirekarma.repository.SkillRespository;
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
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.UniversityJobShareToStudentBean;
import com.hirekarma.beans.UniversitySharedJobList;
import com.hirekarma.beans.UserBean;
import com.hirekarma.email.controller.EmailController;
import com.hirekarma.exception.StudentUserDefindException;
import com.hirekarma.exception.UniversityException;
import com.hirekarma.exception.UniversityJobShareToStudentException;
import com.hirekarma.model.Education;
import com.hirekarma.model.Experience;
import com.hirekarma.model.Project;
import com.hirekarma.model.Skill;
import com.hirekarma.model.Student;
import com.hirekarma.model.University;
import com.hirekarma.model.UniversityJobShareToStudent;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.EducationRepository;
import com.hirekarma.repository.ExperienceRepository;
import com.hirekarma.repository.JobRepository;
import com.hirekarma.repository.ProjectRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.repository.UniversityJobShareRepository;
import com.hirekarma.repository.UniversityRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.SkillService;
import com.hirekarma.service.StudentService;
import com.hirekarma.utilty.ExcelUploadUtil;
import com.hirekarma.utilty.Validation;

@Service("studentServiceImpl")
public class StudentServiceImpl implements StudentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

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

	public UserProfile addAllSkillsToStudent(List<Skill> skills, String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile userProfile = this.userRepository.findUserByEmail(email);
		List<Skill> skillToBeSaved = null;
		userProfile.setSkills(new ArrayList<Skill>());

		for (Skill s : skills) {
			Skill checkIfSaved = this.skillRespository.findByName(s.getName());
			if (checkIfSaved == null) {
				checkIfSaved = this.skillRespository.save(s);
			}
			userProfile.getSkills().add(checkIfSaved);

		}
		return this.userRepository.save(userProfile);

	}

	@Override
	public UserProfile addAllExperienceToStudent(List<Experience> experience, String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile user = userRepository.findByEmail(email, "student");
		if (user == null) {
			throw new Exception("user not found");
		}
		System.out.println(user.getExperiences());
		experienceRepository.deleteAll(user.getExperiences());
		for (Experience e : experience) {
			e.setUserProfile(user);
		}

		List<Experience> experienceDB = experienceRepository.saveAll(experience);

		return user;
	}

	@Override
	public List<Education> addAllEducationToStudent(List<Education> education, String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile user = userRepository.findByEmail(email, "student");
		if (user == null) {
			throw new Exception("user not found");
		}
		System.out.println(user.getExperiences());
		educationRepository.deleteAll(user.getEducations());
		
		for (Education e : education) {
			e.setUserProfile(user);
		}

		List<Education> educationDB = educationRepository.saveAll(education);

		return educationDB;
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

	@Override
	public UserProfile addSkill(int skillId, String token) throws Exception {
		// TODO Auto-generated method stub
		UserProfile user = null;
		Skill skill = null;
		try {

			LOGGER.debug("Inside StudentServicimpl.addSkill(-)");

//			getting email from token	
			String email = Validation.validateToken(token);
			user = userRepository.findByEmail(email, "student");
			skill = skillRespository.getById(skillId);
			if (!(skill != null && user != null)) {
				throw new Exception("user or skill not found");
			}
			user.getSkills().add(skill);

			user = this.userRepository.save(user);
			return user;

		} catch (Exception e) {
			throw e;
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

				emailController.welcomeEmail(body);

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

	@Override
	public UserBean updateStudentProfile(UserBean studentBean, String token) throws Exception {

		LOGGER.debug("Inside StudentServiceImpl.updateStudentProfile(-)");

		UserProfile student = null;
		UserProfile studentReturn = null;
		Optional<UserProfile> optional = null;
		UserBean studentBeanReturn = null;
		Student stud = new Student();
		UserProfile userProfile = null;

		String[] chunks1 = token.split(" ");
		String[] chunks = chunks1[1].split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();

		String payload = new String(decoder.decode(chunks[1]));
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(payload);

		JSONObject jsonObject = (JSONObject) obj;

		String email = (String) jsonObject.get("sub");

		try {
			LOGGER.debug("Inside try block of StudentServiceImpl.updateStudentProfile(-)");

			userProfile = userRepository.findByEmail(email, "student");

			if (userProfile != null) {

				String LowerCaseEmail = studentBean.getEmail().toLowerCase();
				Long count1 = userRepository.getDetailsByEmail(LowerCaseEmail, "student");

				Long count2 = studentRepository.getDetailsByEmail(LowerCaseEmail);

				Optional<University> university = universityRepository.findById(studentBean.getUniversityId());

				if (count1 == 1 && count2 == 1) {

					optional = userRepository.findById(userProfile.getUserId());

					Optional<Student> studOptional = studentRepository.getStudentDetails(userProfile.getUserId());

					if (university.isPresent()) {

						if (!optional.isEmpty()) {

							if (studOptional.isPresent()) {
								student = optional.get();
								stud = studOptional.get();

								if (student != null) {

									student.setName(studentBean.getName());
									student.setEmail(studentBean.getEmail());
									student.setPhoneNo(studentBean.getPhoneNo());
									student.setImage(studentBean.getImage());
									student.setAddress(studentBean.getAddress());
									student.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));

									studentReturn = userRepository.save(student);

									stud.setStudentName(studentReturn.getName());
									stud.setStudentEmail(studentReturn.getEmail());
									stud.setStudentImage(studentBean.getImage());
									stud.setStudentPhoneNumber(Long.valueOf(studentBean.getPhoneNo()));
									stud.setStatus(true);
									stud.setUniversityId(studentBean.getUniversityId());
									stud.setStudentAddress(studentBean.getAddress());
									stud.setBranch(studentBean.getBranch());
									stud.setBatch(studentBean.getBatch());
									stud.setCgpa(studentBean.getCgpa());
									stud.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));

									stud = studentRepository.save(stud);

									studentBeanReturn = new UserBean();
									BeanUtils.copyProperties(studentReturn, studentBeanReturn);

									studentBeanReturn.setBatch(stud.getBatch());
									studentBeanReturn.setBranch(stud.getBranch());
									studentBeanReturn.setCgpa(stud.getCgpa());
									studentBeanReturn.setUniversityId(stud.getUniversityId());

									LOGGER.info(
											"Data Successfully updated using StudentServiceImpl.updateStudentProfile(-)");
								}
							}
						}
					} else {
						throw new StudentUserDefindException("This University Is Not Present !!");
					}
				} else {
					throw new StudentUserDefindException("This Email Is Already Present !!");
				}
			} else {
				throw new StudentUserDefindException("No Data Found !!");
			}

			return studentBeanReturn;
		} catch (Exception e) {
			LOGGER.error("Error occured in StudentServiceImpl.updateStudentProfile(-): " + e);
			throw new StudentUserDefindException(e.getMessage());
		}
	}

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
	public List<UserBean> getAllStudents() {
		LOGGER.debug("Inside StudentServiceImpl.getAllStudents()");
		List<UserProfile> students = null;
		UserBean studentBean = null;
		List<UserBean> studentBeans = null;
		try {
			LOGGER.debug("Inside try block of StudentServiceImpl.getAllStudents()");
			students = userRepository.getAllStudents();
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
	public List<UserBean> importStudentDataExcel(MultipartFile file) {
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
			for (Map<String, String> student : studentLists) {
				studentProfile = new UserProfile();
				studentProfile.setName(student.get("Name"));
				studentProfile.setEmail(student.get("Email"));
				generatedPassword = generateRandomPassword(passwordLength);
				studentProfile.setPassword(generatedPassword);
				studentProfileReturn = insert(studentProfile);
				studentReturnBean = new UserBean();
				BeanUtils.copyProperties(studentProfileReturn, studentReturnBean);
				// this password is not encrypted this is the original random password and
				// encrypted password is stored in database
				studentReturnBean.setPassword(generatedPassword);
				allStudentLists.add(studentReturnBean);
			}
//			headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
//			reqBodyData = new ObjectMapper().writeValueAsString(allStudentLists);
//			requestEntity = new HttpEntity<String>(reqBodyData, headers);
			emailController.welcomeEmailList(allStudentLists);
//			restTemplate.exchange(welcomeListUrl, HttpMethod.POST, requestEntity, String.class);
			LOGGER.info(
					"Student data import Successfully and mail sent using StudentServiceImpl.importStudentDataExcel(-)");
			return allStudentLists;
		} catch (Exception e) {
			LOGGER.error("Error occured in StudentServiceImpl.importStudentDataExcel(-)");
			throw new StudentUserDefindException(e.getMessage());
		}
	}

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
	public UniversityJobShareToStudentBean studentJobResponse(UniversityJobShareToStudentBean jobBean) {
		UniversityJobShareToStudentBean jobShareBean = new UniversityJobShareToStudentBean();
		UniversityJobShareToStudent universityJobShareToStudent = null;
		try {
			LOGGER.debug("Inside UniversityServiceImpl.universityResponse(-)");
			Optional<UniversityJobShareToStudent> optional = universityJobShareRepository.findById(jobBean.getID());
			universityJobShareToStudent = new UniversityJobShareToStudent();
			universityJobShareToStudent = optional.get();
			if (universityJobShareToStudent != null) {

				universityJobShareToStudent.setStudentResponseStatus(jobBean.getStudentResponseStatus());
				universityJobShareToStudent.setFeedBack(jobBean.getFeedBack());
				universityJobShareToStudent.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
				universityJobShareToStudent.setUpdatedBy("Biswa");

				universityJobShareRepository.save(universityJobShareToStudent);

				BeanUtils.copyProperties(universityJobShareToStudent, jobShareBean);

			}
			LOGGER.info("Data Updated Successfully In UniversityServiceImpl.universityResponse(-)");

		} catch (NoSuchElementException e) {
			LOGGER.info("Data Updatation Failed In UniversityServiceImpl.universityResponse(-)" + e);
			throw new UniversityJobShareToStudentException("Please Re-Check This Job May Not Be Available Now !!");
		} catch (Exception e) {
			jobShareBean.setResponse("FAILED");
			LOGGER.info("Data Updatation Failed In UniversityServiceImpl.universityResponse(-)" + e);
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

		try {
			if (studentList.size() == 1) {
				List<Object[]> list = jobRepository.getStudentJobAllDetails(studentList.get(0).getUniversityId(),
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
						universitySharedJob.setStudentResponse((Boolean) obj1[12]);

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
