package com.hirekarma.serviceimpl;

import java.io.IOException;
import java.sql.Timestamp;
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
import com.hirekarma.beans.JobResponseBean;
import com.hirekarma.exception.JobException;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Job;
import com.hirekarma.model.Stream;
import com.hirekarma.model.StudentBranch;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.JobRepository;
import com.hirekarma.repository.StreamRepository;
import com.hirekarma.repository.StudentBranchRepository;
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
	
	//save job
	public JobResponseBean saveJob(JobBean jobBean,String token) throws Exception {
		Job jobSaved = new Job();
		JobResponseBean jobResponseBean = new JobResponseBean();
		byte[]  image = null;
		LOGGER.debug("Inside insertJob()");
		String email = Validation.validateToken(token);
		
		//finding corporate
		Corporate corporate = corporateRepository.findByEmail(email);
		if(corporate==null) {
			throw new Exception("corporate not found");
		}
		
		//adding branch to job and getting name of all branch
		Map<Long,String> branchOutput = new HashMap<>();
		jobBean.setBranchs(new ArrayList<>());
		for(Integer j :jobBean.getBranchIds()) {
			Optional<StudentBranch> studentBranch = studentBranchRepository.findById((long)j);
			if(studentBranch.isEmpty()) {
				throw new Exception("incorrect branch id");
			}
			
			jobBean.getBranchs().add(studentBranch.get());
			
			
		}
		LOGGER.info("succesfully fetched all branch");
		
		
		
//		add stream to job
		List<Stream> streamsTobeAddedToJob = new ArrayList<>();
		jobBean.setStreams(new ArrayList<>());
		for(Integer j:jobBean.getStreamIds()) {
			Optional<Stream> stream = streamRepository.findById(j);
			if(stream.isEmpty()) {
				throw new Exception("inccorect stream id");
			}
			
//			System.out.println(stream);
			jobBean.getStreams().add(stream.get());
		}
	
		LOGGER.info("succesfully fetched all stream");
		
		if(jobBean.getFile()!=null) {
			image = jobBean.getFile().getBytes();
		}
		
		
		jobBean.setDescriptionFile(image);
		jobBean.setDeleteStatus(false);
		jobBean.setCorporateId(corporate.getCorporateId());
		
		//for admin set status true otherwise set false
		if (corporate.getCorporateBadge() != null && corporate.getCorporateBadge() == 3) {
			jobBean.setStatus(true);
		} else {
			jobBean.setStatus(false);
		}
		
		BeanUtils.copyProperties(jobBean, jobSaved);
		jobSaved = jobRepository.save(jobSaved);
		jobBean.setJobId(jobSaved.getJobId());
		BeanUtils.copyProperties(jobBean, jobResponseBean);
		
		return jobResponseBean;
		
		
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
			return getAllJobsForStudent();
		}
		else if(userProfile.getUserType().equals("corporate")) {
			System.out.print("insidde corporate");
			Corporate corporate = corporateRepository.findByUserProfile(userProfile.getUserId());
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
				
				jobBean.setDescriptionFile(image);
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
		if(job==null || job.getCorporateId()!=corporate.getCorporateId()|| job.getDeleteStatus()) {
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
		if(jobBean.getFile()!=null && !jobBean.getFile().isEmpty()) {
			job.setDescriptionFile(jobBean.getFile().getBytes());
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
		if(jobBean.getForcampusDrive()!=null) {
			job.setForcampusDrive(jobBean.getForcampusDrive());
		}
		if(jobBean.getBranchIds()!=null && jobBean.getBranchIds().size()>0) {
			job.setBranchs(getAllBranchFromtheirIds(jobBean.getBranchIds()));
		}
		if(jobBean.getStreamIds()!=null && jobBean.getStreamIds().size()>0) {
			job.setStreams(getAllStreamsFromtheirIds(jobBean.getStreamIds()));
		}
		LOGGER.info("succesfully fetched all branch");
		
		
		
//		
		job.setStatus(false);
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
			jobBean.setDescriptionFile(image);
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
						job.setDescriptionFile(jobBean.getDescriptionFile());
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
			jobResponseBeans.add(jobResponseBean);
		}
		return jobResponseBeans;
	}
	@Override
	public List<JobResponseBean> getAllJobsForStudent() throws Exception {
		List<Job> jobs = this.jobRepository.getAllJobsForStudents();
		List<JobResponseBean> jobResponseBeans = new ArrayList<>();
		for(Job j: jobs) {
			JobResponseBean jobResponseBean = new JobResponseBean();
			BeanUtils.copyProperties(j, jobResponseBean);
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
