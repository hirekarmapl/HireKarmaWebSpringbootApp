package com.hirekarma.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.hibernate.internal.build.AllowSysOut;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMaxValidatorForNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.CampusDriveInviteBean;
import com.hirekarma.beans.CampusDriveResponseBean;
import com.hirekarma.beans.JobApplyResponseBean;
import com.hirekarma.beans.StudentDetails;
import com.hirekarma.beans.StudentResponseBean;
import com.hirekarma.beans.UserBean;
import com.hirekarma.beans.UserBeanResponse;
import com.hirekarma.email.controller.EmailController;
import com.hirekarma.exception.CoporateUserDefindException;
import com.hirekarma.exception.StudentUserDefindException;
import com.hirekarma.model.CampusDriveResponse;
import com.hirekarma.model.ChatRoom;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.InternshipApply;
import com.hirekarma.model.Job;
import com.hirekarma.model.JobApply;
import com.hirekarma.model.Organization;
import com.hirekarma.model.Student;
import com.hirekarma.model.StudentBatch;
import com.hirekarma.model.University;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.CampusDriveResponseRepository;
import com.hirekarma.repository.ChatRoomRepository;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.InternshipApplyRepository;
import com.hirekarma.repository.JobApplyRepository;
import com.hirekarma.repository.JobRepository;
import com.hirekarma.repository.OrganizationRepository;
import com.hirekarma.repository.StudentBatchRepository;
import com.hirekarma.repository.StudentBranchRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.repository.UniversityRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.CoporateUserService;
import com.hirekarma.utilty.Validation;

