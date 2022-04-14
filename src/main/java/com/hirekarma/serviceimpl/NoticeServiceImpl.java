package com.hirekarma.serviceimpl;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.NoticeBean;
import com.hirekarma.model.Notice;
import com.hirekarma.repository.NoticeRepository;
import com.hirekarma.service.NoticeService;
import com.hirekarma.utilty.Utility;

@Service("NoticeService")
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	NoticeRepository noticeRepository;
	
	@Autowired
	AWSS3Service awss3Service;

	public Notice addNoticeByAdmin(NoticeBean noticeBean,MultipartFile file) throws IOException {
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
		
		return this.noticeRepository.save(notice);
	}
	
	public void deleteNoticeByAdmin(String slug) {
		Notice notice = noticeRepository.findBySlug(slug);
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
	public Notice updateNoticeByAdminBySlug(String slug,NoticeBean noticeBean,MultipartFile file) throws IOException {
		Notice notice = noticeRepository.findBySlug(slug);
		BeanUtils.copyProperties(noticeBean, notice);
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
}
