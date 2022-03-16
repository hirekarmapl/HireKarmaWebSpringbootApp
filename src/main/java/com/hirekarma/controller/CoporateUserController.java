package com.hirekarma.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.hirekarma.beans.BlogBean;
import com.hirekarma.beans.CampusDriveResponseBean;
import com.hirekarma.beans.GoogleCalenderRequest;
import com.hirekarma.beans.InternshipApplyResponseBean;
import com.hirekarma.beans.JobApplyBean;
import com.hirekarma.beans.OnlineAssessmentBean;
import com.hirekarma.beans.Response;
import com.hirekarma.beans.StudentDetails;
import com.hirekarma.beans.UserBean;
import com.hirekarma.exception.CoporateUserDefindException;
import com.hirekarma.model.Blog;
import com.hirekarma.model.CampusDriveResponse;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Job;
import com.hirekarma.model.Student;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.CampusDriveResponseRepository;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.JobRepository;
import com.hirekarma.repository.OnlineAssessmentRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.BlogService;
import com.hirekarma.service.CoporateUserService;
import com.hirekarma.service.InternshipApplyService;
import com.hirekarma.utilty.CalendarApi;
import com.hirekarma.utilty.JwtUtil;
import com.hirekarma.utilty.Validation;

@RestController("coporateUserController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class CoporateUserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CoporateUserController.class);

	@Autowired
	private CoporateUserService coporateUserService;
	
	@Autowired
	private OnlineAssessmentRepository onlineAssessmentRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private CorporateRepository corporateRepository;

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CampusDriveResponseRepository campusDriveResponseRepository;
	
	@Autowired
	private InternshipApplyService internshipApplyService;

	
	@PreAuthorize("hasRole('corporate')")
	@GetMapping("/corporate/blog/{slug}")
	public ResponseEntity<Response> getBlogByCorporateBySlug(@PathVariable("slug") String slug,
			@RequestHeader(value = "Authorization") String token) {
		try {
			Blog blog = this.blogService.getBlogByCoporateBySlug(slug, token);
			return new ResponseEntity<Response>(new Response("success", 201, "", blog, null), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('corporate')")
	@GetMapping("/corporate/getAllStudentsReadyForCampusDrive/{campusDriveId}")
	public ResponseEntity<Response> getAllStudentsReadyForCampusDriveByCampusDriveId(@PathVariable("campusDriveId") Long campusDriveId) {
		try {
			Optional<CampusDriveResponse>  optional = campusDriveResponseRepository.findById(campusDriveId);
			if(!optional.isPresent()) {
				throw new Exception("wrong campus drive id");
			}
			CampusDriveResponse campusDriveResponse = optional.get();
			
			List<Student> students = studentRepository.getAllStudentsReadyForCampusDriveByUniversiyAndJob(campusDriveResponse.getUniversityId(), campusDriveResponse.getJobId());
			Job job = jobRepository.getById(campusDriveResponse.getJobId());
			Map<Object,Object> map = new HashMap<Object, Object>();
			map.put("students", students);
			map.put("job", job);
			return new ResponseEntity<Response>(new Response("success", 200, "", map, null), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('corporate')")
	@RequestMapping(value = "/corporate/blog", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ResponseEntity<Response> addBlogByCooperate(@RequestPart("data") BlogBean bean,
			@RequestHeader(value = "Authorization") String token, @RequestPart("file") MultipartFile file) {
		try {
			Blog blog = this.blogService.addBlogByCooperate(bean, token, file);
			return new ResponseEntity<Response>(new Response("success", 201, "added succesfully", blog, null),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/corporate/blog/{slug}")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Response> deleteBlogBySlug(@RequestHeader(value = "Authorization") String token,
			@PathVariable("slug") String slug) {
		try {
			this.blogService.deleteBlogBySlug(token, slug);
			return new ResponseEntity<Response>(new Response("success", 201, "deleted succesfully", null, null),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('corporate')")
	@GetMapping("/corporate/blog/")
	public ResponseEntity<Response> getAllBlogsByCoporate(@RequestHeader(value = "Authorization") String token) {
		try {
			List<Blog> blogs = this.blogService.getAllBlogsByCoporate(token);
			return new ResponseEntity<Response>(new Response("success", 200, "", blogs, null), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	
	@PostMapping("/saveCorporateUrl")
	public ResponseEntity<Response> createUser(@RequestBody UserBean bean) {

		LOGGER.debug("Inside CoporateUserController.createUser(-)");

		UserProfile userProfile = null;
		UserProfile userProfileReturn = null;
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;

		try {
			LOGGER.debug("Inside try block of CoporateUserController.createUser(-)");

			// validating email
			if (Validation.validateEmail(bean.getEmail())) {

				// validating password
				if (!Validation.validatePassword(bean.getPassword())) {
					throw new CoporateUserDefindException(
							"Password must contain at least eight characters, at least one uppercase, one lowercase and one number:");
				}

				userProfile = new UserProfile();
				BeanUtils.copyProperties(bean, userProfile);

				userProfileReturn = coporateUserService.insert(userProfile);

				LOGGER.info("Data successfully saved using CoporateUserController.createUser(-)");
//					BeanUtils.copyProperties(userProfileReturn, userBean);
				userProfileReturn.setPassword(null);

				responseEntity = new ResponseEntity<>(response, HttpStatus.CREATED);

				response.setMessage("Data Saved Successfully...");
				response.setStatus("Success");
				response.setResponseCode(responseEntity.getStatusCodeValue());
				response.setData(userProfileReturn);
			} else {
				throw new CoporateUserDefindException("Please Enter A Valid Email Address !!");
			}

		} catch (Exception e) {
			LOGGER.error("Data saving failed in CoporateUserController.createUser(-): " + e);
			e.printStackTrace();

			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}
	
	

//	@PostMapping("/saveCoporateUrl")
//	public ResponseEntity<CoporateUserBean> createUser(@RequestBody CoporateUserBean coporateUserBean) {
//		LOGGER.debug("Inside CoporateUserController.createUser(-)");
//		CoporateUser coporateUser=null;
//		CoporateUser coporateUserReturn=null;
//		CoporateUserBean userBean=null;
//		try {
//			LOGGER.debug("Inside try block of CoporateUserController.createUser(-)");
//			coporateUser=new CoporateUser();
//			userBean=new CoporateUserBean();
//			BeanUtils.copyProperties(coporateUserBean, coporateUser);
//			coporateUserReturn=coporateUserService.insert(coporateUser);
//			LOGGER.info("Data successfully saved using CoporateUserController.createUser(-)");
//			BeanUtils.copyProperties(coporateUserReturn,userBean);
//			userBean.setPassword(null);
//			return new ResponseEntity<>(userBean,HttpStatus.CREATED);
//		}
//		catch (Exception e) {
//			LOGGER.error("Data saving failed in CoporateUserController.createUser(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

//	@PostMapping("/checkCopporateLoginCredentials")
//	public ResponseEntity<CoporateUserBean> checkLoginCredentials(@RequestBody LoginBean loginBean) {
//		LOGGER.debug("Inside CoporateUserController.checkLoginCredentials(-)");
//		CoporateUserBean coporateUserBean=null;
//		try {
//			LOGGER.debug("Inside try block of CoporateUserController.checkLoginCredentials(-)");
//			coporateUserBean=coporateUserService.checkLoginCredentials(loginBean.getEmail(), loginBean.getPassword());
//			if(coporateUserBean!=null) {
//				LOGGER.info("Credential matched in CoporateUserController.checkLoginCredentials(-)");
//				coporateUserBean.setPassword(null);
//				return new ResponseEntity<>(coporateUserBean,HttpStatus.ACCEPTED);
//			}
//			else {
//				LOGGER.info("Credential does not matched in CoporateUserController.checkLoginCredentials(-)");
//				return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
//			}
//		}
//		catch (Exception e) {
//			LOGGER.error("Some problem occured in CoporateUserController.checkLoginCredentials(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

//	@PutMapping(value = "/updateCoporateUserProfile")
//	public ResponseEntity<CoporateUserBean> updateCoporateUserProfile(@ModelAttribute CoporateUserBean coporateUserBean){
//		LOGGER.debug("Inside CoporateUserController.updateCoporateUserProfile(-)");
//		CoporateUserBean userBean=null;
//		byte[] image=null;
//		try {
//			LOGGER.debug("Inside try block of CoporateUserController.updateCoporateUserProfile(-)");
//			image=coporateUserBean.getFile().getBytes();
//			coporateUserBean.setProfileImage(image);
//			userBean=coporateUserService.updateCoporateUserProfile(coporateUserBean);
//			if(userBean!=null) {
//				LOGGER.info("Coporate details successfully updated in CoporateUserController.updateCoporateUserProfile(-)");
//				userBean.setPassword(null);
//				return new ResponseEntity<>(userBean,HttpStatus.OK);
//			}
//			else {
//				LOGGER.info("Coporate details not found in CoporateUserController.updateCoporateUserProfile(-)");
//				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
//			}
//		}
//		catch (IOException e) {
//			LOGGER.error("Problem occured during image to byte[] conversion in CoporateUserController.updateCoporateUserProfile(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		catch (Exception e) {
//			LOGGER.error("Some problem occured in CoporateUserController.updateCoporateUserProfile(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@PutMapping(value = "/updateCoporateUserProfile")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Response> updateCoporateUserProfile(@ModelAttribute UserBean bean,@RequestHeader("Authorization")String token) {
		try {
			UserBean userBean = this.coporateUserService.updateCoporateUserProfile(bean, token);
			return new ResponseEntity<Response>(
					new Response("success", HttpStatus.OK,"successfully update" , userBean, null),
					HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<Response>(
					new Response("Error", HttpStatus.BAD_REQUEST,e.getMessage() , null, null),
					HttpStatus.BAD_REQUEST);
		}
		
	}

	@GetMapping(value = "/findCorporateById/{corpUserId}")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Response> findCorporateById(@PathVariable Long corpUserId) {
		LOGGER.debug("Inside CoporateUserController.findCorporateById(-)");
		UserBean userBean = null;
		try {
			LOGGER.debug("Inside try block of CoporateUserController.findCorporateById(-)");
			userBean = coporateUserService.findCorporateById(corpUserId);
			if (userBean != null) {
				LOGGER.info("Corporate details get in CoporateUserController.findCorporateById(-)");
				userBean.setPassword(null);
				return new ResponseEntity<Response>(new Response("Success", HttpStatus.OK, "", userBean, null),
						HttpStatus.OK);
			} else {
				LOGGER.info("Coporate details not found in CoporateUserController.findCorporateById(-)");
				return new ResponseEntity<Response>(
						new Response("Error", HttpStatus.NOT_FOUND, "No such entity found", null, null),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOGGER.error("Some problem occured in CoporateUserController.findCorporateById(-): " + e);
			e.printStackTrace();
			return new ResponseEntity<Response>(
					new Response("Error", HttpStatus.INTERNAL_SERVER_ERROR, "something went wrong!", null, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/corporateCampusResponse")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<?> corporateCampusResponse(@RequestBody CampusDriveResponseBean campus) {

		LOGGER.debug("Inside CoporateUserController.corporateCampusResponse(-)");

		List<StudentDetails> StudentDetails = new ArrayList<StudentDetails>();
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();

		try {
			LOGGER.debug("Inside try block of CoporateUserController.corporateCampusResponse(-)");

			StudentDetails = coporateUserService.corporateCampusResponse(campus);

			LOGGER.info("Status Successfully Updated using CoporateUserController.corporateCampusResponse(-)");

			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

			response.setMessage("Request Accepted Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(StudentDetails);

		} catch (Exception e) {
			LOGGER.error("Status Updation failed in CoporateUserController.corporateCampusResponse(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}

	@GetMapping("/applyStudentDetails")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<?> applyStudentDetails(@RequestBody CampusDriveResponseBean campus,
			@RequestHeader(value = "Authorization") String token) {

		LOGGER.debug("Inside CoporateUserController.applyStudentDetails(-)");

		List<StudentDetails> StudentDetails = new ArrayList<StudentDetails>();
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();

		try {
			LOGGER.debug("Inside try block of CoporateUserController.applyStudentDetails(-)");

			StudentDetails = coporateUserService.applyStudentDetails(campus, token);

			LOGGER.info("Status Successfully Updated using CoporateUserController.applyStudentDetails(-)");

			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

			response.setMessage("Data Fetched Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(StudentDetails);

		} catch (Exception e) {
			LOGGER.error("Status Updation failed in CoporateUserController.applyStudentDetails(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}

	@GetMapping(value = "/corporateList")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<?> corporateList() {
		LOGGER.debug("Inside CoporateUserController.corporateList(-)");
		List<Corporate> corporate = new ArrayList<Corporate>();
		ResponseEntity<?> responseEntity = null;
		try {
			LOGGER.debug("Inside try block of CoporateUserController.corporateList(-)");
			corporate = coporateUserService.corporateList();
			responseEntity = new ResponseEntity<>(corporate, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Some problem occured in CoporateUserController.corporateList(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(corporate, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}


	@PostMapping("/calendar")
	@PreAuthorize("hasRole('corporate')")
	public Response createEvent(@RequestBody GoogleCalenderRequest request) {

		try {
			CalendarApi calendarQuickstart = new CalendarApi();
			System.out.println(request.toString());
			String op = calendarQuickstart.insert(request.getEventName(), request.getDescription(),
					request.getLocation(), request.getAttendees().size(), request.getAttendees(),
					request.getStartTime() + "+05:30", request.getEndTime() + "+05:30");
			return new Response("success", 200, op, request, null);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response("error", 400, "Bad Request", null, null);
	}
	
	@PatchMapping("/shortListStudent/{jobApplyId}")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Map<String,Object>> shortListStudent(@PathVariable("jobApplyId") Long jobApplyId, HttpServletRequest request) {		
		LOGGER.debug("Inside CoporateUserController.shortListStudent(-)");
		String jwtToken = null;
		String authorizationHeader = null;
		String email=null;
		Corporate corporate = null;
		Map<String, Object> map = null;
		try {
			authorizationHeader = request.getHeader("Authorization");
			jwtToken = authorizationHeader.substring(7);
			email = jwtTokenUtil.extractUsername(jwtToken);
			corporate = corporateRepository.findByEmail(email);
			map = coporateUserService.shortListStudent(corporate.getCorporateId(), jobApplyId);
		}
		catch (Exception e) {
			LOGGER.error("Error in CoporateUserController.shortListStudent(-)");
			map = new HashMap<String,Object>();
			map.put("status", "Failed");
			map.put("responseCode", "400");
			map.put("message", "Bad Request");
		}
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);

	}
	
	@PatchMapping("/job/shortlist_student")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Response> shortListStudentForJobByJobApplyIdsdAndJobId(@RequestHeader("Authorization")String token,@RequestBody JobApplyBean jobApplyBean)
		 {
		
		try {
			String email = Validation.validateToken(token);
			Corporate corporate = this.corporateRepository.findByEmail(email);
			Map<String,Object> result = this.coporateUserService.shortListStudentForJobByJobApplyIdsdAndJobId(jobApplyBean.getJobApplyIds(), jobApplyBean.getJobId());
			return new ResponseEntity<Response>(new Response("success", 200, "", result, null), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PatchMapping("/internship/shortListStudent/{internshipApplyId}")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Map<String,Object>> shortListStudentForInternship(@PathVariable("internshipApplyId") Long internshipApplyId, HttpServletRequest request) {		
		LOGGER.debug("Inside CoporateUserController.shortListStudent(-)");
		String jwtToken = null;
		String authorizationHeader = null;
		String email=null;
		Corporate corporate = null;
		Map<String, Object> map = null;
		try {
			authorizationHeader = request.getHeader("Authorization");
			jwtToken = authorizationHeader.substring(7);
			email = jwtTokenUtil.extractUsername(jwtToken);
			corporate = corporateRepository.findByEmail(email);
			map = coporateUserService.shortListStudentForInternship(corporate.getCorporateId(), internshipApplyId);
		}
		catch (Exception e) {
			LOGGER.error("Error in CoporateUserController.shortListStudent(-)");
			map = new HashMap<String,Object>();
			map.put("status", "Failed");
			map.put("responseCode", "400");
			map.put("message", "Bad Request");
		}
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);

	}
	
	@GetMapping("/getAllJobApplicationsByCorporate")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Map<String,Object>> getAllJobApplicationsByCorporate(HttpServletRequest request) {
		LOGGER.debug("Inside CoporateUserController.getAllJobApplicationsByCorporate(-)");
		Map<String, Object> map = null;
		ResponseEntity<Map<String, Object>> responseEntity = null;
		String jwtToken = null;
		String authorizationHeader = null;
		String email=null;
		UserProfile userProfile = null;
		Corporate corporate = null;
		try {
			authorizationHeader = request.getHeader("Authorization");
			jwtToken = authorizationHeader.substring(7);
			email = jwtTokenUtil.extractUsername(jwtToken);
			userProfile = userRepository.findUserByEmail(email);
			corporate = corporateRepository.findByEmail(email);
			map = coporateUserService.getAllJobApplicationsByCorporate(corporate.getCorporateId());
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			LOGGER.info("Getting applications success CoporateUserController.getAllJobApplicationsByCorporate(-)");
			return responseEntity;
		}
		catch (Exception e) {
			LOGGER.error("Getting applications failed CoporateUserController.getAllJobApplicationsByCorporate(-): " + e);
			map = new HashMap<String, Object>();
			map.put("status", "Bad Request");
			map.put("responseCode", 400);
			map.put("message", "getting applications failed!!!");
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			e.printStackTrace();
			return responseEntity;
		}
	}
	
	@GetMapping("/getAllCampusDriveInvitesByCorporate")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Map<String,Object>> getAllCampusDriveInvitesByCorporate(HttpServletRequest request) {
		LOGGER.debug("Inside CoporateUserController.getAllCampusDriveInvitesByCorporate(-)");
		Map<String, Object> map = null;
		ResponseEntity<Map<String, Object>> responseEntity = null;
		String jwtToken = null;
		String authorizationHeader = null;
		String email=null;
		UserProfile userProfile = null;
		Long corporateId = null;
		try {
			authorizationHeader = request.getHeader("Authorization");
			jwtToken = authorizationHeader.substring(7);
			email = jwtTokenUtil.extractUsername(jwtToken);
			userProfile = userRepository.findUserByEmail(email);
			Corporate corporate = corporateRepository.findByEmail(email);
			corporateId = corporate.getCorporateId();
			map = coporateUserService.getAllCampusDriveInvitesByCorporateId(corporateId);
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			LOGGER.info("Getting applications success CoporateUserController.getAllCampusDriveInvitesByCorporate(-)");
			return responseEntity;
		}
		catch (Exception e) {
			LOGGER.error("Getting applications failed CoporateUserController.getAllCampusDriveInvitesByCorporate(-): " + e);
			map = new HashMap<String, Object>();
			map.put("status", "Bad Request");
			map.put("responseCode", 400);
			map.put("message", "getting invitations failed!!!");
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			e.printStackTrace();
			return responseEntity;
		}
	}
	@GetMapping("/corporate/all_applications/{jobId}")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Response> getAllAplicationByJobApplyId(@RequestHeader("Authorization")String token,@PathVariable("jobId")Long jobId)
	{	
		try {
			String email = Validation.validateToken(token);
			Corporate corporate = this.corporateRepository.findByEmail(email);
			Map<String,Object> result = this.coporateUserService.getAllApplicantsForJobByJobId(jobId);
			return new ResponseEntity<Response>(new Response("success", 200, "", result, null), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/corporate/all_applications")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Response> getAllInternshipApplicationForSpecificCorporate(@RequestHeader("Authorization")String token)
			throws Exception {
		
		try {
			String email = Validation.validateToken(token);
			Corporate corporate = this.corporateRepository.findByEmail(email);
			Map<String,Object> result = new HashMap<>();
			result.put("jobs",coporateUserService.getAllJobsApplicationForCorporate(corporate.getCorporateId()));
			result.put("internships",this.internshipApplyService.getAllInternshipApplicationForSpecificCorporate(corporate.getCorporateId()));
			return new ResponseEntity<Response>(new Response("success", 200, "", result, null), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
}
