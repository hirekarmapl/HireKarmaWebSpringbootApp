package com.hirekarma.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.AdminShareJobToUniversityBean;
import com.hirekarma.beans.AdminSharedJobList;
import com.hirekarma.beans.JobBean;
import com.hirekarma.exception.JobException;
import com.hirekarma.exception.UniversityException;
import com.hirekarma.model.AdminShareJobToUniversity;
import com.hirekarma.model.Job;
import com.hirekarma.repository.JobRepository;
import com.hirekarma.repository.ShareJobRepository;
import com.hirekarma.service.AdminService;

@Service("adminServiceImpl")
public class AdminServiceImpl implements AdminService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	private ShareJobRepository shareJobRepository;

	@Autowired
	private JobRepository jobRepository;

	@Override
	public Map<String, Object> updateActiveStatus(Long id, boolean status) {

		Job job = new Job();
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			LOGGER.debug("Inside AdminServiceImpl.updateActiveStatus(-)");

			Optional<Job> optional = jobRepository.findById(id);

			job = optional.get();

			if (job != null) {

				job.setStatus(status);
				job.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));

				jobRepository.save(job);

			} else {
				throw new JobException("Job Value Can't Be Null !!");
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

	@Override
	public Map<String, Object> shareJob(AdminShareJobToUniversityBean adminShareJobToUniversityBean) {

		AdminShareJobToUniversityBean user = new AdminShareJobToUniversityBean();
		AdminShareJobToUniversity AdminShareJobToUniversity = null;
		List<AdminShareJobToUniversity> list = new ArrayList<AdminShareJobToUniversity>();
		Job job = new Job();
		Long count = 0L;

		Map<String, Object> response = new HashMap<String, Object>();

		try {
			LOGGER.debug("Inside AdminServiceImpl.shareJob(-)");
			Optional<Job> optional = jobRepository.findById(adminShareJobToUniversityBean.getJobId());
			job = optional.get();
			if (job != null) {
				if (adminShareJobToUniversityBean.getUniversityId().size() != 0) {

					for (int i = 0; i < adminShareJobToUniversityBean.getUniversityId().size(); i++) {
						count++;
						System.out.println("\n\n************" + adminShareJobToUniversityBean.getUniversityId().get(i)
								+ "***************");
						AdminShareJobToUniversity = new AdminShareJobToUniversity();
						AdminShareJobToUniversity.setJobId(adminShareJobToUniversityBean.getJobId());
						AdminShareJobToUniversity
								.setUniversityId(adminShareJobToUniversityBean.getUniversityId().get(i));
						AdminShareJobToUniversity.setJobStatus("ACTIVE");
						AdminShareJobToUniversity.setCreatedBy("Biswa");
						AdminShareJobToUniversity.setCreatedOn(new Timestamp(new java.util.Date().getTime()));

						shareJobRepository.save(AdminShareJobToUniversity);
						BeanUtils.copyProperties(AdminShareJobToUniversity, user);
						list.add(AdminShareJobToUniversity);
					}
					user.setToatlSharedJob(count);
					user.setResponse("SHARED");
				} else {
					throw new JobException("No University Selected !!");
				}
			} else {
				throw new JobException("No Job Selected !!");
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
		List<Job> jobBeanList = new ArrayList<Job>();
		Job jobBean = null;
		try {
			jobBeanList = jobRepository.getJobAllDetails();
			
			System.out.println(jobBeanList.size());
				
//				if (list.size() != 0) {
//					for (Object[] obj1 : list) {
//						jobBean = new JobBean();
//
//						adminSharedJobList.setUniversityResponseStatus(String.valueOf(obj1[0]));
//						adminSharedJobList.setShareJobId(String.valueOf(obj1[1]));
//						adminSharedJobList.setJobTitle((String) obj1[2]);
//						adminSharedJobList.setCategory((String) obj1[3]);
//						adminSharedJobList.setJobType((String) obj1[4]);
//						adminSharedJobList.setWfhCheckbox((Boolean) obj1[5]);
//						adminSharedJobList.setSkills((String) obj1[6]);
//						adminSharedJobList.setCity((String) obj1[7]);
//						adminSharedJobList.setOpenings(String.valueOf(obj1[8]));
//						adminSharedJobList.setSalary(String.valueOf(obj1[9]));
//						adminSharedJobList.setAbout((String) obj1[10]);
//						adminSharedJobList.setDescription((String) obj1[11]);
//
//						jobBeanList.add(jobBean);
//					}
//
//				} else {
//					throw new UniversityException("No Job Found !!");
//				}
		} catch (Exception e) {
			throw e;
		}

		return jobBeanList;

	}

}
