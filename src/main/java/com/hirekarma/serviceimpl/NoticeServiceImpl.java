package com.hirekarma.serviceimpl;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

	public Notice addNoticeByAdmin(NoticeBean noticeBean,MultipartFile file) throws IOException {
		Notice notice = new Notice();
		BeanUtils.copyProperties(noticeBean, notice);
		byte[] fileArr = Utility.readFile(file);
		notice.setFeatureImage(fileArr);
		notice.setDeadLine(Timestamp.valueOf(noticeBean.getDeadline()));
		notice.setSlug(Utility.createSlug("n1t"));
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
		byte[] fileArr = Utility.readFile(file);
		notice.setFeatureImage(fileArr);
		notice.setDeadLine(Timestamp.valueOf(noticeBean.getDeadline()));
		return this.noticeRepository.save(notice);

		
		
	}
}
