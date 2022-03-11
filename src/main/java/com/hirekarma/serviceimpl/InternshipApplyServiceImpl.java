package com.hirekarma.serviceimpl;

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

import com.hirekarma.beans.InternshipApplyBean;
import com.hirekarma.beans.InternshipApplyResponseBean;
import com.hirekarma.beans.UniversitySharedJobList;
import com.hirekarma.exception.InternshipApplyException;
import com.hirekarma.exception.UniversityException;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Internship;
import com.hirekarma.model.InternshipApply;
import com.hirekarma.model.Student;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.InternshipApplyRepository;
import com.hirekarma.repository.InternshipRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.InternshipApplyService;

@Service("internshipApplyServiceImpl")
public class InternshipApplyServiceImpl implements InternshipApplyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InternshipApplyServiceImpl.class);

	@Autowired
	private InternshipApplyRepository internshipApplyRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private InternshipRepository internshipRepository;
	
	@Autowired
	private CorporateRepository corporateRepository;

	@Override
	public InternshipApplyBean insert(InternshipApplyBean internshipApplyBean, String token) throws ParseException {

		LOGGER.debug("Inside InternshipApplyServiceImpl.insert()");
		InternshipApply internshipApply = null, 
		internshipApplyReturn = null;
		InternshipApplyBean applyBean = null;
		List<Student> studentList = new ArrayList<Student>();

		String[] chunks1 = token.split(" ");
		String[] chunks = chunks1[1].split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();

		String payload = new String(decoder.decode(chunks[1]));
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(payload);

		JSONObject jsonObject = (JSONObject) obj;

		String email = (String) jsonObject.get("sub");

		studentList = studentRepository.getDetailsByEmail1(email);
		
		UserProfile userProfile = userRepository.findUserByEmail(email);
		
		try {
			
			Optional<Internship> optional = internshipRepository.findById(internshipApplyBean.getInternshipId());
			if(!optional.isPresent()) {
				throw new Exception("no such internship present");
			}
			Internship internship = optional.get();
			
			LOGGER.debug("Inside try block of InternshipApplyServiceImpl.insert()");
			if (studentList != null && studentList.size() >= 1) {
				if(studentList.get(0).getProfileUpdationStatus()==null || !studentList.get(0).getProfileUpdationStatus()  ) {
					throw new Exception("Please update your profile first!");
				}
				if(userProfile.getSkills()==null || userProfile.getSkills().isEmpty() ) {
					throw new Exception("please enter some skills!");
				}
				if(userProfile.getEducations()==null || userProfile.getEducations().isEmpty() ) {
					throw new Exception("please complete your education detials");
				}
				internshipApply = new InternshipApply();
				BeanUtils.copyProperties(internshipApplyBean, internshipApply);
				internshipApply.setDeleteStatus(false);
				internshipApply.setApplicatinStatus(false);
				internshipApply.setStudentId(studentList.get(0).getStudentId());
				internshipApply.setCorporateId(internship.getCorporateId());
				internshipApply.setInternshipId(internship.getInternshipId());
				internshipApplyReturn = internshipApplyRepository.save(internshipApply);
				
				applyBean = new InternshipApplyBean();
				BeanUtils.copyProperties(internshipApplyReturn, applyBean);
				
				LOGGER.info("Data saved using InternshipApplyServiceImpl.insert()");
			} else {
				throw new InternshipApplyException("Student Data Not Found !! ");
			}
			return applyBean;

		} catch (Exception e) {
			LOGGER.error("Data Insertion failed using InternshipApplyServiceImpl.insert(-): " + e);
			throw new InternshipApplyException(e.getMessage());
		}
	}
	public List<InternshipApplyResponseBean> getAllInternshipsForAStudent(Long studentId){
		List<InternshipApplyResponseBean> internshipApplyResponseBeans = new ArrayList<InternshipApplyResponseBean>();
		List<InternshipApply> internshipApplies = internshipApplyRepository.getAllInternshipByStudentId(studentId);
		System.out.println("inside loop");
		for(InternshipApply i:internshipApplies) {
			
			InternshipApplyResponseBean internshipApplyResponseBean = new InternshipApplyResponseBean();
			BeanUtils.copyProperties(i, internshipApplyResponseBean);
			Internship internship = internshipRepository.getById(internshipApplyResponseBean.getInternshipId());
			Corporate corporate = corporateRepository.getById(internshipApplyResponseBean.getInternshipId());
			internshipApplyResponseBean.setCorporate(corporate);
			internshipApplyResponseBean.setInternship(internship);
			internshipApplyResponseBeans.add(internshipApplyResponseBean);
			
		}
		System.out.println("completetd loop");
		return internshipApplyResponseBeans;
	}
	
	public List<InternshipApplyResponseBean> getAllInternshipApplicationForSpecificCorporate(Long corporateId){
		List<InternshipApplyResponseBean> internshipApplyResponseBeans = new ArrayList<InternshipApplyResponseBean>();
		List<InternshipApply> internshipApplies = internshipApplyRepository.findByCorporateId(corporateId);
		System.out.println("inside loop");
		for(InternshipApply i:internshipApplies) {
			
			InternshipApplyResponseBean internshipApplyResponseBean = new InternshipApplyResponseBean();
			BeanUtils.copyProperties(i, internshipApplyResponseBean);
			Internship internship = internshipRepository.getById(internshipApplyResponseBean.getInternshipId());
			Corporate corporate = corporateRepository.getById(internshipApplyResponseBean.getInternshipId());
			Student student = studentRepository.getById(internshipApplyResponseBean.getStudentId());
//			internshipApplyResponseBean.setCorporate(corporate);
			internshipApplyResponseBean.setInternship(internship);
			internshipApplyResponseBean.setStudent(student);
			internshipApplyResponseBeans.add(internshipApplyResponseBean);
			
		}
		System.out.println("completetd loop");
		return internshipApplyResponseBeans;
	}

}