@Service("coporateUserService")
public class CoporateUserServiceImpl implements CoporateUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CoporateUserServiceImpl.class);

	@Autowired
	private StudentBatchRepository studentBatchRepository;
	
	@Autowired
	private StudentBranchRepository studentBranchRepository;
	@Autowired
	private AWSS3Service awss3Service;
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private JobRepository jobRepository;

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
	
	@Autowired
	private JobApplyRepository jobApplyRepository;
	
	@Autowired
	private ChatRoomRepository chatRoomRepository;

	@Autowired
	private UniversityRepository universityRepository;
	
	@Autowired
	private InternshipApplyRepository internshipApplyRepository;

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
				
				//save Into Common Table
				
				userProfile.setUserType("corporate");
				userProfile.setStatus("Active");
				userProfile.setPassword(passwordEncoder.encode(userProfile.getPassword()));
			

				user = userRepository.save(userProfile);

				//save Into Corporate Table
				
				corporate.setCorporateEmail(LowerCaseEmail);
				corporate.setCorporateName(user.getName());
				corporate.setStatus(true);
				corporate.setUserProfile(user.getUserId());
				corporateRepository.save(corporate);
				
				//save Into Organization Table

				organization = new Organization();
				organization.setCorporateId(corporate.getCorporateId());
				organization.setStatus(true);

				organizationRepository.save(organization);

				body = new HashMap<String, String>();
				body.put("email", corporate.getCorporateEmail());
				body.put("name", corporate.getCorporateName());
				body.put("type", "corporate");
				emailController.welcomeAndOnBoardEmail(body);

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

	public UserProfile updateUserProfilefromUserNotNullForCorporate(UserBean bean,UserProfile userProfile) throws Exception {
		if(bean.getFile()!=null && !bean.getFile().isEmpty()) {
			userProfile.setImageUrl(awss3Service.uploadFile(bean.getFile()));
		}
		if(bean.getPhoneNo()!=null) {
			if(!Validation.isValidMobileNo(bean.getPhoneNo())) {
				throw new Exception("Enter proper phone number");
			}
			userProfile.setPhoneNo(bean.getPhoneNo());
		}
		if(bean.getAddress()!=null && !bean.getAddress().equals("")) {
			userProfile.setAddress(bean.getAddress());
		}
		if(bean.getName()!=null && !bean.getName().equals("")) {
			userProfile.setName(bean.getName());
		}
		if(bean.getAbout()!=null && !bean.getAbout().equals("")) {
			userProfile.setAbout(bean.getAbout());
		}
		return userProfile;
	}
	
	public Corporate updateCorporateFromUserProfileNotNull(UserProfile userProfile,Corporate corporate){
		corporate.setImageUrl(userProfile.getImageUrl());
		corporate.setCorporatePhoneNumber(userProfile.getPhoneNo());
		corporate.setCorporateAddress(userProfile.getAddress());
		corporate.setCorporateName(userProfile.getName());
		corporate.setProfileUpdationStatus(true);
		corporate.setAbout(userProfile.getAbout());
		return corporate;
	}
	
	boolean getCorporateProfileUpdateStatus(Corporate corporate) {
		if(corporate.getWebsiteUrl()==null||corporate.getWebsiteUrl().equals("")) {
			return false;
		}		if(corporate.getCorporateEmail()==null || corporate.getCorporateEmail().equals("")) {
			return false;
		}
		if(corporate.getAbout()==null ||corporate.getAbout().equals("") ) {
			return false;
		}
		if(corporate.getCorporateName()==null || corporate.getCorporateName().equals("")) {
			return false;
		}
		return true;
	}
	@Override
	public UserBean updateCoporateUserProfile(UserBean bean,String token)throws Exception {
		String email = Validation.validateToken(token);
		UserProfile userProfile= userRepository.findUserByEmail(email);
		if(!userProfile.getUserType().equals("corporate")) {
			throw new Exception("no such corporate exist");
		}
		userProfile = this.userRepository.save(updateUserProfilefromUserNotNullForCorporate(bean,userProfile));
		Corporate corporate = this.corporateRepository.findByEmail(email);
	
		if(bean.getWebsiteUrl()!=null && !bean.getWebsiteUrl().equals("")) {
			corporate.setWebsiteUrl(bean.getWebsiteUrl());
		}
		corporate.setProfileUpdationStatus(getCorporateProfileUpdateStatus(corporate));
		corporate  = this.corporateRepository.save(updateCorporateFromUserProfileNotNull(userProfile,corporate));
		
		UserBean userBean = new UserBean();
		BeanUtils.copyProperties(userProfile, userBean);
		
		userBean.setWebsiteUrl(corporate.getWebsiteUrl());
		userBean.setProfileUpdationStatus(corporate.getProfileUpdationStatus());
		return userBean;
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
					University university = universityRepository.getById(driveResponse.getUniversityId());
					if(university==null) {
						throw new CoporateUserDefindException("no such university found");
					}
					
//					check this part not working
					if (driveResponse.getCorporateResponse() && driveResponse.getUniversityAsk()) {
						System.out.print("checkning studentRepository.findApplyStudentDetail" );
						System.out.print(university.getUserId()+" "+driveResponse.getJobId());
						List<Object[]> list = studentRepository.findApplyStudentDetails(university.getUniversityId(),
								driveResponse.getJobId());
						System.out.println(list.size());
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
	@Override
	public Map<String, Object> shortListStudentForInternship(Long corporateId, Long internshipApplyId) {
		LOGGER.debug("Inside CoporateUserServiceImpl.shortListStudent(-,-)");
		Map<String, Object> map = null;
	
		Optional<InternshipApply> internshipApplyOptional = null;
		InternshipApply internshipApply = null;
		ChatRoom chatRoom = null;
		try {
			map = new HashMap<String,Object>();
			internshipApplyOptional = internshipApplyRepository.findById(internshipApplyId);
			if(!internshipApplyOptional.isEmpty()) {
				internshipApply = internshipApplyOptional.get();
				if(corporateId == internshipApply.getCorporateId()) {
					if(internshipApply.getApplicatinStatus()) {
						map.put("status", "Failed");
						map.put("responseCode", "400");
						map.put("message", "Student Already shortlisted");
						return map;
					}
					internshipApply.setApplicatinStatus(true);
					internshipApplyRepository.save(internshipApply);
					
					map.put("status", "Success");
					map.put("responseCode", "200");
					map.put("message", "Student got shortlisted");
					chatRoom = new ChatRoom();
					chatRoom.setJobApplyId(null);
					chatRoom.setInternshipApplyId(internshipApply.getInternshipApplyId());
					chatRoom.setCorporateId(corporateId);
					chatRoom.setStudentId(internshipApply.getStudentId());
					chatRoom = chatRoomRepository.save(chatRoom);
					map.put("chatRoom", chatRoom);
				}
				else {
					map = new HashMap<String,Object>();
					map.put("status", "Unauthorized");
					map.put("responseCode", "400");
					map.put("message", "Corporate mismatch");
				}
			}
			else {
				map = new HashMap<String,Object>();
				map.put("status", "Bad Request");
				map.put("responseCode", "400");
				map.put("message", "Internship Apply Id Not Found");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error in CoporateUserServiceImpl.shortListStudent(-)");
			map = new HashMap<String,Object>();
			map.put("status", "Failed");
			map.put("responseCode", "400");
			map.put("message", "Bad Request");
		}
		return map;
	}
	@Override
	public Map<String, Object> shortListStudent(Long corporateId, Long jobApplyId) {
		LOGGER.debug("Inside CoporateUserServiceImpl.shortListStudent(-,-)");
		Map<String, Object> map = null;
		Optional<JobApply> jobApplyOpt = null;
		JobApply jobApply = null;
		ChatRoom chatRoom = null;
		try {
			map = new HashMap<String,Object>();
			jobApplyOpt = jobApplyRepository.findById(jobApplyId);
			if(!jobApplyOpt.isEmpty()) {
				jobApply = jobApplyOpt.get();
				if(corporateId == jobApply.getCorporateId()) {
					if(jobApply.getApplicationStatus()) {
						map.put("status", "Failed");
						map.put("responseCode", "400");
						map.put("message", "Student Already shortlisted");
						return map;
					}
					jobApply.setApplicationStatus(true);
					jobApplyRepository.save(jobApply);
					
					map.put("status", "Success");
					map.put("responseCode", "200");
					map.put("message", "Student got shortlisted");
					chatRoom = new ChatRoom();
					chatRoom.setJobApplyId(jobApply.getJobApplyId());
					chatRoom.setCorporateId(corporateId);
					chatRoom.setStudentId(jobApply.getStudentId());
					chatRoom = chatRoomRepository.save(chatRoom);
					map.put("chatRoom", chatRoom);
				}
				else {
					map = new HashMap<String,Object>();
					map.put("status", "Unauthorized");
					map.put("responseCode", "400");
					map.put("message", "Corporate mismatch");
				}
			}
			else {
				map = new HashMap<String,Object>();
				map.put("status", "Bad Request");
				map.put("responseCode", "400");
				map.put("message", "Job Apply Id Not Found");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error in CoporateUserServiceImpl.shortListStudent(-)");
			map = new HashMap<String,Object>();
			map.put("status", "Failed");
			map.put("responseCode", "400");
			map.put("message", "Bad Request");
		}
		return map;
	}

	public List<JobApplyResponseBean> getAllJobsApplicationForCorporate(Long corporateId){
		List<JobApply> jobApplies = null;
		List<JobApplyResponseBean> jobApplyResponseBeans = new ArrayList<>();
		
			jobApplies = jobApplyRepository.getAllJobApplicationsByCorporate(corporateId);
			for(JobApply jobApply:jobApplies) {
				JobApplyResponseBean jobApplyResponseBean = new JobApplyResponseBean();
				BeanUtils.copyProperties(jobApply, jobApplyResponseBean);
				Student student = this.studentRepository.getById(jobApply.getStudentId());
				Job job = this.jobRepository.getById(jobApply.getJobId());
				StudentResponseBean studentResponseBean = new StudentResponseBean();
				BeanUtils.copyProperties(student, studentResponseBean);
				studentResponseBean.setStudentBatch(student.getBatch()!=null? this.studentBatchRepository.getById(student.getBatch()):null); 
				studentResponseBean.setStudentBranch(student.getBranch()!=null? this.studentBranchRepository.getById(student.getBranch()):null); 
				jobApplyResponseBean.setStudent(student);
				jobApplyResponseBean.setStudentResponseBean(studentResponseBean);				jobApplyResponseBean.setJob(job);
				jobApplyResponseBeans.add(jobApplyResponseBean);
			}
			return jobApplyResponseBeans;
	}

	@Override
	public Map<String, Object> getAllJobApplicationsByCorporate(Long corporateId) {
		LOGGER.debug("Inside CoporateUserServiceImpl.getAllJobApplicationsByStudent(-)");
		List<JobApply> jobApplies = null;
		Map<String,Object> map = null;
		List<JobApplyResponseBean> jobApplyResponseBeans = new ArrayList<>();
		try {
			jobApplies = jobApplyRepository.getAllJobApplicationsByCorporate(corporateId);
			for(JobApply jobApply:jobApplies) {
				JobApplyResponseBean jobApplyResponseBean = new JobApplyResponseBean();
				BeanUtils.copyProperties(jobApply, jobApplyResponseBean);
				Student student = this.studentRepository.getById(jobApply.getStudentId());
				Job job = this.jobRepository.getById(jobApply.getJobId());
				jobApplyResponseBean.setStudent(student);
				jobApplyResponseBean.setJob(job);
				jobApplyResponseBeans.add(jobApplyResponseBean);
			}
			if(jobApplies != null && jobApplies.size()>0) {
				map = new HashMap<String, Object>();
				map.put("status", "Failed");
				map.put("responseCode", 500);
				map.put("data", jobApplyResponseBeans);
				return map;
			}
			else {
				map = new HashMap<String, Object>();
				map.put("status", "Failed");
				map.put("responseCode", 500);
				map.put("message", "No student applied for your job!!!");
				return map;
			}
		}
		catch (Exception e) {
			LOGGER.info("Data getting Failed In CoporateUserServiceImpl.universityResponse(-)" + e);
			e.printStackTrace();
			map = new HashMap<String, Object>();
			map.put("status", "Failed");
			map.put("responseCode", 500);
			map.put("message", "Bad Request!!!");
			return map;
		}
	}

	@Override
	public Map<String, Object> getAllCampusDriveInvitesByCorporateId(Long corporateId) {
		LOGGER.debug("Inside CoporateUserServiceImpl.getAllCampusDriveInvitesByCorporateId(-)");
		List<CampusDriveInviteBean> campusDriveInviteBeans = null;
		CampusDriveInviteBean campusDriveInviteBean = null;
		Map<String,Object> map = null;
		List<CampusDriveResponse> campusDriveResponses = null;
		University university =null;
		try {
			campusDriveResponses = campusDriveResponseRepository.getAllCampusDriveResponseByCorporateId(corporateId);
			if(campusDriveResponses!=null && campusDriveResponses.size()>0) {
				campusDriveInviteBeans = new ArrayList<CampusDriveInviteBean>();
				for(CampusDriveResponse campusDriveResponse : campusDriveResponses) {
					university = campusDriveResponseRepository.getUniversityByUniversityId(campusDriveResponse.getUniversityId());
					
					if(university != null) {
						Job job = jobRepository.getById(campusDriveResponse.getJobId());
						campusDriveInviteBean = new CampusDriveInviteBean();
						campusDriveInviteBean.setCampusDriveId(campusDriveResponse.getCampusDriveId());
						campusDriveInviteBean.setCorporateId(campusDriveResponse.getCorporateId());
						campusDriveInviteBean.setJobId(campusDriveResponse.getJobId());
						campusDriveInviteBean.setUniversityId(university.getUniversityId());
						campusDriveInviteBean.setUniversityAsk(campusDriveResponse.getUniversityAsk());
						campusDriveInviteBean.setCorporateResponse(campusDriveResponse.getCorporateResponse());
						campusDriveInviteBean.setUniversityAskedOn(campusDriveResponse.getUniversityAskedOn());
						campusDriveInviteBean.setCorporateResponseOn(campusDriveResponse.getCorporateResponseOn());
						campusDriveInviteBean.setUserId(university.getUserId());
						campusDriveInviteBean.setUniversityName(university.getUniversityName());
						campusDriveInviteBean.setUniversityEmail(university.getUniversityEmail());
						campusDriveInviteBean.setUniversityAddress(university.getUniversityAddress());
						campusDriveInviteBean.setUniversityPhoneNumber(university.getUniversityPhoneNumber());
						campusDriveInviteBean.setUniversityImageUrl(university.getUniversityImageUrl());
						campusDriveInviteBean.setCreatedOn(university.getCreatedOn());
						campusDriveInviteBean.setCreatedOn(university.getCreatedOn());
						campusDriveInviteBean.setStatus(university.getStatus());
						campusDriveInviteBean.setJobName(job.getJobTitle());
						campusDriveInviteBeans.add(campusDriveInviteBean);
					}
				}
			}
			map = new HashMap<String, Object>();
			map.put("status", "Success");
			map.put("responseCode", 200);
			map.put("data", campusDriveInviteBeans);
			LOGGER.info("Data fetched Successfully In CoporateUserServiceImpl.getAllCampusDriveInvitesByCorporateId(-)");
			return map;
		}
		catch (Exception e) {
			LOGGER.info("Data getting Failed In CoporateUserServiceImpl.getAllCampusDriveInvitesByCorporateId(-)" + e);
			e.printStackTrace();
			map = new HashMap<String, Object>();
			map.put("status", "Failed");
			map.put("responseCode", 500);
			map.put("message", "Bad Request!!!");
			return map;
		}
	}

	@Override
	public Map<String, Object> getAllApplicantsForJobByJobId(Long jobId) {
		Job job = jobRepository.findByJobId(jobId);
		if(job==null) {
			throw new NoSuchElementException("invalid job id");
		}
		Map<String,Object> result = new HashMap<String, Object>();
		List<Map<String,Object>> jobApplyResponses = new ArrayList<>();
		List<Object[]> responseData = jobApplyRepository.findJobApplyAndStudentAndUserProfileAndChatRoomByJobId(jobId);
		for(Object[] o :  responseData) {
			Map<String,Object> jobApplyResponse = new HashMap<String, Object>();
//			BeanUtils.copyProperties((JobApply)o[0], jobApplyResponseBean);
			jobApplyResponse.put("jobApply", (JobApply)o[0]);
			Student s = (Student) o[1];
			UserProfile up = (UserProfile) o[2];
			Long chatRoomId = (Long) o[3];
			StudentResponseBean studentResponseBean = new StudentResponseBean();
			BeanUtils.copyProperties(s, studentResponseBean);
			studentResponseBean.setStudentBatch(s.getBatch()!=null? studentBatchRepository.getById(s.getBatch()):null);
			studentResponseBean.setStudentBranch(s.getBranch()!=null? studentBranchRepository.getById(s.getBranch()):null);
			studentResponseBean.setSkills(up.getSkills());
			studentResponseBean.setEducations(up.getEducations());
			jobApplyResponse.put("student", studentResponseBean);
			jobApplyResponse.put("chatRoomId", chatRoomId);
			jobApplyResponses.add(jobApplyResponse);
		}
		result.put("jobApplies", jobApplyResponses);
		result.put("job",job);
		
		return result;
	}

	@Override
	public Map<String, Object> shortListStudentForJobByJobApplyIdsdAndJobId(List<Long> jobApplyIds,Long jobId) throws Exception {
		Map<String,Object> result = new HashMap<>();
		
		Job job = jobRepository.getById(jobId);
		if(job==null) {
			throw new Exception("no such job found");
		}
//		get all the jobApply object
		List<JobApply> jobApplies = new ArrayList<>();
		List<ChatRoom> chatRooms = new ArrayList<>();
		for(Long id: jobApplyIds) {
			Optional<JobApply> optional = jobApplyRepository.findById(id);
			ChatRoom chatRoom = new ChatRoom();
			if(!optional.isPresent()) {
				throw new Exception("please check your inputs for jobApply ids");
			}
			JobApply jobApply = optional.get();
			if(job.getJobId()!=jobApply.getJobId()) {
				throw new Exception("invalid jobApply id");
			}
			if(!jobApply.getApplicationStatus()) {
			
				jobApply.setApplicationStatus(true);
				jobApplies.add(jobApply);
				
				chatRoom.setJobApplyId(jobApply.getJobApplyId());
				chatRoom.setCorporateId(jobApply.getCorporateId());
				chatRoom.setStudentId(jobApply.getStudentId());
				chatRooms.add(chatRoom);
			}
			
		}
		chatRooms =  chatRoomRepository.saveAll(chatRooms);
		jobApplies = jobApplyRepository.saveAll(jobApplies);
		result.put("chatRooms", chatRooms);
		return result;
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
