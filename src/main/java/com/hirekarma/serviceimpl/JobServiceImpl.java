package com.hirekarma.serviceimpl;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.internal.build.AllowSysOut;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.JobBean;
import com.hirekarma.beans.JobModelResponseBean;
import com.hirekarma.beans.JobResponseBean;
import com.hirekarma.email.controller.EmailController;
import com.hirekarma.exception.JobException;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Job;
import com.hirekarma.model.JobApply;
import com.hirekarma.model.Stream;
import com.hirekarma.model.Student;
import com.hirekarma.model.StudentBranch;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.JobApplyRepository;
import com.hirekarma.repository.JobRepository;
import com.hirekarma.repository.StreamRepository;
import com.hirekarma.repository.StudentBranchRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.JobService;
import com.hirekarma.utilty.Validation;

@Service("jobService")
public class JobServiceImpl implements JobService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobServiceImpl.class);

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private CorporateRepository corporateRepository;
	
	@Autowired
	private StudentBranchRepository studentBranchRepository;

	@Autowired
	private StreamRepository streamRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JobApplyRepository jobApplyRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private EmailController emailController;

	@Autowired
	private AWSS3Service awss3Service;
	
//	Job updateJobFromJobBeanForNotNull(JobBean jobBean,Job job) throws Exception {
////		for adding branches
//		Map<Long,String> branchOutput = new HashMap<>();
//		if(jobBean.getBranchIds().size()!=0) {
//			jobBean.setBranchs(new ArrayList<>());
//			for(Integer j :jobBean.getBranchIds()) {
//				Optional<StudentBranch> studentBranch = studentBranchRepository.findById((long)j);
//				if(studentBranch.isEmpty()) {
//					throw new Exception("incorrect branch id");
//				}			
//				jobBean.getBranchs().add(studentBranch.get());
//			}
//		}
//		
////		for adding streams
//		List<Stream> streamsTobeAddedToJob = new ArrayList<>();
//		if( jobBean.getStreamIds().size()!=0 ) {
//			jobBean.setStreams(new ArrayList<>());
//			for(Integer j:jobBean.getStreamIds()) {
//				Optional<Stream> stream = streamRepository.findById(j);
//				if(stream.isEmpty()) {
//					throw new Exception("inccorect stream id");
//				}
//				jobBean.getStreams().add(stream.get());
//			}
//		}
//		
//		
//		return job;
//	}
	
	//save job
	public JobModelResponseBean saveJob(JobBean jobBean,String token) throws Exception {
		Job job = new Job();
		JobResponseBean jobResponseBean = new JobResponseBean();
		LOGGER.debug("Inside insertJob()");
		String email = Validation.validateToken(token);
		
		//finding corporate
		Corporate corporate = corporateRepository.findByEmail(email);
	
		if(corporate.getProfileUpdationStatus()==null || !corporate.getProfileUpdationStatus()) {
			throw new Exception("Please update your profile first");
		}
		if(corporate.getAbout()==null||corporate.getAbout().equals("")) {
			throw new Exception("please update your profile - about");
		}
		jobBean.setAbout(corporate.getAbout());
		if(corporate==null) {
			throw new Exception("corporate not found");
		}
		
		if (corporate.getCorporateBadge() != null && corporate.getCorporateBadge() == 3) {
			jobBean.setStatus(true);
		} else {
			jobBean.setStatus(false);
		}
		job.setCorporateId(corporate.getCorporateId());
		job.setDeleteStatus(false);	
		job = this.jobRepository.save(copyPropertiesFromBeanToJobForNotNull(job, jobBean));
		emailController.createJob(job, corporate);
		JobModelResponseBean jobModelResponseBean = new JobModelResponseBean();
		BeanUtils.copyProperties(job, jobModelResponseBean);
		jobModelResponseBean.setCorporate(corporate);
		return jobModelResponseBean;
	}
	
