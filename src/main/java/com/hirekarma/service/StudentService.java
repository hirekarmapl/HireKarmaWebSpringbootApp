package com.hirekarma.service;

import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.EducationBean;
import com.hirekarma.beans.StudentMentorBooking;
import com.hirekarma.beans.UniversityJobShareToStudentBean;
import com.hirekarma.beans.UserBean;
import com.hirekarma.beans.UserBeanResponse;
import com.hirekarma.model.Education;
import com.hirekarma.model.Experience;
import com.hirekarma.model.Skill;
import com.hirekarma.model.Stream;
import com.hirekarma.model.Student;
import com.hirekarma.model.UserProfile;

public interface StudentService {
//	public Student insert(Student student);
//	public StudentBean checkLoginCredentials(String email,String password);
//	public StudentBean updateStudentProfile(StudentBean studentBean);
//	public StudentBean findStudentById(Long studentId);
	
	public UserProfile insert(UserProfile student);
//	public UserBean updateStudentProfile(UserBean studentBean, String token) throws Exception;
	public UserBean findStudentById(Long studentId);
	public List<UserBean> getAllStudents(String token) throws Exception;
	public Map<String,Object> importStudentDataExcel(MultipartFile file,String token) throws Exception;
	public UniversityJobShareToStudentBean studentJobResponse(UniversityJobShareToStudentBean jobBean,String token) throws Exception;
	public List<?> jobDetails(String token) throws ParseException;
	public Boolean updateSkills(List<Skill> skillIds,String token) throws Exception;
	
	public List<Skill> getAllSkillsOfStudent(String token) throws Exception;
	
	public List<Experience> getAllExperiencesOfStudent(String token) throws Exception;
	public List<Education> getAllEducationsOfStudent(String token) throws Exception;
	public Map<String,Object> getAllJobApplicationsByStudent(Long studentId);
	
	
	
//	this is better verison of above function
	public UserProfile insert2(String email,String password,String name) throws Exception;
	public UserBeanResponse updateStudentProfile2(UserBean userBean, String token) throws Exception;
	public Map<String,Object> addSkillToAStudent(Skill skill, String token) throws Exception;
	public Map<String,Object> deleteSkillOfAStudent(int id,String token) throws Exception;
	public Education addEducationToAStudent(EducationBean educationBean,String token) throws Exception;
	public void deleteEducationOfAStudentbyId(String token,int id) throws Exception;
	public Map<String, Object> addAllSkillsToStudent(List<Skill> skills,String token) throws Exception;
	List<Education> addAllEducationToStudent(List<EducationBean> educationBeans, String token) throws Exception;
	public List<Experience> addAllExperienceToStudent(List<Experience> experience,String token) throws Exception;
	Experience addExperienceToAStudent(Experience experience, String token) throws Exception;
	public void deleteExperienceOfAStudent(int id,String token) throws Exception;
	List<Student> getAllStudentsAccoridngToBranchBatchCgpaFilter(Long branchId, Long batchId, Double cgpa,Long universityId);
	List<Student> getAllStudentsAccoridngToStreamBranchBatchCgpaFilter(Stream stream, Long branchId, Long batchId,
			Double cgpa, Long universityId);
	double getProfileUpdateStatusForStudentByStudentAndUserProfile(Student student, UserProfile studentUserProfile);
	Map<String,Object> bookAMentorSlot(StudentMentorBooking studentMentorBooking,Student student) throws Exception;
	Map<String,Object> getMentorAvailableSession(StudentMentorBooking studentMentorBooking,Student student) throws Exception;
	List<Student> FilterStudents(Stream stream, Long branchId, Long batchId, Double cgpa, Long universityId,
			String studentName, Long studentPhoneNumber);
	UserBeanResponse updateStudentProfileByUniversity(UserBean userBean, String token) throws Exception;
}
