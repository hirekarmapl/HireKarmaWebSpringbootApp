package com.hirekarma.serviceimpl;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.NoticeBean;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Notice;
import com.hirekarma.model.University;
import com.hirekarma.repository.NoticeRepository;
import com.hirekarma.service.NoticeService;
import com.hirekarma.utilty.Utility;

@Service("NoticeService")
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	NoticeRepository noticeRepository;
	
	@Autowired
	AWSS3Service awss3Service;

	public Map<String,Object> addNoticeByAdmin(NoticeBean noticeBean,MultipartFile file) throws IOException {
		Map<String, Object> response = new HashMap<>();
		Notice notice = new Notice();
		BeanUtils.copyProperties(noticeBean, notice);
		
		if(file!=null && !file.isEmpty()) {
			notice.setImageUrl(awss3Service.uploadFile(file));
		}
		if(noticeBean.getDeadLineString()!=null && !noticeBean.getDeadLineString().equals("")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			LocalDateTime deadLine = LocalDateTime.parse(noticeBean.getDeadLineString(), formatter);
			notice.setDeadLine(deadLine);
		}
		response.put("notice", this.noticeRepository.save(notice));
		return response;
	}
	
	public Map<String, Object> addNoticeByCorporate(NoticeBean noticeBean,MultipartFile file,Corporate corporate) throws IOException {
		Map<String, Object> response = new HashMap<>();
		Notice notice = new Notice();
		BeanUtils.copyProperties(noticeBean, notice);
		notice.setCorporateId(corporate.getCorporateId());
		if(file!=null && !file.isEmpty()) {
			notice.setImageUrl(awss3Service.uploadFile(file));
		}
		if(noticeBean.getDeadLineString()!=null && !noticeBean.getDeadLineString().equals("")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			LocalDateTime deadLine = LocalDateTime.parse(noticeBean.getDeadLineString(), formatter);
			notice.setDeadLine(deadLine);
		}
		response.put("notice", this.noticeRepository.save(notice));
		return response;
	}
	
	@Override
	public Map<String, Object> addNoticeByUniversity(NoticeBean noticeBean,MultipartFile file,University university) throws IOException {
		Map<String, Object> response = new HashMap<>();
		Notice notice = new Notice();
		BeanUtils.copyProperties(noticeBean, notice);
		notice.setUniversityId(university.getUniversityId());
		if(file!=null && !file.isEmpty()) {
			notice.setImageUrl(awss3Service.uploadFile(file));
		}
		if(noticeBean.getDeadLineString()!=null && !noticeBean.getDeadLineString().equals("")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			LocalDateTime deadLine = LocalDateTime.parse(noticeBean.getDeadLineString(), formatter);
			notice.setDeadLine(deadLine);
		}
		response.put("notice", this.noticeRepository.save(notice));
		return response;
	}
	public void deleteNoticeByAdmin(String slug) throws Exception {
		Optional<Notice> noticeOptional = Optional.ofNullable(noticeRepository.findBySlug(slug));
		if(!noticeOptional.isPresent()) {
			throw new Exception("no such slug found");
		}
		Notice notice = noticeOptional.get();
		
		if(notice.getUniversityId()!=null || notice.getCorporateId()!=null ) {
			throw new Exception("unauthorized");
		}
		this.noticeRepository.delete(notice);
	}

	@Override
	public void deleteNoticeByCorporate(String slug) throws Exception {
		Optional<Notice> noticeOptional = Optional.ofNullable(noticeRepository.findBySlug(slug));
		if(!noticeOptional.isPresent()) {
			throw new Exception("no such slug found");
		}
		Notice notice = noticeOptional.get();
		
		if(notice.getCorporateId()==null) {
			throw new Exception("unauthorized");
		}
		this.noticeRepository.delete(notice);
	}	
	@Override
	public void deleteNoticeByUniversity(String slug) throws Exception {
		Optional<Notice> noticeOptional = Optional.ofNullable(noticeRepository.findBySlug(slug));
		if(!noticeOptional.isPresent()) {
			throw new Exception("no such slug found");
		}
		Notice notice = noticeOptional.get();
		
		if(notice.getUniversityId()==null) {
			throw new Exception("unauthorized");
		}
		this.noticeRepository.delete(notice);
	}
	@Override
	public List<Notice> getAllNoticeByAdmin() {
		return noticeRepository.findAll();
	}

	@Override
	public Notice getNoticeByAdminBySlug(String slug) {
		return noticeRepository.findBySlug(slug);
	}


	@Override
	public Notice updateNoticeByAdminBySlug(String slug,NoticeBean noticeBean,MultipartFile file) throws Exception {
		Optional<Notice> noticeOptional = Optional.ofNullable(noticeRepository.findBySlug(slug));
		if(!noticeOptional.isPresent()) {
			throw new Exception("invalid slug");
		}
		Notice notice = noticeOptional.get();

		if(notice.getCorporateId()!=null || notice.getUniversityId()!=null) {
			throw new Exception("unauthorized");
		}
		notice = updateNoticeForBeanNotNull(notice, noticeBean);
		notice.setUpdatedOn(LocalDateTime.now());
		if(noticeBean.getDeadLineString()!=null && !noticeBean.getDeadLineString().equals("")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			LocalDateTime deadLine = LocalDateTime.parse(noticeBean.getDeadLineString(), formatter);
			notice.setDeadLine(deadLine);
		}
		if(file!=null && !file.isEmpty()) {
			notice.setImageUrl(awss3Service.uploadFile(file));
		}
		return this.noticeRepository.save(notice);
	}
	@Override
	public Notice updateNoticeByCorporateBySlug(String slug,NoticeBean noticeBean,MultipartFile file,Corporate corporate) throws Exception {
		Optional<Notice> noticeOptional = Optional.ofNullable(noticeRepository.findBySlug(slug));
		if(!noticeOptional.isPresent()) {
			throw new Exception("invalid slug");
		}
		Notice notice = noticeOptional.get();
		if(notice.getCorporateId()==null) {
			throw new Exception("unauthorized");
		}
		notice = updateNoticeForBeanNotNull(notice, noticeBean);
		notice.setUpdatedOn(LocalDateTime.now());
		if(noticeBean.getDeadLineString()!=null && !noticeBean.getDeadLineString().equals("")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			LocalDateTime deadLine = LocalDateTime.parse(noticeBean.getDeadLineString(), formatter);
			notice.setDeadLine(deadLine);
		}
		if(file!=null && !file.isEmpty()) {
			notice.setImageUrl(awss3Service.uploadFile(file));
		}
		return this.noticeRepository.save(notice);
	}

	Notice updateNoticeForBeanNotNull(Notice notice, NoticeBean noticeBean){
		if(noticeBean.getBody()!=null && !noticeBean.getBody().equals("")) {
			notice.setBody(noticeBean.getBody());
		}
		if(noticeBean.getKeywords()!=null && !noticeBean.getKeywords().equals("")) {
			notice.setKeywords(noticeBean.getKeywords());
		}
		if(noticeBean.getLink()!=null && !noticeBean.getLink().equals("")) {
			notice.setLink(noticeBean.getLink());
		}
		return notice;
	}
	@Override
	public Notice updateNoticeByUniversityBySlug(String slug,NoticeBean noticeBean,MultipartFile file,University university) throws Exception {
		Optional<Notice> noticeOptional = Optional.ofNullable(noticeRepository.findBySlug(slug));
		if(!noticeOptional.isPresent()) {
			throw new Exception("invalid slug");
		}
		Notice notice = noticeOptional.get();
		if(notice.getUniversityId()==null || notice.getUniversityId().compareTo(university.getUniversityId())!=0) {
			throw new Exception("unauthorized");
		}
		notice = updateNoticeForBeanNotNull(notice, noticeBean);
		notice.setUpdatedOn(LocalDateTime.now());
		if(noticeBean.getDeadLineString()!=null && !noticeBean.getDeadLineString().equals("")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			LocalDateTime deadLine = LocalDateTime.parse(noticeBean.getDeadLineString(), formatter);
			notice.setDeadLine(deadLine);
		}
		if(file!=null && !file.isEmpty()) {
			notice.setImageUrl(awss3Service.uploadFile(file));
		}
		return this.noticeRepository.save(notice);
	}
	@Override
	public List<Notice> getAllNoticeByCorporate(Corporate corporate) {
		return this.noticeRepository.findByCorporateId(corporate.getCorporateId(), LocalDateTime.now());
	}

	@Override
	public List<Notice> getAllNoticeByUniversity(University university) {
		// TODO Auto-generated method stub
		return this.noticeRepository.findByUniversityId(university.getUniversityId(), LocalDateTime.now());
	}
}
