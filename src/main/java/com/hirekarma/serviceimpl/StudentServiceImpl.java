package com.hirekarma.serviceimpl;

import static java.util.stream.Collectors.toMap;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hirekarma.beans.ShareJobBean;
import com.hirekarma.beans.UniversityJobShareBean;
import com.hirekarma.beans.UserBean;
import com.hirekarma.exception.StudentUserDefindException;
import com.hirekarma.model.ShareJob;
import com.hirekarma.model.UniversityJobShare;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.UniversityJobShareRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.StudentService;
import com.hirekarma.service.UniversityService;
import com.hirekarma.utilty.ExcelUploadUtil;

@Service("studentServiceImpl")
public class StudentServiceImpl implements StudentService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
//	@Autowired
//	private StudentRepository studentRepository;
	
	@Autowired
	private ExcelUploadUtil excelUploadUtil;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UniversityJobShareRepository universityJobShareRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${mail.service.welcomeListUrl}")
	private String welcomeListUrl;
	
	@Value("${mail.service.welcomeUrl}")
	private String welcomeUrl;
	
	@Value("${mail.service.getStarted}")
	private String getStarted;

//	@Override
//	public Student insert(Student student) {
//		LOGGER.debug("Inside StudentServiceImpl.insert(-)");
//		Student studentReturn=null;
//		try {
//			LOGGER.debug("Inside try block of StudentServiceImpl.insert(-)");
//			student.setStatus("Active");
//			student.setPassword(passwordEncoder.encode(student.getPassword()));
//			studentReturn=studentRepository.save(student);
//			LOGGER.info("Data successfully saved using StudentServiceImpl.insert(-)");
//			return studentReturn;
//		}
//		catch (Exception e) {
//			LOGGER.error("Data Insertion failed using StudentServiceImpl.insert(-): "+e);
//			throw new StudentUserDefindException(e.getMessage());
//		}
//	}
	
	@Override
	public UserProfile insert(UserProfile student) {
		LOGGER.debug("Inside StudentServiceImpl.insert(-)");
		UserProfile studentReturn=null;
		HttpHeaders headers=null;
		Map<String,String> body=null;
		String reqBodyData=null;
		HttpEntity<String> requestEntity=null;
		try {
			LOGGER.debug("Inside try block of StudentServiceImpl.insert(-)");
			student.setStatus("Active");
			student.setUserType("student");
			student.setPassword(passwordEncoder.encode(student.getPassword()));
			studentReturn=userRepository.save(student);
			headers=new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			body=new HashMap<String,String>();
			body.put("email", student.getEmail());
			reqBodyData=new ObjectMapper().writeValueAsString(body);
			requestEntity=new HttpEntity<String>(reqBodyData,headers);
			restTemplate.exchange(welcomeUrl,HttpMethod.POST,requestEntity,String.class);
			restTemplate.exchange(getStarted,HttpMethod.POST,requestEntity,String.class);
			LOGGER.info("Data successfully saved using StudentServiceImpl.insert(-)");
			return studentReturn;
		}
		catch (Exception e) {
			LOGGER.error("Data Insertion failed using StudentServiceImpl.insert(-): "+e);
			throw new StudentUserDefindException(e.getMessage());
		}
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
	
	@Override
	public UserBean updateStudentProfile(UserBean studentBean) {
		LOGGER.debug("Inside StudentServiceImpl.updateStudentProfile(-)");
		UserProfile student=null;
		UserProfile studentReturn=null;
		Optional<UserProfile> optional=null;
		UserBean studentBeanReturn=null;
		try {
			LOGGER.debug("Inside try block of StudentServiceImpl.updateStudentProfile(-)");
			optional=userRepository.findById(studentBean.getUserId());
			if(!optional.isEmpty()) {
				student=optional.get();
				if(student!=null) {
					student.setName(studentBean.getName());
					student.setEmail(studentBean.getEmail());
					student.setPhoneNo(studentBean.getPhoneNo());
					student.setImage(studentBean.getImage());
					student.setAddress(studentBean.getAddress());
					student.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
					studentReturn=userRepository.save(student);
					studentBeanReturn=new UserBean();
					BeanUtils.copyProperties(studentReturn, studentBeanReturn);
					LOGGER.info("Data Successfully updated using StudentServiceImpl.updateStudentProfile(-)");
				}
			}
			return studentBeanReturn;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in StudentServiceImpl.updateStudentProfile(-): "+e);
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
		UserProfile student=null;
		Optional<UserProfile> optional=null;
		UserBean studentBean=null;
		try {
			LOGGER.debug("Inside try block of StudentServiceImpl.findStudentById(-)");
			optional=userRepository.findById(studentId);
			if(!optional.isEmpty()) {
				student=optional.get();
				if(student!=null) {
					studentBean=new UserBean();
					BeanUtils.copyProperties(student, studentBean);
					LOGGER.info("Data Successfully fetched using StudentServiceImpl.findStudentById(-)");
				}
			}
			return studentBean;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in StudentServiceImpl.findStudentById(-): "+e);
			throw new StudentUserDefindException(e.getMessage());
		}
	}
	
	@Override
	public List<UserBean> getAllStudents() {
		LOGGER.debug("Inside StudentServiceImpl.getAllStudents()");
		List<UserProfile> students=null;
		UserBean studentBean=null;
		List<UserBean> studentBeans=null;
		try {
			LOGGER.debug("Inside try block of StudentServiceImpl.getAllStudents()");
			students=userRepository.getAllStudents();
			if(students!=null && students.size()>0) {
				studentBeans = new ArrayList<UserBean>();
				for (UserProfile student : students) {
					studentBean = new UserBean();
					BeanUtils.copyProperties(student, studentBean);
					studentBeans.add(studentBean);
				}
			}
			LOGGER.info("Data Successfully fetched using StudentServiceImpl.getAllStudents()");
			return studentBeans;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in StudentServiceImpl.getAllStudents(): "+e);
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
		List<Map<String,String>> studentLists = null;
		int passwordLength = 12;
		String generatedPassword = null;
		UserProfile studentProfile = null;
		UserProfile studentProfileReturn = null;
		List<UserBean> allStudentLists = null;
		UserBean studentReturnBean =null;
		HttpHeaders headers = null;
		String reqBodyData = null;
		HttpEntity<String> requestEntity = null;
		try {
			LOGGER.debug("Inside try block of StudentServiceImpl.importStudentDataExcel(-)");
			path = Files.createTempDirectory("");
			tempFile = path.resolve(file.getOriginalFilename()).toFile();
			file.transferTo(tempFile);
			workbook = WorkbookFactory.create(tempFile);
			sheet = workbook.getSheetAt(0);
			rowStreamSupplier = excelUploadUtil.getRowStreamSupplier(sheet);
			headerRow = rowStreamSupplier.get().findFirst().get();
			List<String> headerCells = excelUploadUtil.getStream(headerRow)
					.map(Cell::getStringCellValue)
					.collect(Collectors.toList());
			int colCount = headerCells.size();
			studentLists = rowStreamSupplier.get().skip(1).map(row->{
				excelUploadUtil.getStream(row).forEach(cell->{
					cell.setCellType(CellType.STRING);
				});
				List<String> cellList = excelUploadUtil.getStream(row)
						.map(Cell::getStringCellValue)
						.collect(Collectors.toList());
				return excelUploadUtil.cellIteratorSupplier(colCount).get().collect(toMap(headerCells::get,cellList::get));
			}).collect(Collectors.toList());
			//integrate with database to save all students are there in excel and sent them email using MicroServices
			allStudentLists = new ArrayList<UserBean>();
			for (Map<String,String> student : studentLists) {
				studentProfile = new UserProfile();
				studentProfile.setName(student.get("Name"));
				studentProfile.setEmail(student.get("Email"));
				generatedPassword = generateRandomPassword(passwordLength);
				studentProfile.setPassword(generatedPassword);
				studentProfileReturn = insert(studentProfile);
				studentReturnBean = new UserBean();
				BeanUtils.copyProperties(studentProfileReturn, studentReturnBean);
				//this password is not encrypted this is the original random password and encrypted password is stored in database
				studentReturnBean.setPassword(generatedPassword);
				allStudentLists.add(studentReturnBean);
			}
			headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			reqBodyData = new ObjectMapper().writeValueAsString(allStudentLists);
			requestEntity=new HttpEntity<String>(reqBodyData,headers);
			restTemplate.exchange(welcomeListUrl,HttpMethod.POST,requestEntity,String.class);
			LOGGER.info("Student data import Successfully and mail sent using StudentServiceImpl.importStudentDataExcel(-)");
			return allStudentLists;
		}
		catch (Exception e) {
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
		for (int i = 0; i < passwordLength/4; i++) {
			stringBuilder.append(chars1.charAt(random.nextInt(chars1.length())));
			stringBuilder.append(chars2.charAt(random.nextInt(chars2.length())));
			stringBuilder.append(chars3.charAt(random.nextInt(chars3.length())));
			stringBuilder.append(chars4.charAt(random.nextInt(chars4.length())));
		}
		return stringBuilder.toString();
	}

	@Override
	public UniversityJobShareBean studentJobResponse(UniversityJobShareBean jobBean) {
		UniversityJobShareBean jobShareBean = null;
		UniversityJobShare universityJobShare = null;
		try {
			LOGGER.debug("Inside UniversityServiceImpl.universityResponse(-)");
			Optional<UniversityJobShare> optional = universityJobShareRepository.findById(jobBean.getTableId());
			universityJobShare = new UniversityJobShare();
			universityJobShare = optional.get();
			if (universityJobShare != null) {
				jobShareBean = new UniversityJobShareBean();
				universityJobShare.setStudentResponseStatus(jobBean.getStudentResponseStatus());
				universityJobShare.setFeedBack(jobBean.getFeedBack());
				universityJobShare.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
				universityJobShare.setUpdatedBy("Biswa");
				
				universityJobShareRepository.save(universityJobShare);
				
				BeanUtils.copyProperties(universityJobShare, jobShareBean);
				jobShareBean.setResponse("SUCCESS");
			}
			LOGGER.info("Data Updated Successfully In UniversityServiceImpl.universityResponse(-)");
		} catch (Exception e) {
			jobShareBean.setResponse("FAILED");
			LOGGER.info("Data Updatation Failed In UniversityServiceImpl.universityResponse(-)" + e);
			throw e;
		}
		return jobShareBean;
	}
}
