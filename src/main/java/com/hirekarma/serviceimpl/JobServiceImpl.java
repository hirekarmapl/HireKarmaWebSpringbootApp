package com.hirekarma.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.JobBean;
import com.hirekarma.exception.JobException;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Job;
import com.hirekarma.model.Stream;
import com.hirekarma.model.StudentBranch;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.JobRepository;
import com.hirekarma.repository.StreamRepository;
import com.hirekarma.repository.StudentBranchRepository;
import com.hirekarma.service.JobService;

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

}
