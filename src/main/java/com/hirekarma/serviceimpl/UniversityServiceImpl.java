package com.hirekarma.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.AdminShareJobToUniversityBean;
import com.hirekarma.beans.CampusDriveResponseBean;
import com.hirekarma.beans.UniversityJobShareToStudentBean;
import com.hirekarma.exception.CampusDriveResponseException;
import com.hirekarma.exception.UniversityException;
import com.hirekarma.exception.UserProfileException;
import com.hirekarma.model.AdminShareJobToUniversity;
import com.hirekarma.model.CampusDriveResponse;
import com.hirekarma.model.UniversityJobShareToStudent;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.CampusDriveResponseRepository;
import com.hirekarma.repository.ShareJobRepository;
import com.hirekarma.repository.UniversityJobShareRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.UniversityService;

@Service("universityServiceImpl")
public class UniversityServiceImpl implements UniversityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UniversityServiceImpl.class);

	@Autowired
	private ShareJobRepository shareJobRepository;

	@Autowired
	private UserRepository userRepository;

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

		UniversityJobShareToStudent universityJobShareToStudent = null;
		UserProfile userProfile = null;
		List<UniversityJobShareToStudent> list = new ArrayList<UniversityJobShareToStudent>();
		Long count = 0L;

		Map<String, Object> response = new HashMap<String, Object>();

		try {
			LOGGER.debug("Inside UniversityServiceImpl.shareJobStudent(-)");

			String[] chunks = universityJobShareToStudentBean.getToken().split("\\.");
			Base64.Decoder decoder = Base64.getUrlDecoder();

			String payload = new String(decoder.decode(chunks[1]));
			JSONParser jsonParser = new JSONParser();
			Object obj = jsonParser.parse(payload);

			JSONObject jsonObject = (JSONObject) obj;

			String name = (String) jsonObject.get("sub");

			universityJobShareToStudentBean.setTokenKey(name);

			userProfile = userRepository.findByEmail(universityJobShareToStudentBean.getTokenKey(), "university");

			if (userProfile != null) {

				if (universityJobShareToStudentBean != null) {

					for (int i = 0; i < universityJobShareToStudentBean.getStudentId().size(); i++) {
						count++;
						universityJobShareToStudent = new UniversityJobShareToStudent();
						universityJobShareToStudent.setJobId(universityJobShareToStudentBean.getJobId());
						universityJobShareToStudent.setUniversityId(userProfile.getUserId());
						universityJobShareToStudent.setJobStatus(true);
						universityJobShareToStudent.setStudentId(universityJobShareToStudentBean.getStudentId().get(i));
						universityJobShareToStudent.setCreatedBy("Biswa");
						universityJobShareToStudent.setCreatedOn(new Timestamp(new java.util.Date().getTime()));

						universityJobShareRepository.save(universityJobShareToStudent);
						list.add(universityJobShareToStudent);
					}

					LOGGER.info("Data Updated Successfully In UniversityServiceImpl.shareJobStudent(-)");
				}

				response.put("shareJob", list);
				response.put("totalSharedJob", count);
			} else {
				throw new UserProfileException("Invalid Token !!");
			}

		} catch (Exception e) {
			LOGGER.info("Data Updatation Failed In UniversityServiceImpl.shareJobStudent(-)" + e);
			throw e;
		}

		return response;
	}

	@Override
	public CampusDriveResponseBean campusDriveRequest(CampusDriveResponseBean campus) {
		CampusDriveResponseBean driveResponseBean = new CampusDriveResponseBean();
		CampusDriveResponse driveResponse = null;
		try {
			LOGGER.debug("Inside UniversityServiceImpl.campusDriveRequest(-)");
			Long campusList = campusDriveResponseRepository.findSharedCampus(campus.getUniversityId(),
					campus.getCorporateId(), campus.getJobId());
			System.out.println("*********\n\n\n" + campusList + "\n\n\n************");
			if (campusList == 0) {

				driveResponse = new CampusDriveResponse();

				driveResponse.setJobId(campus.getJobId());
				driveResponse.setCorporateId(campus.getCorporateId());
				driveResponse.setUniversityId(campus.getUniversityId());
				driveResponse.setUniversityAskedOn(new Timestamp(new java.util.Date().getTime()));
				driveResponse.setCorporateResponse(false);
				driveResponse.setUniversityAsk(true);

				campusDriveResponseRepository.save(driveResponse);

				BeanUtils.copyProperties(driveResponse, driveResponseBean);

				LOGGER.info("Data Inserted Successfully In UniversityServiceImpl.campusDriveRequest(-)");
			} else {
				throw new CampusDriveResponseException("You Have Already Shared Asked To This Perticular Corporate !!");
			}

		} catch (Exception e) {

			LOGGER.info("Data Insertation Failed In UniversityServiceImpl.campusDriveRequest(-)" + e);
			throw e;
		}
		return driveResponseBean;
	}

}
