package com.hirekarma.serviceimpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.AdminShareJobToUniversityBean;
import com.hirekarma.beans.AdminSharedJobList;
import com.hirekarma.beans.CampusDriveResponseBean;
import com.hirekarma.beans.NoticeBean;
import com.hirekarma.beans.StudentResponseBean;
import com.hirekarma.beans.StudentResponseToUniversity;
import com.hirekarma.beans.UniversityJobShareToStudentBean;
import com.hirekarma.beans.UniversityShareJobToStudentResponse;
import com.hirekarma.email.controller.EmailController;
import com.hirekarma.exception.CampusDriveResponseException;
import com.hirekarma.exception.UniversityException;
import com.hirekarma.exception.UserProfileException;
import com.hirekarma.model.AdminShareJobToUniversity;
import com.hirekarma.model.CampusDriveResponse;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Job;
import com.hirekarma.model.Notice;
import com.hirekarma.model.Stream;
import com.hirekarma.model.Student;
import com.hirekarma.model.StudentBatch;
import com.hirekarma.model.StudentBranch;
import com.hirekarma.model.University;
import com.hirekarma.model.UniversityJobShareToStudent;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.AdminShareJobToUniversityRepository;
import com.hirekarma.repository.CampusDriveResponseRepository;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.JobRepository;
import com.hirekarma.repository.NoticeRepository;
import com.hirekarma.repository.ShareJobRepository;
import com.hirekarma.repository.StreamRepository;
import com.hirekarma.repository.StudentBatchRepository;
import com.hirekarma.repository.StudentBranchRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.repository.UniversityJobShareRepository;
import com.hirekarma.repository.UniversityRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.StudentService;
import com.hirekarma.service.UniversityService;
import com.hirekarma.utilty.Validation;

