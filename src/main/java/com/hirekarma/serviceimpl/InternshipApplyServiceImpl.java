package com.hirekarma.serviceimpl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.InternshipApplyBean;
import com.hirekarma.beans.UniversitySharedJobList;
import com.hirekarma.exception.InternshipApplyException;
import com.hirekarma.exception.UniversityException;
import com.hirekarma.model.InternshipApply;
import com.hirekarma.model.Student;
import com.hirekarma.repository.InternshipApplyRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.service.InternshipApplyService;

@Service("internshipApplyServiceImpl")
public class InternshipApplyServiceImpl implements InternshipApplyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InternshipApplyServiceImpl.class);

	@Autowired
	private InternshipApplyRepository internshipApplyRepository;

	@Autowired
	private StudentRepository studentRepository;

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

		try {
			LOGGER.debug("Inside try block of InternshipApplyServiceImpl.insert()");
			if (studentList != null && studentList.size() >= 1) {

				internshipApply = new InternshipApply();
				BeanUtils.copyProperties(internshipApplyBean, internshipApply);
				internshipApply.setDeleteStatus(false);
				internshipApply.setApplicatinStatus(false);
				internshipApply.setStudentId(studentList.get(0).getStudentId());
				internshipApply.setCorporateId(internshipApplyBean.getCorporateId());
				
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

}
