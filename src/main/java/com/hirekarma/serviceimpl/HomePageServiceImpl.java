package com.hirekarma.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.HomePageBean;
import com.hirekarma.model.HomePage;
import com.hirekarma.repository.CampusDriveResponseRepository;
import com.hirekarma.repository.HomePageRepository;
import com.hirekarma.repository.JobApplyRepository;
import com.hirekarma.repository.JobRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.repository.UniversityJobShareRepository;
import com.hirekarma.service.HomePageService;
@Service("HomePageService")
public class HomePageServiceImpl implements HomePageService {

	@Autowired
	HomePageRepository homePageRepository;
	
	@Autowired
	CampusDriveResponseRepository campusDriveResponseRepository;
	
	@Autowired
	JobApplyRepository jobApplyRepository;
	
	@Autowired
	UniversityJobShareRepository universityJobShareRepository;
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	JobRepository jobRepository;
	
	
	@Override
	public Map<String, Object> add(HomePageBean homePageBean) throws Exception {
		Map<String,Object> result = new HashMap<String, Object>();
		System.out.println(homePageBean);
		if(homePageBean.getData()==null||homePageBean.getXpath()==null||homePageBean.getUrl()==null||homePageBean.getData().equals("")||homePageBean.getXpath().equals("")||homePageBean.getUrl().equals("")) {
			throw new Exception("all the fields are mandatory");			
		}
		
		HomePage homePage = homePageRepository.findByUrlAndXpath(homePageBean.getUrl(), homePageBean.getXpath());
		
		if(homePage==null) {
			HomePage homePageSaved = new HomePage();
			BeanUtils.copyProperties(homePageBean, homePageSaved);
			this.homePageRepository.save(homePageSaved);
		}else {
			homePage.setData(homePageBean.getData());
			this.homePageRepository.save(homePage);
		}
		return result;
	}
	@Override
	public Map<String, Object> noOfJobPosted() {
		Map<String,Object> response = new HashMap<String,Object>();
		response.put("noOfJobPosted", jobRepository.count());
		return response;
	}
	@Override
	public Map<String, Object> noOfJobApplications() {
		Map<String,Object> response = new HashMap<String,Object>();
		response.put("noOfJobPosted", jobApplyRepository.count());
		return response;
	}
	@Override
	public Map<String, Object> noOfStudents() {
		Map<String,Object> response = new HashMap<String,Object>();
		response.put("noOfStudents", studentRepository.count());
		return response;
	}

	@Override
	public Map<String,Object> noOfStudentsHired(){
		Long jobApplyHiredCount= jobApplyRepository.countByIsHire(true);
		Long campusHiredCount = universityJobShareRepository.countByIsHire(true);
		Map<String,Object> response = new HashMap<String,Object>();
		response.put("noOfStudentsHired", (jobApplyHiredCount+campusHiredCount));
		return response;
	}
	@Override
	public Map<String,Object> noOfOngoingDrive(){
		Long onGoingDrive = campusDriveResponseRepository.countByCorporateResponseTrue();
		Map<String,Object> response = new HashMap<String,Object>();
		response.put("nofOngoingDrive", (onGoingDrive));
		return response;
	}
}