@Service("universityServiceImpl")
public class UniversityServiceImpl implements UniversityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UniversityServiceImpl.class);

	@Autowired
	AWSS3Service awss3Service;
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	@Autowired
	private StudentBatchRepository studentBatchRepository;
	
	@Autowired
	private StudentBranchRepository studentBranchRepository;
	@Autowired
	private ShareJobRepository shareJobRepository;
	
	@Autowired
	private StudentService studentService;

	@PersistenceContext
	  private EntityManager em;


	@Autowired
	private StreamRepository streamRepository;
	@Autowired
	private EmailController emailController;
	
	@Autowired
	private CorporateRepository corporateRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private UniversityRepository universityRepository;

	@Autowired
	private AdminShareJobToUniversityRepository adminShareJobToUniversityRepository;

	@Autowired
	private UniversityJobShareRepository universityJobShareRepository;

	@Autowired
	private CampusDriveResponseRepository campusDriveResponseRepository;

	@Override
	public Map<String, Object> universityResponse(AdminShareJobToUniversityBean jobBean) {

		AdminShareJobToUniversity adminShareJobToUniversity = new AdminShareJobToUniversity();
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			LOGGER.debug("Inside UniversityServiceImpl.universityResponse(-)");

			if (!jobBean.getUniversityResponseStatus() && (jobBean.getRejectionFeedback() == null
					&& jobBean.getRejectionFeedback().equalsIgnoreCase("null")
					&& jobBean.getRejectionFeedback().equals(""))) {
				throw new UserProfileException("Rejected Reason Can't Be Blank !!");
			} else {

				Optional<AdminShareJobToUniversity> optional = shareJobRepository.findById(jobBean.getShareJobId());
				adminShareJobToUniversity = optional.get();

				if (adminShareJobToUniversity != null) {
					if (jobBean.getUniversityResponseStatus() != null) {
						adminShareJobToUniversity.setUniversityResponseStatus(jobBean.getUniversityResponseStatus());
						adminShareJobToUniversity.setRejectionFeedback(jobBean.getRejectionFeedback());
						adminShareJobToUniversity.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));

						shareJobRepository.save(adminShareJobToUniversity);
					} else {
						throw new UniversityException("Response Value Can't Be Null !!");
					}

				}
			}

			response.put("result", adminShareJobToUniversity);
			LOGGER.info("Data Updated Successfully In UniversityServiceImpl.universityResponse(-)");

		} catch (Exception e) {

			LOGGER.info("Data Updatation Failed In UniversityServiceImpl.universityResponse(-)" + e);
			throw e;
		}

		return response;
	}

	@Override
	public Map<String, Object> shareJobStudent(UniversityJobShareToStudentBean universityJobShareToStudentBean)
			throws Exception {

//		getting admin and setting details
		AdminShareJobToUniversity adminShareJobToUniversity = adminShareJobToUniversityRepository.getById(universityJobShareToStudentBean.getShareJobId());
		if(adminShareJobToUniversity==null) {
			throw new UserProfileException("invalid shareJobid");
		}
		
		universityJobShareToStudentBean.setJobId(adminShareJobToUniversity.getJobId());
		universityJobShareToStudentBean.setUniversityId(adminShareJobToUniversity.getUniversityId());
		
		UniversityJobShareToStudent universityJobShareToStudent = null;
	
//		checking if data already exist
		List<UniversityJobShareToStudent> universityJobShareToStudents = this.universityJobShareRepository.findByJobIdAndUniversityId(universityJobShareToStudentBean.getJobId(), universityJobShareToStudentBean.getUniversityId());
		if(universityJobShareToStudents!=null && universityJobShareToStudents.size()!=0) {
			throw new Exception("Job Already Shared");
		}
		
		List<Stream> streams = new ArrayList<Stream>();
		try {
			streams = this.streamRepository.findAllById(universityJobShareToStudentBean.getStreamIds());
			System.out.println(streams);
		}
		catch(Exception e) {
			throw new Exception("check your streamids properly");
		}
		if(streams.size()!=universityJobShareToStudentBean.getStreamIds().size() ){
			throw new Exception("check your streamids properly");
		}
		
		
//		checking if univeristy and university in AdminShareJOb matches
		String email =  Validation.validateToken(universityJobShareToStudentBean.getToken());
		University university = universityRepository.findByEmail(email);
		System.out.println(adminShareJobToUniversity.getUniversityId()+" "+university.getUniversityId());
		System.out.println();
		if(!adminShareJobToUniversity.getUniversityId().equals(university.getUniversityId())) {
			throw new Exception("job not shared to unviersity");
		}
		
		
//		output variable
		List<UniversityJobShareToStudent> list = new ArrayList<UniversityJobShareToStudent>();
		Long count = 0L;
		Map<String, Object> response = new HashMap<String, Object>();
		System.out.println("before filetered students");
		List<Student> filteredStudents = new ArrayList<>() ;
		if(universityJobShareToStudentBean.getStreamIds()!=null && universityJobShareToStudentBean.getStreamIds().size()!=0) {
			System.out.println("inside streams"); 
			for(Stream s:streams) {
				 System.out.println("stream id"+s.getId());
//				 for mba
				if(s.getId()==281) {
					 filteredStudents.addAll(studentService.getAllStudentsAccoridngToStreamBranchBatchCgpaFilter(s,null, universityJobShareToStudentBean.getBatchId(), universityJobShareToStudentBean.getCgpaId(), universityJobShareToStudentBean.getUniversityId()));
				}
//				 for mca
				else if(s.getId()==273) {
					 filteredStudents.addAll(studentService.getAllStudentsAccoridngToStreamBranchBatchCgpaFilter(s,null, universityJobShareToStudentBean.getBatchId(), universityJobShareToStudentBean.getCgpaId(), universityJobShareToStudentBean.getUniversityId()));
				}
				else {
					 filteredStudents.addAll(studentService.getAllStudentsAccoridngToStreamBranchBatchCgpaFilter(s,universityJobShareToStudentBean.getBranchId(), universityJobShareToStudentBean.getBatchId(), universityJobShareToStudentBean.getCgpaId(), universityJobShareToStudentBean.getUniversityId()));
					
				 }
				 System.out.println(filteredStudents.size());
			 }
		}
		else {
			filteredStudents = studentService.getAllStudentsAccoridngToBranchBatchCgpaFilter(universityJobShareToStudentBean.getBranchId(), universityJobShareToStudentBean.getBatchId(), universityJobShareToStudentBean.getCgpaId(), universityJobShareToStudentBean.getUniversityId());
		}
		
		if(filteredStudents.size()==0) {
			throw new Exception("no such student found");
		}
		
		Job job = this.jobRepository.getById(universityJobShareToStudentBean.getJobId());
		Corporate corporate = this.corporateRepository.getById(job.getCorporateId());
		
		for (int i = 0; i < filteredStudents.size(); i++) {
			count++;
			universityJobShareToStudent = new UniversityJobShareToStudent();
			universityJobShareToStudent.setJobId(universityJobShareToStudentBean.getJobId());
			
			universityJobShareToStudent.setUniversityId(university.getUniversityId());
			universityJobShareToStudent.setJobStatus(true);
			universityJobShareToStudent.setStudentId(filteredStudents.get(i).getStudentId());
			universityJobShareToStudent.setCreatedBy("Hirekarma");
			universityJobShareToStudent.setCreatedOn(new Timestamp(new java.util.Date().getTime()));
			emailController.sendEmailNotificationAboutJobToStudent(filteredStudents.get(i).getStudentEmail(), job, filteredStudents.get(i),corporate);
			universityJobShareRepository.save(universityJobShareToStudent);
			
			list.add(universityJobShareToStudent);
		}
		response.put("shareJob", list);
		response.put("totalSharedJob", count);	
		return response;
	}

	@Override
	public CampusDriveResponseBean campusDriveRequest(CampusDriveResponseBean campus, String jwtToken)
			throws Exception {
		CampusDriveResponseBean driveResponseBean = new CampusDriveResponseBean();
		CampusDriveResponse driveResponse = null;
		University university = new University();
		try {
			LOGGER.debug("Inside UniversityServiceImpl.campusDriveRequest(-)");

			String[] chunks1 = jwtToken.split(" ");
			String[] chunks = chunks1[1].split("\\.");
			Base64.Decoder decoder = Base64.getUrlDecoder();

			String payload = new String(decoder.decode(chunks[1]));
			JSONParser jsonParser = new JSONParser();
			Object obj = jsonParser.parse(payload);

			JSONObject jsonObject = (JSONObject) obj;

			String email = (String) jsonObject.get("sub");
//			getting university
			university = universityRepository.findByEmail(email);
			Optional<?> optional = null;
/*
 get univeristy,job
 if campusDriveRespone exist with particular unviersity ,corporate and job then throw error else create new one
 */
			if (university != null) {

				 Job job = jobRepository.getById(campus.getJobId());
				 if(job==null)
				 {
					 throw new Exception("Corporate not found");
				 }
						LOGGER.info("FOUNDED JOB ");
						Long campusList = campusDriveResponseRepository.findSharedCampus(university.getUniversityId(),
								job.getCorporateId(), job.getJobId());
						System.out.println("*********\n\n\n" + campusList + "\n\n\n************");
						LOGGER.info("{} {} {}",university.getUniversityId(),
								job.getJobId(), job.getCorporateId());
						Object object = shareJobRepository.getRequestVerificationDetails(university.getUniversityId(),
								job.getJobId(), job.getCorporateId());

						if (object != null) {
							LOGGER.info("object is not null");
							if (campusList == 0) {

								driveResponse = new CampusDriveResponse();

								driveResponse.setJobId(job.getJobId());
								driveResponse.setCorporateId(job.getCorporateId());
								driveResponse.setUniversityId(university.getUniversityId());
								driveResponse.setUniversityAskedOn(new Timestamp(new java.util.Date().getTime()));
								driveResponse.setCorporateResponse(true);
								driveResponse.setUniversityAsk(true);

								campusDriveResponseRepository.save(driveResponse);

								BeanUtils.copyProperties(driveResponse, driveResponseBean);

								LOGGER.info(
										"Data Inserted Successfully In UniversityServiceImpl.campusDriveRequest(-)");
							} else {
								throw new CampusDriveResponseException(
										"Already Request Sended To This Perticular Corporate !!");
							}
						} else {
							throw new CampusDriveResponseException(
									"No Shared Job Found From This Corporate  To Your University !!");
						}
					
			
			} else {
				throw new CampusDriveResponseException("Token invalid");
			}

		} catch (Exception e) {

			LOGGER.info("Data Insertation Failed In UniversityServiceImpl.campusDriveRequest(-)" + e);
			throw e;
		}
		return driveResponseBean;
	}

	@Override
	public List<?> seeShareJobList(String token) throws Exception {

		String[] chunks1 = token.split(" ");
		String[] chunks = chunks1[1].split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();

		String payload = new String(decoder.decode(chunks[1]));
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(payload);

		JSONObject jsonObject = (JSONObject) obj;

		String email = (String) jsonObject.get("sub");

		Long id = universityRepository.findIdByEmail(email);
LOGGER.info("universityRepository.findIdByEmail return id = "+id);
		AdminSharedJobList adminSharedJobList = null;
		List<AdminSharedJobList> SharedJobList = new ArrayList<AdminSharedJobList>();
		try {
			if (id != null) {
				List<Object[]> list = adminShareJobToUniversityRepository.getJobDetailsByUniversityId(id);

				if (list.size() != 0) {
					for (Object[] obj1 : list) {
						AdminShareJobToUniversity adminShareJobToUniversity = (AdminShareJobToUniversity) obj1[0];
						Job j = (Job) obj1[1];
						adminSharedJobList = new AdminSharedJobList();

						adminSharedJobList.setUniversityResponseStatus(adminShareJobToUniversity.getUniversityResponseStatus().toString());
						adminSharedJobList.setShareJobId(adminShareJobToUniversity.getShareJobId().toString());
						adminSharedJobList.setJobTitle(j.getJobTitle());
						adminSharedJobList.setCategory(j.getCategory());
						adminSharedJobList.setJobType(j.getJobType());
						adminSharedJobList.setWfhCheckbox(j.getWfhCheckbox());
						adminSharedJobList.setSkills(j.getSkills());
						adminSharedJobList.setCity(j.getCity());
						adminSharedJobList.setOpenings(j.getOpenings().toString());
						adminSharedJobList.setSalary(j.getSalary().toString());
						adminSharedJobList.setAbout(j.getAbout());
						adminSharedJobList.setDescription(j.getDescription());
						adminSharedJobList.setJobId(j.getJobId().toString());
						adminSharedJobList.setCorporateId(j.getCorporateId().toString());
						adminSharedJobList.setDescriptionFileUrl(j.getDescriptionFileUrl());
						adminSharedJobList.setRolesAndResponsibility(j.getRolesAndResponsibility());	
						adminSharedJobList.setEligibilityCriteria(j.getEligibilityCriteria());
						adminSharedJobList.setBranchs(j.getBranchs());
						adminSharedJobList.setStreams(j.getStreams());

						SharedJobList.add(adminSharedJobList);
					}

				} else {
					throw new UniversityException("You Don't Have Any Shared Job !!");
				}
			} else {
				throw new UniversityException("University Data Not Found !!");
			}
		} catch (Exception e) {
			throw e;
		}

		return SharedJobList;
	}

	@Override
	public List<?> studentDetails(String token) throws Exception {
		String[] chunks1 = token.split(" ");
		String[] chunks = chunks1[1].split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();

		String payload = new String(decoder.decode(chunks[1]));
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(payload);

		JSONObject jsonObject = (JSONObject) obj;

		String email = (String) jsonObject.get("sub");
		List<Student> studentList = new ArrayList<Student>();
		List<University> university = new ArrayList<University>();

		university = universityRepository.getDetailsByEmail1(email);

		try {
			if (university.size() == 1) {
				studentList = studentRepository.getStudentListForUniversity(university.get(0).getUniversityId());
				
				if (studentList != null) {

				} else {
					throw new UniversityException("No Student Found In Your University !!");
				}
			} else {
				throw new UniversityException("Something Went Wrong !! Duplicte Data Found !!");
			}
		} catch (Exception e) {
			throw e;
		}
		List<StudentResponseBean> studentResponseBeans = new ArrayList<>();
		for(Student s: studentList) {
			StudentResponseBean studentResponseBean = new StudentResponseBean();
			BeanUtils.copyProperties(s, studentResponseBean);
			studentResponseBean.setStudentBatch(s.getBatch()!=null? studentBatchRepository.getById(s.getBatch()):null);
			studentResponseBean.setStudentBranch(s.getBranch()!=null? studentBranchRepository.getById(s.getBranch()):null);
			studentResponseBeans.add(studentResponseBean);;
		}
		return studentResponseBeans;
	}

	@Override
	public List<?> seeShareJobListToStudent(String token) throws Exception {
		String[] chunks1 = token.split(" ");
		String[] chunks = chunks1[1].split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();

		String payload = new String(decoder.decode(chunks[1]));
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(payload);

		JSONObject jsonObject = (JSONObject) obj;

		String email = (String) jsonObject.get("sub");

		Long id = universityRepository.findIdByEmail(email);

		StudentResponseToUniversity responseToUniversity = null;
		List<StudentResponseToUniversity> responseToUniversityList = new ArrayList<StudentResponseToUniversity>();
		List<UniversityJobShareToStudent> universityJobShareToStudent = new ArrayList<UniversityJobShareToStudent>();

		try {
			if (id != null) {
				universityJobShareToStudent = universityJobShareRepository.getSharedJobList(id);
				if (universityJobShareToStudent.size() != 0) {
					for (int i = 0; i < universityJobShareToStudent.size(); i++) {

						responseToUniversity = new StudentResponseToUniversity();

						responseToUniversity
								.setJob(jobRepository.findByJobId(universityJobShareToStudent.get(i).getJobId()));
						responseToUniversity.setStudent(
								studentRepository.findByStudentId(universityJobShareToStudent.get(i).getStudentId()));

						if (responseToUniversity.getJob() != null | responseToUniversity.getStudent() != null) {

							if (universityJobShareToStudent.get(i).getStudentResponseStatus() != null
									&& universityJobShareToStudent.get(i).getStudentResponseStatus()) {

								responseToUniversity.setStudentResponse("ACCEPTED");

							} else if (universityJobShareToStudent.get(i).getStudentResponseStatus() != null
									&& !universityJobShareToStudent.get(i).getStudentResponseStatus()) {

								responseToUniversity.setStudentResponse("REJECTED");

							} else {
								responseToUniversity.setStudentResponse("No Response !!");
							}

						} else {
							responseToUniversity.setStudentResponse("No Data Found For This Job Or Student !!");
						}

						responseToUniversity.setStudentFeedBack(universityJobShareToStudent.get(i).getFeedBack());

						responseToUniversityList.add(responseToUniversity);
					}

				} else {
					throw new UniversityException("You Are Not Sharing Any Job To Students !!");
				}
			} else {
				throw new UniversityException("Something Went Wrong !! University Data Not Found !!");
			}

		} catch (Exception e) {
			throw e;
		}
		return responseToUniversityList;
	}

	@Override
	public List<?> studentFilter(String token, Long batchId, Long branchId, Double cgpa) throws ParseException {
		String[] chunks1 = token.split(" ");
		String[] chunks = chunks1[1].split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();

		String payload = new String(decoder.decode(chunks[1]));
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(payload);

		JSONObject jsonObject = (JSONObject) obj;

		String email = (String) jsonObject.get("sub");
		List<Student> studentList = new ArrayList<Student>();
		List<University> university = new ArrayList<University>();

		university = universityRepository.getDetailsByEmail1(email);

		try {
			if (university.size() == 1) {
				studentList = studentRepository.getStudentFilter(university.get(0).getUniversityId(), batchId, branchId,
						cgpa);

				if (studentList.size() != 0) {

				} else {
					throw new UniversityException("No Student Found In This Filter !!");
				}
			} else {
				throw new UniversityException("Something Went Wrong !! Duplicte Data Found !!");
			}
		} catch (Exception e) {
			throw e;
		}

		return studentList;
	}
	
	public Map<String,Object> getAllJobsSharedByUniversity(University university){
		Map<String,Object> result = new HashMap<String,Object>();
		List<UniversityJobShareToStudent> universityJobShareToStudents = this.universityJobShareRepository.findByUniversityId(university.getUniversityId());
		
		List<Job> jobs = new ArrayList<Job>();
		Set<Long> jobIds = new HashSet<>();
		List<UniversityShareJobToStudentResponse> universityShareJobToStudentResponses = new ArrayList<UniversityShareJobToStudentResponse>();
		for(UniversityJobShareToStudent u:universityJobShareToStudents) {
			if(!jobIds.contains(u.getJobId())) {
				UniversityShareJobToStudentResponse universityShareJobToStudentResponse = new UniversityShareJobToStudentResponse();
				jobIds.add(u.getJobId());
				Job job = jobRepository.getById(u.getJobId());
				AdminShareJobToUniversity adminShareJobToUniversity = this.adminShareJobToUniversityRepository.findByJobIdAndUniversityId(job.getJobId(), university.getUniversityId());
				
				universityShareJobToStudentResponse.setJob(job);
				universityShareJobToStudentResponse.setShareJobId(adminShareJobToUniversity.getShareJobId());
				universityShareJobToStudentResponses.add(universityShareJobToStudentResponse);
			}
		}
		result.put("data", universityShareJobToStudentResponses);
		return result;
	}

	@Override
	public void removeStudentFromUniversity(String token, Long studentId) throws Exception {
		String email = Validation.validateToken(token);
		University university = universityRepository.findByEmail(email);
		Student student = studentRepository.findByStudentId(studentId);
		if(student==null) {
			throw new Exception("no such user found");
		}
		System.out.println(student.getUniversityId().compareTo(university.getUniversityId()));
		if( student.getUniversityId().compareTo(university.getUniversityId())!=0) {
		
			throw new Exception("unauthorized");
		}
		student.setUniversityId(null);
		this.studentRepository.save(student);
		
	}
	@Override
	public Map<String, Object> universityShareNotice(NoticeBean noticeBean,MultipartFile file,University university) throws Exception{
		Notice notice = new Notice();
		BeanUtils.copyProperties(noticeBean, notice);
		
		if(file!=null && !file.isEmpty()) {
			notice.setImageUrl(awss3Service.uploadFile(file));
		}
		if(noticeBean.getDeadLineString()!=null && !noticeBean.getDeadLineString().equals("")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			LocalDateTime deadLine = LocalDateTime.parse(noticeBean.getDeadLineString(), formatter);
			notice.setDeadLine(deadLine);
		}
		Map<String,Object> response = new HashMap<>();
		notice.setCreatedOn(LocalDateTime.now());
		notice.setUniversityId(university.getUniversityId());
		notice = this.noticeRepository.save(notice);
		List<Student> students = this.studentRepository.getStudentListForUniversity(university.getUniversityId());
		emailController.sendEmailToStudentAboutNotice(students);
		return response;
	}
}
