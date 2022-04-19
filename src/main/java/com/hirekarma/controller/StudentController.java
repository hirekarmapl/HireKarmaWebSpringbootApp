package com.hirekarma.controller;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.EducationBean;
import com.hirekarma.beans.NoticeBean;
import com.hirekarma.beans.Response;
import com.hirekarma.beans.StudentMentorBooking;
import com.hirekarma.beans.UniversityJobShareToStudentBean;
import com.hirekarma.beans.UserBean;
import com.hirekarma.beans.UserBeanResponse;
import com.hirekarma.exception.StudentUserDefindException;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Education;
import com.hirekarma.model.Experience;
import com.hirekarma.model.Mentor;
import com.hirekarma.model.Skill;
import com.hirekarma.model.Student;
import com.hirekarma.model.University;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.MentorRepository;
import com.hirekarma.repository.NoticeRepository;
import com.hirekarma.repository.SkillRespository;
import com.hirekarma.repository.StudentOnlineAssessmentAnswerRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.StudentOnlineAssessmentService;
import com.hirekarma.service.StudentService;
import com.hirekarma.utilty.JwtUtil;
import com.hirekarma.utilty.Validation;

@RestController("studentController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class StudentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	private NoticeRepository noticeRepository;

	@Autowired
	private StudentService studentService;

	@Autowired
	private SkillRespository skillRespository;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentOnlineAssessmentService studentOnlineAssessmentService;


	@Autowired
	private MentorRepository mentorRepository;
	
	@PreAuthorize("hasRole('student')")
	@GetMapping("student/notices")
	public ResponseEntity<Response> getAllNotice(@RequestHeader("Authorization") String token) {
		try {
			String email = Validation.validateToken(token);
			return new ResponseEntity<Response>(new Response("success", 200, "", this.noticeRepository.getAllNoticeForStudent(email), null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error",400, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/saveStudentUrl")
	public ResponseEntity<Response> createUser2(@RequestBody Map<String, String> studentBean) {
		LOGGER.debug("Inside StudentController.createUser(-)");

		try {
			LOGGER.debug("Inside try block of StudentController.createUser(-)");

			if (!Validation.validateEmail(studentBean.get("email"))) {
				throw new StudentUserDefindException("Please Enter A Valid Email !!");
			} 
			
			if(!Validation.validatePassword(studentBean.get("password"))) {
				throw new Exception("password should be minimum 8 length and should contain one Uppercase, one Lowercase, and one numeric!");
			}

			UserProfile responseUserProfile =  studentService.insert2(studentBean.get("email"),studentBean.get("password"),studentBean.get("name"));
				return new ResponseEntity<Response>(new Response("success", 201, "added succesfully", responseUserProfile, null),
						HttpStatus.CREATED);
		
			} catch (Exception e) {
				return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
						HttpStatus.BAD_REQUEST);
				
			}
	}

	@GetMapping("/student/mentors")
	public ResponseEntity<Response> getAllMentors(@RequestHeader("Authorization") String token) {
		try {	
			Student student = this.studentRepository.findByStudentEmail(Validation.validateToken(token));
			if(!student.isPremimum()) {
				throw new Exception("unauthorized");
			}
			List<Mentor> mentors = this.mentorRepository.findByAvailableTrue();
			return new ResponseEntity(
					new Response("success", 200, "",mentors, null),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", 500, e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/student/mentor/book-session")
	public ResponseEntity<Response> studentBookASlot(@RequestBody StudentMentorBooking studentMentorBooking,@RequestHeader("Authorization") String token){
		try {

			Student student = this.studentRepository.findByStudentEmail(Validation.validateToken(token));
			if(student==null || !student.isPremimum()) {
				throw new Exception("unauthorized");
			}
			
			return new ResponseEntity(
					new Response("success", 200, "",studentService.bookAMentorSlot(studentMentorBooking, student), null),
					HttpStatus.OK);
		} catch (DateTimeParseException dtpe) {
			return new ResponseEntity(new Response("error", 422, "invalid input", null, null),
					HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", 500, e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/student/mentor/available-session")
	public ResponseEntity<Response> getMentorAvailableSessions(@RequestBody StudentMentorBooking studentMentorBooking,@RequestHeader("Authorization") String token){
		try {

			Student student = this.studentRepository.findByStudentEmail(Validation.validateToken(token));
			if(student==null || !student.isPremimum()) {
				throw new Exception("unauthorized");
			}
			
			return new ResponseEntity(
					new Response("success", 200, "",studentService.getMentorAvailableSession(studentMentorBooking, student), null),
					HttpStatus.OK);
		} catch (DateTimeParseException dtpe) {
			return new ResponseEntity(new Response("error", 422, "invalid input", null, null),
					HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", 500, e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PutMapping(value = "/updateStudentProfile")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<Response> updateStudentProfile(@ModelAttribute UserBean studentBean,
			@RequestHeader(value = "Authorization") String token) {
		LOGGER.debug("Inside StudentController.updateStudentProfile(-)");
		UserBeanResponse bean = null;
		byte[] image = null;
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		try {
			LOGGER.debug("Inside try block of StudentController.updateStudentProfile(-)");
			if(studentBean.getEmail()!=null && !Validation.validateEmail(studentBean.getEmail())) {
				throw new Exception("Please enter a valid email");
			}
			if(studentBean.getPhoneNo()!=null && !Validation.phoneNumberValidation(Long.valueOf(studentBean.getPhoneNo())))
			{
				throw new Exception("Please enter valid phone number");
			}
			bean = studentService.updateStudentProfile2(studentBean, token);
			
			if (bean != null) {
				LOGGER.info(
						"Coporate details successfully updated in StudentController.updateStudentProfile(-)");
				bean.setPassword(null);

				responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

				response.setStatus("Success");
				response.setMessage("Data Updated Successfully...");
			} else {
				LOGGER.info("Coporate details not found in StudentController.updateStudentProfile(-)");

				responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

				response.setStatus("Failed");
				response.setMessage("Details Not Found !!");
			}

			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(bean);
			
		} catch (IOException e) {
			LOGGER.error(
					"Problem occured during image to byte[] conversion in StudentController.updateStudentProfile(-): "
							+ e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());

		} catch (Exception e) {
			LOGGER.error("Some problem occured in StudentController.updateStudentProfile(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}

	@GetMapping(value = "/findStudentById/{studentId}")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<UserBean> findStudentById(@PathVariable Long studentId) {
		LOGGER.debug("Inside StudentController.findStudentById(-)");
		UserBean bean = null;
		try {
			LOGGER.debug("Inside try block of StudentController.findStudentById(-)");
			bean = studentService.findStudentById(studentId);
			if (bean != null) {
				LOGGER.info("Corporate details get in StudentController.findStudentById(-)");
				bean.setPassword(null);
				return new ResponseEntity<>(bean, HttpStatus.OK);
			} else {
				LOGGER.info("Coporate details not found in StudentController.findStudentById(-)");
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOGGER.error("Some problem occured in StudentController.findStudentById(-): " + e);
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/studentJobResponse")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<Response> studentJobResponse(@RequestBody UniversityJobShareToStudentBean jobBean,
			@RequestHeader("Authorization") String token) {
		LOGGER.debug("Inside StudentController.studentJobResponse(-)");
		UniversityJobShareToStudentBean universityJobShareToStudentBean = new UniversityJobShareToStudentBean();
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of StudentController.studentJobResponse(-)");
			universityJobShareToStudentBean = studentService.studentJobResponse(jobBean, token);
			LOGGER.info("Response Successfully Updated using UniversityController.studentJobResponse(-)");

			responseEntity = new ResponseEntity<>(response, HttpStatus.ACCEPTED);

			response.setMessage("your response has been added");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(universityJobShareToStudentBean);

		} catch (Exception e) {
			LOGGER.error("Response Updation failed in StudentController.studentJobResponse(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}

	@RequestMapping("/jobDetails")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<Response> jobDetails(@RequestHeader(value = "Authorization") String token) {
		LOGGER.debug("Inside studentController.jobDetails(-)");
		List<?> listData = null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of studentController.jobDetails(-)");
			listData = studentService.jobDetails(token);
			LOGGER.info("Response Successfully Updated using studentController.jobDetails(-)");

			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

			response.setMessage("Data Fetched Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(listData);

		} 
		catch(NoSuchElementException ne) {
			responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			response.setMessage(ne.getMessage());
			response.setStatus("error");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		catch (Exception e) {
			LOGGER.error("Response Updation failed in studentController.jobDetails(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}

	

	@PostMapping("/student/skill")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<?> addSkillToAStudent(@RequestHeader(value = "Authorization") String token,
			@RequestBody Skill skill) {
		try {
			Map<String,Object> answer = this.studentService.addSkillToAStudent(skill, token);
			return new ResponseEntity<Response>(new Response("success", 200, "added succesfully", answer, null),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}
	
	@DeleteMapping("/student/skill/{id}")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<?> deleteSkillOfAStudent(@RequestHeader(value = "Authorization") String token,
			@PathVariable("id")int id) {
		try {
			
			Map<String,Object> answer = this.studentService.deleteSkillOfAStudent(id, token);
			return new ResponseEntity<Response>(new Response("success", 200, "deleted succesfully", answer, null),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/student/experiences")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<?> addAllExperienceToStudent(@RequestHeader(value = "Authorization") String token,
			@RequestBody Map<String, List<Experience>> experience) {
		try {
			List<Experience> experiences = this.studentService.addAllExperienceToStudent(experience.get("experiences"), token);
			return new ResponseEntity<Response>(new Response("success", 200, "added succesfully", experiences, null),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/student/experience")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<?> addExperienceToAStudent(@RequestHeader(value = "Authorization") String token,
			@RequestBody Experience experience) {
		try {
			experience = this.studentService.addExperienceToAStudent(experience, token);
			return new ResponseEntity<Response>(new Response("success", 200, "added succesfully", experience, null),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/student/educations")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<Response> getAllEducationsOfStudent(@RequestHeader(value = "Authorization") String token) {
		try {
			List<Education> educations = this.studentService.getAllEducationsOfStudent(token);
			return new ResponseEntity<Response>(new Response("success", 200, "added succesfully", educations, null),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/student/education/{educationId}")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<Response> deleteEducationOfAStudentbyId(@RequestHeader(value = "Authorization") String token,@PathVariable("educationId")int educationId) {
		try {
			
			this.studentService.deleteEducationOfAStudentbyId(token, educationId);
			return new ResponseEntity<Response>(new Response("success", 200, "deleted succesfully",null , null),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	


	@PostMapping("/student/education")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<?> addAEducationToStudent(@RequestHeader(value = "Authorization") String token,
			@RequestBody EducationBean education) {
		try {
			Education educationsSaved = this.studentService.addEducationToAStudent(education, token);
			return new ResponseEntity<Response>(
					new Response("success", 200, "added succesfully", educationsSaved, null), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}
	@PostMapping("/student/educations")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<?> addAEducationToStudent(@RequestHeader(value = "Authorization") String token,
			@RequestBody Map<String,List<EducationBean>> request) {
		try {
			List<Education> educationsSaved = this.studentService.addAllEducationToStudent(request.get("educations"), token);
			return new ResponseEntity<Response>(
					new Response("success", 200, "added succesfully", educationsSaved, null), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/student/experience")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<Response> getAllExperienceOfStudent(@RequestHeader(value = "Authorization") String token) {
		try {
			List<Experience> experiences = this.studentService.getAllExperiencesOfStudent(token);
			return new ResponseEntity<Response>(new Response("success", 200, "", experiences, null),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/student/experience/{id}")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<?> deleteExperienceOfAStudent(@RequestHeader(value = "Authorization") String token,
			@PathVariable("id")int id) {
		try {
			
			 this.studentService.deleteExperienceOfAStudent(id, token);
			return new ResponseEntity<Response>(new Response("success", 200, "deleted succesfully", null, null),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}
	@PostMapping("/student/skills")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<?> addAllSkillsToStudent(@RequestHeader(value = "Authorization") String token,
			@RequestBody Map<String, List<Skill>> request) {
		try {
			Map<String,Object> response = this.studentService.addAllSkillsToStudent(request.get("skills"), token);
			return new ResponseEntity<Response>(new Response("success", 200, "updated succesfully", response.get("skills"), null),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	
	@GetMapping("/student/getSkills")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<Response> getAllSkillsOfStudent(@RequestHeader(value = "Authorization") String token)
			throws Exception {
		try {
			List<Skill> skills = this.studentService.getAllSkillsOfStudent(token);
			return new ResponseEntity<Response>(new Response("success", 200, "", skills, null), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getAllJobApplicationsByStudent")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<Map<String, Object>> getAllJobApplicationsByStudent(HttpServletRequest request) {
		LOGGER.debug("Inside StudentController.getAllJobApplicationsByStudent(-)");
		Map<String, Object> map = null;
		ResponseEntity<Map<String, Object>> responseEntity = null;
		String jwtToken = null;
		String authorizationHeader = null;
		String email = null;
		UserProfile userProfile = null;
		Long studentId = null;
		try {
			authorizationHeader = request.getHeader("Authorization");
			jwtToken = authorizationHeader.substring(7);
			email = jwtTokenUtil.extractUsername(jwtToken);
			userProfile = userRepository.findUserByEmail(email);
			studentId = userProfile.getUserId();
			Student student = studentRepository.findByStudentEmail(email);
			map = studentService.getAllJobApplicationsByStudent(student.getStudentId());
			responseEntity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			LOGGER.error("Getting applications success StudentController.getAllJobApplicationsByStudent(-)");
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error("Getting applications failed StudentController.getAllJobApplicationsByStudent(-): " + e);
			map = new HashMap<String, Object>();
			map.put("status", "Bad Request");
			map.put("responseCode", 400);
			map.put("message", "getting applications failed!!!");
			responseEntity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			e.printStackTrace();
			return responseEntity;
		}
	}

	
	
//	@PostMapping("/checkStudentLoginCredentials")
//	public ResponseEntity<StudentBean> checkLoginCredentials(@RequestBody LoginBean loginBean) {
//		LOGGER.debug("Inside StudentController.checkLoginCredentials(-)");
//		StudentBean studentBean=null;
//		try {
//			LOGGER.debug("Inside try block of StudentController.checkLoginCredentials(-)");
//			studentBean=studentService.checkLoginCredentials(loginBean.getEmail(), loginBean.getPassword());
//			if(studentBean!=null) {
//				LOGGER.info("Credential matched in StudentController.checkLoginCredentials(-)");
//				return new ResponseEntity<>(studentBean,HttpStatus.ACCEPTED);
//			}
//			else {
//				LOGGER.info("Credential does not matched in StudentController.checkLoginCredentials(-)");
//				return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
//			}
//		}
//		catch (Exception e) {
//			LOGGER.error("Some problem occured in StudentController.checkLoginCredentials(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

//	@PutMapping(value = "/updateStudentProfile")
//	public ResponseEntity<StudentBean> updateStudentProfile(@ModelAttribute StudentBean studentBean){
//		LOGGER.debug("Inside StudentController.updateStudentProfile(-)");
//		StudentBean bean=null;
//		byte[] image=null;
//		try {
//			LOGGER.debug("Inside try block of StudentController.updateStudentProfile(-)");
//			image=studentBean.getFile().getBytes();
//			studentBean.setProfileImage(image);
//			bean=studentService.updateStudentProfile(studentBean);
//			if(bean!=null) {
//				LOGGER.info("Coporate details successfully updated in StudentController.updateStudentProfile(-)");
//				bean.setPassword(null);
//				return new ResponseEntity<>(bean,HttpStatus.OK);
//			}
//			else {
//				LOGGER.info("Coporate details not found in StudentController.updateStudentProfile(-)");
//				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
//			}
//		}
//		catch (IOException e) {
//			LOGGER.error("Problem occured during image to byte[] conversion in StudentController.updateStudentProfile(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		catch (Exception e) {
//			LOGGER.error("Some problem occured in StudentController.updateStudentProfile(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//	@GetMapping(value = "/findStudentById/{studentId}")
//	public ResponseEntity<StudentBean> findStudentById(@PathVariable Long studentId){
//		LOGGER.debug("Inside StudentController.findStudentById(-)");
//		StudentBean bean=null;
//		try {
//			LOGGER.debug("Inside try block of StudentController.findStudentById(-)");
//			bean=studentService.findStudentById(studentId);
//			if(bean!=null) {
//				LOGGER.info("Corporate details get in StudentController.findStudentById(-)");
//				bean.setPassword(null);
//				return new ResponseEntity<>(bean,HttpStatus.OK);
//			}
//			else {
//				LOGGER.info("Coporate details not found in StudentController.findStudentById(-)");
//				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
//			}
//		}
//		catch (Exception e) {
//			LOGGER.error("Some problem occured in StudentController.findStudentById(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

}
