package com.hirekarma.serviceimpl;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.event.S3EventNotification.ResponseElementsEntity;
import com.hirekarma.beans.AdminShareJobToUniversityBean;
import com.hirekarma.beans.BadgeShareBean;
import com.hirekarma.email.controller.EmailController;
import com.hirekarma.exception.AdminException;
import com.hirekarma.model.AdminShareJobToUniversity;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Job;
import com.hirekarma.model.JobApply;
import com.hirekarma.model.Stream;
import com.hirekarma.model.StudentBranch;
import com.hirekarma.model.University;
import com.hirekarma.repository.AdminShareJobToUniversityRepository;
import com.hirekarma.repository.BadgesRepository;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.JobApplyRepository;
import com.hirekarma.repository.JobRepository;
import com.hirekarma.repository.ShareJobRepository;
import com.hirekarma.repository.StreamRepository;
import com.hirekarma.repository.UniversityRepository;
import com.hirekarma.service.AdminService;

@Service("adminServiceImpl")
public class AdminServiceImpl implements AdminService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	private ShareJobRepository shareJobRepository;

	@Autowired
	private StreamRepository streamRepository;
	@Autowired
	private EmailController emailController;
	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private UniversityRepository universityRepository;

	@Autowired
	private JobApplyRepository jobApplyRepository;

	@Autowired
	private CorporateRepository corporateRepository;

	@Autowired
	private BadgesRepository badgesRepository;
	
	@Autowired
	private AdminShareJobToUniversityRepository adminShareJobToUniversityRepository;

	@Scheduled(cron = "0 0 0 * * *")
	public void jobApplicationStatusChange() {

		LOGGER.info("\n\nSchedular Working For Deactivating The Job Appliaction : " + new Date().toString());

		List<JobApply> JobApplyList = new ArrayList<JobApply>();

		JobApplyList = jobApplyRepository.findAll();

		if (JobApplyList.size() != 0) {

			for (JobApply job : JobApplyList) {

				String time1 = String.valueOf(job.getCreatedOn());
				String[] fetchedTime = time1.split(" ");

				String time2 = String.valueOf(LocalDateTime.now());
				String[] now = time2.split("T");

				LocalDate d1 = LocalDate.parse(fetchedTime[0], DateTimeFormatter.ISO_LOCAL_DATE);
				LocalDate d2 = LocalDate.parse(now[0], DateTimeFormatter.ISO_LOCAL_DATE);

				Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
				long diffDays = diff.toDays();

				if (diffDays == 90) {

					job.setApplicationStatus(false);
					jobApplyRepository.save(job);

					LOGGER.info("Appliaction Rejected For : " + job.getCoverLetter());
				}
			}
		}
	}
	
	@Override
	public void updateJobsStatus(List<Long> jobIds,boolean status) throws Exception{
		List<Job> jobs = null;
		try {
			jobs = jobRepository.findAllById(jobIds);
			for(Job j:jobs) {
				if(j.getCorporateId()!=null) {
					Corporate coporate = this.corporateRepository.getById(j.getCorporateId());
					emailController.activateJob(j, coporate);
				}
			}
			System.out.println(jobs);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw new Exception("invalid job ids");
		}
		this.jobRepository.updateMutipleJobStatus(jobIds,status);
		
		
	}
	@Override
	public Map<String, Object> updateActiveStatus(Long id, boolean status) {

		Job job = new Job();
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			LOGGER.debug("Inside AdminServiceImpl.updateActiveStatus(-)");

			Optional<Job> optional = jobRepository.findById(id);

			job = optional.get();
			if(job.getCorporateId()!=null) {
				Corporate corporate  = this.corporateRepository.getById(id);
				emailController.activateJob(job,corporate);
			}
			if (job != null) {

				job.setStatus(status);
				job.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));

				jobRepository.save(job);

			} else {
				throw new AdminException("Job Value Can't Be Null !!");
			}

			response.put("activeJob", job);

			LOGGER.info("Data Updated Successfully In AdminServiceImpl.updateActiveStatus(-)");
		}

		catch (Exception e) {
			LOGGER.info("Data Updated Failed In AdminServiceImpl.updateActiveStatus(-)" + e);
			throw e;
		}
		return response;
	}

	public AdminShareJobToUniversity updateShareJob(JSONObject lookup,long id) throws Exception{
		AdminShareJobToUniversity adminShareJobToUniversity = this.adminShareJobToUniversityRepository.getById(id);
		if(adminShareJobToUniversity==null) {
			throw new Exception("no user found");
		}
		adminShareJobToUniversity.setLookUp(lookup.toString());
		adminShareJobToUniversity.setJdUpdation(2);
		return this.adminShareJobToUniversityRepository.save(adminShareJobToUniversity);
	}
	
	public Map<String,Object> shareJob2(AdminShareJobToUniversityBean adminShareJobToUniversityBean) throws Exception{
		LOGGER.debug("Inside AdminServiceImpl.shareJob(-)");
		AdminShareJobToUniversityBean user = new AdminShareJobToUniversityBean();
		AdminShareJobToUniversity AdminShareJobToUniversity = null;
		List<AdminShareJobToUniversity> list = new ArrayList<AdminShareJobToUniversity>();
		Map<String, Object> response = new HashMap<String, Object>();
		List<University> universityJobAlreadyShared = new ArrayList<>();
		// checking all the ids are valid
		
		if(this.universityRepository.findUniveristyByIds(adminShareJobToUniversityBean.getUniversityId()).size() !=adminShareJobToUniversityBean.getUniversityId().size())
		{
			throw new Exception("please check uour list");
		}
		
//		getting job
		Optional<Job> optional = jobRepository.findById(adminShareJobToUniversityBean.getJobId());
		if(optional.isEmpty()) {
			throw new Exception("no such job found");
		}
		Job job = optional.get();
		Corporate corporate  = this.corporateRepository.getById(job.getCorporateId());
		if(!job.getStatus() || !job.getForcampusDrive()) {
			throw new Exception("job is either not active or its not avialable for campus drive");
		}
		
		int count = 0;
		if (adminShareJobToUniversityBean.getUniversityId().size() != 0) {
			
			for (int i = 0; i < adminShareJobToUniversityBean.getUniversityId().size(); i++) {
			
				System.out.println("\n\n************"
						+ adminShareJobToUniversityBean.getUniversityId().get(i) + "***************");
				AdminShareJobToUniversity adminShareJobToUniversityExist = adminShareJobToUniversityRepository.findByJobIdAndUniversityId(adminShareJobToUniversityBean.getJobId(), adminShareJobToUniversityBean.getUniversityId().get(i));
				if(adminShareJobToUniversityExist!=null) {
					universityJobAlreadyShared.add(universityRepository.getById(adminShareJobToUniversityBean.getUniversityId().get(i)));
					
				}
				else {
					count++;
					AdminShareJobToUniversity = new AdminShareJobToUniversity();
					AdminShareJobToUniversity.setJobId(adminShareJobToUniversityBean.getJobId());
					AdminShareJobToUniversity
							.setUniversityId(adminShareJobToUniversityBean.getUniversityId().get(i));
					AdminShareJobToUniversity.setJobStatus("ACTIVE");
					AdminShareJobToUniversity.setCreatedBy("Biswa");
					AdminShareJobToUniversity.setCreatedOn(new Timestamp(new java.util.Date().getTime()));
					if(adminShareJobToUniversityBean.getJsonObject()!=null) {

						AdminShareJobToUniversity.setLookUp(adminShareJobToUniversityBean.getJsonObject().toString());
					}
					shareJobRepository.save(AdminShareJobToUniversity);
					BeanUtils.copyProperties(AdminShareJobToUniversity, user);

					List<Stream> streams = job.getStreams();
					List<String> streamString = new ArrayList<>();
					if(streams!=null && streams.size()!=0) {
						for(Stream s:streams) {
							streamString.add(s.getName());
						}
					}
					List<StudentBranch> studentBranchs = job.getBranchs();
					List<String> branchString = new ArrayList<>();
					if(studentBranchs!=null && studentBranchs.size()!=0)
					{
						for(StudentBranch s : studentBranchs) {
							branchString.add(s.getBranchName());
						}
					}
					
					
					emailController.adminShareJobToStudent(job,null,corporate,streamString,branchString);
					list.add(AdminShareJobToUniversity);
				}
				
			}
			user.setResponse("SHARED");
		} else {
			throw new AdminException("No University Selected !!");
		}
		response.put("shareJob", list);
		response.put("totalSharedJob", count);
		response.put("alreadyJobSharedToUniversity", universityJobAlreadyShared);
		response.put("done", true);
		
		return response;
	}
	@Override
	public Map<String, Object> shareJob(AdminShareJobToUniversityBean adminShareJobToUniversityBean) {

		AdminShareJobToUniversityBean user = new AdminShareJobToUniversityBean();
		AdminShareJobToUniversity AdminShareJobToUniversity = null;
		List<AdminShareJobToUniversity> list = new ArrayList<AdminShareJobToUniversity>();
		Job job = new Job();
		Long count = 0L;
		Long universityCount = 0L;
		Optional<University> university = null;

		Map<String, Object> response = new HashMap<String, Object>();

		try {
			LOGGER.debug("Inside AdminServiceImpl.shareJob(-)");
			for (Long id : adminShareJobToUniversityBean.getUniversityId()) {
//				university = new University();
				university = universityRepository.findById(id);
				if (university.isPresent()) {
					universityCount++;
				}
			}
			if (universityCount == adminShareJobToUniversityBean.getUniversityId().size()) {

				Optional<Job> optional = jobRepository.findById(adminShareJobToUniversityBean.getJobId());
				job = optional.get();

				if (job != null) {
					if (adminShareJobToUniversityBean.getUniversityId().size() != 0) {

						for (int i = 0; i < adminShareJobToUniversityBean.getUniversityId().size(); i++) {
							count++;
							System.out.println("\n\n************"
									+ adminShareJobToUniversityBean.getUniversityId().get(i) + "***************");
							AdminShareJobToUniversity = new AdminShareJobToUniversity();
							AdminShareJobToUniversity.setJobId(adminShareJobToUniversityBean.getJobId());
							AdminShareJobToUniversity
									.setUniversityId(adminShareJobToUniversityBean.getUniversityId().get(i));
							AdminShareJobToUniversity.setJobStatus("ACTIVE");
							AdminShareJobToUniversity.setCreatedBy("Biswa");
							AdminShareJobToUniversity.setCreatedOn(new Timestamp(new java.util.Date().getTime()));
							AdminShareJobToUniversity.setLookUp(adminShareJobToUniversityBean.getJsonObject().toString());
							shareJobRepository.save(AdminShareJobToUniversity);
							BeanUtils.copyProperties(AdminShareJobToUniversity, user);
							list.add(AdminShareJobToUniversity);
						}
						user.setToatlSharedJob(count);
						user.setResponse("SHARED");
					} else {
						throw new AdminException("No University Selected !!");
					}
				} else {
					throw new AdminException("No Job Selected !!");
				}
			} else {
				throw new AdminException("University Not Found !! Please Re-Check Your University List !!");
			}

			response.put("shareJob", list);
			response.put("totalSharedJob", count);

		} catch (Exception e) {
			LOGGER.info("Data Updated Failed In AdminServiceImpl.updateActiveStatus(-)" + e);
			throw e;
		}

		return response;
	}

	@Override
	public List<?> displayJobList() {
		List<Job> jobList = new ArrayList<Job>();
		try {
			LOGGER.debug("Inside AdminServiceImpl.displayJobList(-)");

			jobList = jobRepository.getJobAllDetails();

			System.out.println("Total DisplayJobList : " + jobList.size());
		} catch (Exception e) {
			throw e;
		}
		return jobList;
	}

	@Override
	public List<?> displayUniversityList() {
		List<University> UniversityList = new ArrayList<University>();
		try {
			LOGGER.debug("Inside AdminServiceImpl.displayUniversityList(-)");

			UniversityList = universityRepository.displayUniversityList(true);

			System.out.println("Total DisplayUniversityList : " + UniversityList.size());

		} catch (Exception e) {
			throw e;
		}
		return UniversityList;
	}

	@Override
	public Corporate shareBadge(BadgeShareBean badgeShareBean) {

		Optional<?> optional = null;
		Corporate corporate = new Corporate();

		try {
			LOGGER.debug("Inside AdminServiceImpl.shareBadge(-)");

			optional = badgesRepository.findById(badgeShareBean.getBadgeId());
			if (optional.isPresent()) {
				optional = null;
				optional = corporateRepository.findById(badgeShareBean.getCorporateId());
				if (optional.isPresent()) {

					corporate = (Corporate) optional.get();
					corporate.setCorporateBadge(badgeShareBean.getBadgeId());

					corporateRepository.save(corporate);
					LOGGER.info("Badge Successfuly Updated In AdminServiceImpl.shareBadge(-)");
				} else {
					throw new AdminException("Selected Corporate Not Found !!");
				}
			} else {
				throw new AdminException("Selected Badge Not Found !!");
			}
		} catch (Exception e) {
			LOGGER.error("Data Updated Failed In AdminServiceImpl.shareBadge(-)" + e);
			throw e;
		}
		return corporate;
	}

	@Override
	public AdminShareJobToUniversity requestCorporateToUpdateJD(long adminShareJobId) throws Exception {
		AdminShareJobToUniversity adminShareJobToUniversity = this.adminShareJobToUniversityRepository.getById(adminShareJobId);
		adminShareJobToUniversity.setJdUpdation(1);
		return this.adminShareJobToUniversityRepository.save(adminShareJobToUniversity);
		
	}

	
	

}