//	get all job according to token
	
	@Override
	public List<JobResponseBean> getAllJobsAccordingToToken(String token) throws Exception {
		String email  = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findUserByEmail(email);
		System.out.println(userProfile.getUserType());
		System.out.println(userProfile.getUserType()=="corporate");
		System.out.println(userProfile.getUserType().equals("corporate"));
		if(userProfile.getUserType().equals("admin")) {
			return getAllJobsForAdmin();
		}
		else if(userProfile.getUserType().equals("student")) {
			Student student = this.studentRepository.findByStudentEmail(userProfile.getEmail());
			
			return getAllJobsForStudent(student);
		}
		else if(userProfile.getUserType().equals("corporate")) {
			System.out.print("insidde corporate");
			Corporate corporate = corporateRepository.findByEmail(email);
//			Corporate corporate = corporateRepository.findByUserProfile(userProfile.getUserId());
			return getAllJobsForCorporate(corporate.getCorporateId());
		}
		
		throw new Exception("not a valid user");
	}
	@Override
	public JobBean insert(JobBean jobBean, String token) {
		LOGGER.debug("Inside JobServiceImpl.insert()");
		Job job = null;
		Job jobReturn = null;
		JobBean bean = null;
		byte[] image = null;
		Corporate corporate = null;
		try {
			LOGGER.debug("Inside try block of JobServiceImpl.insert()");

//			finding unique cooperate by its email using token
			String[] chunks1 = token.split(" ");
			String[] chunks = chunks1[1].split("\\.");
			Base64.Decoder decoder = Base64.getUrlDecoder();

			String payload = new String(decoder.decode(chunks[1]));
			JSONParser jsonParser = new JSONParser();
			Object obj = jsonParser.parse(payload);

			JSONObject jsonObject = (JSONObject) obj;

			String email = (String) jsonObject.get("sub");

			corporate = corporateRepository.findByEmail(email);

			//adding branch to job
			List<StudentBranch> branchesToBeAddToJob = new ArrayList<>();
			for(Integer j :jobBean.getBranchIds()) {
				StudentBranch studentBranch = studentBranchRepository.getById((long)j);
				if(studentBranch==null) {
					throw new Exception("incorrect branch id");
				}
				System.out.println(studentBranch);
				jobBean.getBranchs().add(studentBranch);
				
			}
			
//			add stream to job
			List<Stream> streamsTobeAddedToJob = new ArrayList<>();
			for(Integer j:jobBean.getStreamIds()) {
				Stream stream = streamRepository.getById(j);
				if(stream==null) {
					throw new Exception("inccorect stream id");
				}
				jobBean.getStreams().add(stream);
			}
			
			System.out.println("streamsss"+jobBean.getStreams());
//			if cooperate found then
			if (corporate != null) {

				image = jobBean.getFile().getBytes();
				
				jobBean.setDeleteStatus(false);
				
				jobBean.setCorporateId(corporate.getCorporateId());
				if (corporate.getCorporateBadge() != null && corporate.getCorporateBadge() == 3) {
					jobBean.setStatus(true);
				} else {
					jobBean.setStatus(false);
				}

				job = new Job();
				BeanUtils.copyProperties(jobBean, job);
				jobReturn = jobRepository.save(job);
				bean = new JobBean();
				BeanUtils.copyProperties(jobReturn, bean);
				LOGGER.info("Data successfully saved using JobServiceImpl.insert(-)");

			} else {
				throw new JobException("Corporate Data Not Found !!");
			}
			return bean;

		} catch (Exception e) {
			LOGGER.error("Data Insertion failed using JobServiceImpl.insert(-): " + e);
			throw new JobException(e.getMessage());
		}
	}

	@Override
	public List<JobBean> findJobsByUserId(String token) throws ParseException {
		LOGGER.debug("Inside JobServiceImpl.findJobsByUserId(-)");
		List<Job> jobs = null;
		List<JobBean> jobBeans = null;
		JobBean jobBean = null;
		boolean flag = false;
		Corporate corporate = null;
		String[] chunks1 = token.split(" ");
		String[] chunks = chunks1[1].split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();

		String payload = new String(decoder.decode(chunks[1]));
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(payload);

		JSONObject jsonObject = (JSONObject) obj;

		String email = (String) jsonObject.get("sub");

		corporate = corporateRepository.findByEmail(email);

		try {
			if (corporate != null) {
				LOGGER.debug("Inside try block of JobServiceImpl.findJobsByUserId(-)");
				jobs = jobRepository.findJobsByUserId(corporate.getCorporateId(), false);
				if (jobs != null) {
					if (jobs.size() > 0) {
						jobBeans = new ArrayList<JobBean>();
						for (Job job : jobs) {
							jobBean = new JobBean();
							BeanUtils.copyProperties(job, jobBean);
							jobBeans.add(jobBean);
						}
						flag = true;
					} else {
						flag = false;
					}
				} else {
					flag = false;
				}
				if (flag) {
					LOGGER.info("Data successfully fetched using JobServiceImpl.findJobsByUserId(-)");
					return jobBeans;
				} else {
					LOGGER.info("No jobs are there. Get Result using JobServiceImpl.findJobsByUserId(-)");
					return jobBeans;
				}
			} else {
				throw new JobException("Corporate Data Not Found !!");
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in JobServiceImpl.findJobsByUserId(-): " + e);
			throw new JobException(e.getMessage());
		}
	}

	@Override
	public JobBean findJobById(Long jobId, String token) throws ParseException {
		LOGGER.debug("Inside JobServiceImpl.findJobById(-)");
		Job job = null;
		Optional<Job> optional = null;
		JobBean jobBean = null;
		Corporate corporate = null;

		String[] chunks1 = token.split(" ");
		String[] chunks = chunks1[1].split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();

		String payload = new String(decoder.decode(chunks[1]));
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(payload);

		JSONObject jsonObject = (JSONObject) obj;

		String email = (String) jsonObject.get("sub");

		try {
			LOGGER.debug("Inside try block of JobServiceImpl.findJobById(-)");

			corporate = corporateRepository.findByEmail(email);

			if (corporate != null) {

				optional = jobRepository.getJobDetails(jobId, corporate.getCorporateId());

				if (!optional.isEmpty()) {

					job = optional.get();

					if (job != null && !job.getDeleteStatus()) {

						jobBean = new JobBean();
						BeanUtils.copyProperties(job, jobBean);

						LOGGER.info("Data Successfully fetched using JobServiceImpl.findJobById(-)");
					} else {
						throw new JobException("This Job Has Been Removed !!");
					}
				}
			} else {
				throw new JobException("Corporate Data Not Found !!");
			}
			return jobBean;
		} catch (Exception e) {
			LOGGER.error("Error occured in JobServiceImpl.findJobById(-): " + e);
			throw new JobException(e.getMessage());
		}
	}

//	we are not actually deleteing anything but setting status of delete true f
	@Override
	public List<JobBean> deleteJobById(Long jobId, String token) throws ParseException {
		LOGGER.debug("Inside JobServiceImpl.deleteJobById(-)");
		Job job = null;
		Optional<Job> optional = null;
		Corporate corporate = null;
		List<JobBean> jobBeans = null;

		String[] chunks1 = token.split(" ");
		String[] chunks = chunks1[1].split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();

		String payload = new String(decoder.decode(chunks[1]));
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(payload);

		JSONObject jsonObject = (JSONObject) obj;

		String email = (String) jsonObject.get("sub");
		try {
			LOGGER.debug("Inside try block of JobServiceImpl.deleteJobById(-)");

			corporate = corporateRepository.findByEmail(email);

			if (corporate != null) {

				optional = jobRepository.getJobDetails(jobId, corporate.getCorporateId());

				if (optional.isPresent()) {

					job = optional.get();

					if (job != null) {

						job.setDeleteStatus(true);
						job.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));

						jobRepository.save(job);
						jobBeans = findJobsByUserId(token);
					}
				} else {
					throw new JobException("This Job Is Not Available !!");
				}
			} else {
				throw new JobException("Corporate Not Available !!");
			}
			LOGGER.debug("Data Successfully Deleted using JobServiceImpl.findJobById(-)");
			return jobBeans;
		} catch (Exception e) {
			LOGGER.error("Error occured in JobServiceImpl.deleteJobById(-): " + e);
			throw new JobException(e.getMessage());
		}
	}
	
	@Override
	public Job updateJobById2(JobBean jobBean, String token) throws Exception{
		String email = Validation.validateToken(token);
		Corporate corporate = corporateRepository.findByEmail(email);
		Job job = jobRepository.findByJobId(jobBean.getJobId());
		if(job.getStatus()!=null && job.getStatus()) {
			throw new Exception("unauthorized - job has been activated by admin");
		}
		System.out.println(corporate.getCorporateId() +" "+job.getCorporateId());
		if(job==null || job.getCorporateId().compareTo(corporate.getCorporateId())!=0|| job.getDeleteStatus()) {
			throw new Exception("unauthorized");
		}
		job = copyPropertiesFromBeanToJobForNotNull(job,jobBean);
		return this.jobRepository.save(job);

	}
	Job copyPropertiesFromBeanToJobForNotNull(Job job,JobBean jobBean) throws Exception {
		
		if(jobBean.getJobTitle()!=null) {
			job.setJobTitle(jobBean.getJobTitle());
		}
		if(jobBean.getWfhCheckbox()!=null) {
			job.setWfhCheckbox(jobBean.getWfhCheckbox());
		}
		if(jobBean.getSkills()!=null) {
			job.setSkills(jobBean.getSkills());
		}
		if(jobBean.getCity()!=null) {
			job.setCity(jobBean.getCity());
		}
		if(jobBean.getOpenings()!=null) {
			job.setOpenings(jobBean.getOpenings());
		}
		
		if(jobBean.getSalary()!=null) {
			job.setSalary(jobBean.getSalary());
		}
		if(jobBean.getAbout()!=null) {
			job.setAbout(jobBean.getAbout());
		}
		if(jobBean.getDescription()!=null) {
			job.setDescription(jobBean.getDescription());
		}
		if(jobBean.getFile()!=null) {
			job.setDescriptionFileUrl(awss3Service.uploadFile(jobBean.getFile()));
			System.out.println(job.getDescriptionFileUrl());
		}
		if(jobBean.getBranchIds()!=null && jobBean.getBranchIds().size()>0) {
			job.setBranchs(getAllBranchFromtheirIds(jobBean.getBranchIds()));
		}
		if(jobBean.getStreamIds()!=null && jobBean.getStreamIds().size()>0) {
			job.setStreams(getAllStreamsFromtheirIds(jobBean.getStreamIds()));
		}
		if(jobBean.getEligibilityCriteria()!=null) {
			job.setEligibilityCriteria(jobBean.getEligibilityCriteria());
		}
		if(jobBean.getRolesAndResponsibility()!=null) {
			job.setRolesAndResponsibility(jobBean.getRolesAndResponsibility());
		}
		if(jobBean.getSalary()!=null) {
			job.setSalary(jobBean.getSalary());
		}
		if(jobBean.getSalaryAtProbation()!=null) {
			job.setSalaryAtProbation(jobBean.getSalaryAtProbation());;
		}
		if(jobBean.getServiceAgreement()!=null) {
			job.setServiceAgreement(jobBean.getServiceAgreement());
		}
		if(jobBean.getForcampusDrive()==null && job.getForcampusDrive()==null) {
			job.setForcampusDrive(false);
		}
		else {
			job.setForcampusDrive(jobBean.getForcampusDrive());
		}
		job.setDeleteStatus(false);
		
		if(jobBean.getTentativeDatesforCampusDrive()!=null && !jobBean.getTentativeDatesforCampusDrive().equals("")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(jobBean.getTentativeDatesforCampusDrive()+" 00:00:00", formatter);
			job.setTentativeDate(dateTime);
		}
		LOGGER.info("succesfully fetched all branch");
		
		return job;
	}
	@Override
	public JobBean updateJobById(JobBean jobBean, String token) throws ParseException {

		LOGGER.debug("Inside JobServiceImpl.updateJobById(-)");

		Job job = null;
		Job jobReturn = null;
		Optional<Job> optional = null;
		JobBean bean = null;
		byte[] image = null;
		Corporate corporate = null;

		String[] chunks1 = token.split(" ");
		String[] chunks = chunks1[1].split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();

		String payload = new String(decoder.decode(chunks[1]));
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(payload);

		JSONObject jsonObject = (JSONObject) obj;

		String email = (String) jsonObject.get("sub");

		try {
			LOGGER.debug("Inside try block of JobServiceImpl.updateJobById(-)");

			image = jobBean.getFile().getBytes();
			corporate = corporateRepository.findByEmail(email);

			if (corporate != null) {

				optional = jobRepository.getJobDetails(jobBean.getJobId(), corporate.getCorporateId());

				if (optional.isPresent()) {

					job = optional.get();

					if (job != null && !job.getDeleteStatus()) {

						job.setJobTitle(jobBean.getJobTitle());
						job.setCategory(jobBean.getCategory());
						job.setJobType(jobBean.getJobType());
						job.setWfhCheckbox(jobBean.getWfhCheckbox());
						job.setSkills(jobBean.getSkills());
						job.setCity(jobBean.getCity());
						job.setOpenings(jobBean.getOpenings());
						job.setSalary(jobBean.getSalary());
						job.setAbout(jobBean.getAbout());
						job.setDescription(jobBean.getDescription());
						job.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));

						jobReturn = jobRepository.save(job);

						bean = new JobBean();
						BeanUtils.copyProperties(jobReturn, bean);

						LOGGER.info("Data Successfully updated using JobServiceImpl.updateJobById(-)");
					} else {
						throw new JobException("This Job Has Been Removed !!");
					}
				} else {
					throw new JobException("This Job Is Not Available With This Corporate!!");
				}
			} else {
				throw new JobException("Corporate Not Available !!");
			}

			return bean;

		} catch (Exception e) {
			LOGGER.error("Error occured in JobServiceImpl.updateJobById(-): " + e);
			throw new JobException(e.getMessage());
		}
	}
	@Override
	public List<JobResponseBean> getAllJobsForAdmin() throws Exception {
		List<Job> jobs = this.jobRepository.getAllJobsForAdmin();
		List<JobResponseBean> jobResponseBeans = new ArrayList<>();
		for(Job j: jobs) {
			JobResponseBean jobResponseBean = new JobResponseBean();
			BeanUtils.copyProperties(j, jobResponseBean);

			Corporate corporate = this.corporateRepository.getById(jobResponseBean.getCorporateId());
			jobResponseBean.setCorporate(corporate);
			jobResponseBeans.add(jobResponseBean);
		}
		return jobResponseBeans;
	}
	@Override
	public List<JobResponseBean> getAllJobsForStudent(Student student) throws Exception {
		List<Job> jobs = this.jobRepository.getAllJobsForStudents();
		List<JobResponseBean> jobResponseBeans = new ArrayList<>();
		for(Job j: jobs) {
			JobResponseBean jobResponseBean = new JobResponseBean();
			BeanUtils.copyProperties(j, jobResponseBean);
			Corporate corporate = this.corporateRepository.getById(j.getCorporateId());
			JobApply jobApply = this.jobApplyRepository.findByStudentIdAndJobId(student.getStudentId(), j.getJobId());
			if(jobApply!=null) {
				jobResponseBean.setAlreadyApplied(true);
			}
			else {
				jobResponseBean.setAlreadyApplied(false);
			}
			jobResponseBean.setCorporate(corporate);
			jobResponseBeans.add(jobResponseBean);
		}
		return jobResponseBeans;
	}
	
	

	public List<JobResponseBean> getAllJobsForCorporate(Long corporateId) throws Exception {
		List<Job> jobs = this.jobRepository.findJobsByUserId(corporateId,false);
		List<JobResponseBean> jobResponseBeans = new ArrayList<>();
		for(Job j: jobs) {
			JobResponseBean jobResponseBean = new JobResponseBean();
			BeanUtils.copyProperties(j, jobResponseBean);
			jobResponseBeans.add(jobResponseBean);
		}
		return jobResponseBeans;
	}
	
	public List<StudentBranch> getAllBranchFromtheirIds(List<Integer> branchIds) throws Exception{
		List<StudentBranch> branches = new ArrayList<>();
		for(Integer j :branchIds) {
			Optional<StudentBranch> studentBranch = studentBranchRepository.findById((long)j);
			if(studentBranch.isEmpty()) {
				throw new Exception("incorrect branch id");
			}
			
			branches.add(studentBranch.get());
			
			
		}
		return branches;
	}
	public List<Stream> getAllStreamsFromtheirIds(List<Integer> streamIds) throws Exception{
		List<Stream> streams = new ArrayList<>();
		for(Integer j :streamIds) {
			Optional<Stream> stream = streamRepository.findById(j);
			if(stream.isEmpty()) {
				throw new Exception("incorrect branch id");
			}
			
			streams.add(stream.get());
			
			
		}
		return streams;
	}
	
	

}
