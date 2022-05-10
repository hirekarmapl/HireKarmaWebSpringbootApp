package com.hirekarma.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.OnlineAssesmentResponseBean;
import com.hirekarma.beans.OnlineAssessmentBean;
import com.hirekarma.beans.Response;
import com.hirekarma.beans.StudentOnlineAssessmentAnswerRequestBean;
import com.hirekarma.model.Blog;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.OnlineAssessment;
import com.hirekarma.model.QuestionANdanswer;
import com.hirekarma.model.Student;
import com.hirekarma.model.StudentOnlineAssessment;
import com.hirekarma.model.University;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.OnlineAssessmentService;
import com.hirekarma.utilty.Validation;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/")
public class OnlineAssesmentController {

	@Autowired
	OnlineAssessmentService onlineAssessmentService;

	@Autowired
	UserRepository userRepository;

	@PreAuthorize("hasRole('corporate')")
	@GetMapping("/corporate/assessment/dummy")
	public OnlineAssessment dummy() {
		return new OnlineAssessment();
	}

	@PreAuthorize("hasRole('student')")
	@GetMapping("/student/assessment/questionaries/{slug}")
	public ResponseEntity<Response> getAllQNAForStudentOfOnlineAssessment(@RequestHeader("Authorization") String token,
			@PathVariable("slug") String studentOnlineAssessmentSlug) {
		try {
			return new ResponseEntity(
					new Response("success", HttpStatus.OK, "", this.onlineAssessmentService
							.getAllQNAForStudentForOnlineAssessment(token, studentOnlineAssessmentSlug), null),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('student')")
	@PostMapping("/student/assessment/submit/{slug}")
	public ResponseEntity<Response> submitAnswerForOnlineAssessmentByStudent(
			@RequestHeader("Authorization") String token, @PathVariable("slug") String studentOnlineAssessmentSlug,
			@RequestBody List<StudentOnlineAssessmentAnswerRequestBean> studentOnlineAssessmentAnswerRequestBeans) {
		try {
			
			this.onlineAssessmentService.submitAnswerForOnlineAssessmentByStudent(studentOnlineAssessmentSlug,
					studentOnlineAssessmentAnswerRequestBeans, token);

			return new ResponseEntity(new Response("success", HttpStatus.OK, "successfully added", "", null),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('corporate')")
	@GetMapping("/corporate/assessment/dummyBean")
	public OnlineAssessmentBean dummyBean() {
		return new OnlineAssessmentBean();
	}

//	get all assessement without qna
	@PreAuthorize("hasAnyRole('admin','corporate','university')")
	@GetMapping("/corporate/assessment")
	public ResponseEntity<Response> getOnlineAssesmentsQNA(@RequestHeader("Authorization") String token) {
		try {
			List<OnlineAssessmentBean> onlineAssessmentBeans = null;
			Map<String, Object> response = new HashMap<>();
			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate = (Corporate) userData.get(0)[1];
			University university = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if (userProfile.getUserType().equals("university")) {
				onlineAssessmentBeans = this.onlineAssessmentService
						.getOnlineAssesmentsAddedByUniversityWithoutQNA(university);
			} else if (userProfile.getUserType().equals("admin")) {
				onlineAssessmentBeans = this.onlineAssessmentService.getOnlineAssesmentsAddedByAdminWithoutQNA();
			} else if (userProfile.getUserType().equals("corporate")) {
				onlineAssessmentBeans = this.onlineAssessmentService
						.getOnlineAssesmentsAddedByCorporatedWithoutQNA(corporate);
			}
			return new ResponseEntity(new Response("success", HttpStatus.OK, "", onlineAssessmentBeans, null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

//	create assessment
	@PreAuthorize("hasAnyRole('admin','corporate','university')")
	@PostMapping("/corporate/assessment")
	public ResponseEntity<Response> addOnlineAssessment(@RequestBody OnlineAssessmentBean onlineAssessmentBean,
			@RequestHeader("Authorization") String token) {
		try {
			OnlineAssessment onlineAssessment = null;

			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate = (Corporate) userData.get(0)[1];
			University university = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if (userProfile.getUserType().equals("university")) {
				onlineAssessment = this.onlineAssessmentService.addOnlineAssessmentByUniversity(onlineAssessmentBean,
						university);
			} else if (userProfile.getUserType().equals("admin")) {
				onlineAssessment = this.onlineAssessmentService.addOnlineAssessmentByAdmin(onlineAssessmentBean);
			} else if (userProfile.getUserType().equals("corporate")) {
				onlineAssessment = this.onlineAssessmentService.addOnlineAssessmentByCorporate(onlineAssessmentBean,
						corporate);
			}
			return new ResponseEntity<Response>(
					new Response("success", 201, "added succesfully", onlineAssessment, null), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

//	add question to particular assessment
	@PreAuthorize("hasAnyRole('admin','corporate','university')")
	@PostMapping("/corporate/assessment/questionaries")
	public ResponseEntity<Response> addQuestionToOnlineAssesment(@RequestBody OnlineAssessmentBean onlineAssessmentBean,
			@RequestHeader("Authorization") String token) {
		try {
			OnlineAssessment onlineAssessment = null;
			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate = (Corporate) userData.get(0)[1];
			University university = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if (userProfile.getUserType().equals("university")) {
				onlineAssessment = this.onlineAssessmentService
						.addQuestionToOnlineAssesmentByUniversity(onlineAssessmentBean, university);
			} else if (userProfile.getUserType().equals("admin")) {
				onlineAssessment = this.onlineAssessmentService
						.addQuestionToOnlineAssesmentByAdmin(onlineAssessmentBean);
			} else if (userProfile.getUserType().equals("corporate")) {
				onlineAssessment = this.onlineAssessmentService
						.addQuestionToOnlineAssesmentByCorporate(onlineAssessmentBean, corporate);
			}
			return new ResponseEntity<Response>(
					new Response("success", 201, "added succesfully", onlineAssessment, null), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

//	send assessment to students
	@PreAuthorize("hasAnyRole('admin','corporate','university')")
	@PostMapping("/corporate/assessment/student")
	public ResponseEntity<Response> sendOnlineAssessmentToStudents(
			@RequestBody OnlineAssessmentBean onlineAssessmentBean) {
		try {
			List<StudentOnlineAssessment> studentOnlineAssessments = this.onlineAssessmentService
					.sendOnlineAssessmentToStudents(onlineAssessmentBean);

			return new ResponseEntity<Response>(new Response("success", 200, "", studentOnlineAssessments, null),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

//	student can view all assessment shared to him
	@PreAuthorize("hasRole('student')")
	@GetMapping("/student/assessment")
	public ResponseEntity<Response> getAllOnlineAssessmentForStudent(@RequestHeader("Authorization") String token) {
		try {
			List<OnlineAssesmentResponseBean> studentOnlineAssessments = this.onlineAssessmentService
					.getAllOnlineAssessmentForStudent(token);

			return new ResponseEntity<Response>(new Response("success", 200, "", studentOnlineAssessments, null),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

//	update online assessment
	@PreAuthorize("hasAnyRole('admin','corporate','university')")
	@PutMapping("/corporate/assessment/{slug}")
	public ResponseEntity<Response> updateOnlineAssessment(@RequestBody OnlineAssessmentBean onlineAssessmentBean,
			@RequestHeader("Authorization") String token, @PathVariable("slug") String slug) {
		try {
			OnlineAssessment onlineAssessment = null;
			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate = (Corporate) userData.get(0)[1];
			University university = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if (userProfile.getUserType().equals("university")) {
				onlineAssessment = this.onlineAssessmentService.updateOnlineAssessmentByUniversity(onlineAssessmentBean,
						university, slug);
			} else if (userProfile.getUserType().equals("admin")) {
				onlineAssessment = this.onlineAssessmentService.updateOnlineAssessmentByAdmin(onlineAssessmentBean,
						slug);
			} else if (userProfile.getUserType().equals("corporate")) {
				onlineAssessment = this.onlineAssessmentService.updateOnlineAssessmentByCorporate(onlineAssessmentBean,
						corporate, slug);
			}

			return new ResponseEntity<Response>(
					new Response("success", 201, "added succesfully", onlineAssessment, null), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasAnyRole('admin','corporate','university')")
	@GetMapping("/corporate/assessment/{slug}")
	public ResponseEntity<Response> getOnlineAssessmentBySlug(@RequestHeader("Authorization") String token,
			@PathVariable("slug") String slug) {
		try {
			OnlineAssessment onlineAssessment = null;
			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate = (Corporate) userData.get(0)[1];
			University university = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if (userProfile.getUserType().equals("university")) {
				onlineAssessment =  this.onlineAssessmentService.getUniversityOnlineAssessmentBySlug(university, slug);
			} else if (userProfile.getUserType().equals("admin")) {
				onlineAssessment =  this.onlineAssessmentService.getAdminOnlineAssessmentBySlug( slug);
			} else if (userProfile.getUserType().equals("corporate")) {
				onlineAssessment = this.onlineAssessmentService.getCorporateOnlineAssessmentBySlug(corporate, slug);
			}
			
			return new ResponseEntity<Response>(
					new Response("success", 201, "added succesfully", onlineAssessment, null), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

//	@PreAuthorize("hasRole('corporate')")
//	@PutMapping("/corporate/assessment/questionaries")
//	public ResponseEntity<Response> updateQuestionOfOnlineAssessmentByCorporate(
//			@RequestBody OnlineAssessmentBean onlineAssessmentBean, @RequestHeader("Authorization") String token) {
//		try {
//			OnlineAssessment onlineAssessment = this.onlineAssessmentService
//					.updateQuestionOfOnlineAssessmentByCorporate(onlineAssessmentBean.getOnlineAssessmentSlug(),
//							onlineAssessmentBean.getQuestions(), token);
//			System.out.println(onlineAssessment.toString());
//			for (QuestionANdanswer q : onlineAssessment.getQuestionANdanswers()) {
//				System.out.println(q.getType());
//			}
//			return new ResponseEntity<Response>(
//					new Response("success", 201, "added succesfully", onlineAssessment, null), HttpStatus.CREATED);
//
//		} catch (Exception e) {
//			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
//					HttpStatus.BAD_REQUEST);
//		}
//	}

	@PreAuthorize("hasAnyRole('admin','corporate','university')")
	@DeleteMapping("/corporate/assessment/{slug}")
	public ResponseEntity<Response> deleteAOnlineAssessmentBySlug(@RequestHeader("Authorization") String token,
			@PathVariable("slug") String slug) {
		try {
			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate = (Corporate) userData.get(0)[1];
			University university = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if (userProfile.getUserType().equals("university")) {
				this.onlineAssessmentService.deleteOnlineAssessmentBySlugAndUniversity(slug, university);
			} else if (userProfile.getUserType().equals("admin")) {
				this.onlineAssessmentService.deleteOnlineAssessmentBySlugAndAdmin(slug);
			} else if (userProfile.getUserType().equals("corporate")) {
				this.onlineAssessmentService.deleteOnlineAssessmentBySlugAndCorporate(slug, corporate);
			}
			this.onlineAssessmentService.deleteOnlineAssessmentBySlugAndToken(slug, token);
			return new ResponseEntity(new Response("success", HttpStatus.OK, "", null, null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, "", null, null),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('corporate')")
	@DeleteMapping("/corporate/assessment/questionaries")
	public ResponseEntity<Response> deleteQuestionofOnlineAssessmentByCorporate(
			@RequestBody OnlineAssessmentBean onlineAssessmentBean, @RequestHeader("Authorization") String token) {
		try {
			this.onlineAssessmentService.deleteQuestionofOnlineAssessment(onlineAssessmentBean, token);

			return new ResponseEntity<Response>(new Response("success", 200, "deleted succesfully", null, null),
					HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}


	@GetMapping("/public/assessment")
	public ResponseEntity<Response> getAllOnlineAssessmentForPublic(@RequestHeader("Authorization") String token) {
		try {
			List<OnlineAssesmentResponseBean> studentOnlineAssessments = this.onlineAssessmentService
					.getAllOnlineAssessmentForPublic();

			return new ResponseEntity<Response>(new Response("success", 200, "", studentOnlineAssessments, null),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/public/assessment/questionaries/{slug}")
	public ResponseEntity<Response> getAllQNAForPublicForAssessement(@RequestHeader("Authorization") String token,
			@PathVariable("slug") String onlineAssessmentSlug) {
		try {
			return new ResponseEntity(
					new Response("success", HttpStatus.OK, "",
							this.onlineAssessmentService.getAllQNAForPublicForAssessement(onlineAssessmentSlug), null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('student')")
	@PostMapping("/public/assessment/submit/{slug}")
	public ResponseEntity<Response> submitAnswerForPublicOnlineAssessment(
			@RequestHeader("Authorization") String token, @PathVariable("slug") String OnlineAssessmentSlug,
			@RequestBody List<StudentOnlineAssessmentAnswerRequestBean> studentOnlineAssessmentAnswerRequestBeans) {
		try {
			Map<String,Object> response = new HashMap<>();
			response = this.onlineAssessmentService.submitResponseForPublicAssessment(OnlineAssessmentSlug,
					studentOnlineAssessmentAnswerRequestBeans);

			return new ResponseEntity(new Response("success", HttpStatus.OK, "successfully added", response, null),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

}
