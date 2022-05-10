package com.hirekarma.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.NoticeBean;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Notice;
import com.hirekarma.model.University;

@Service
public interface NoticeService {
	public Map<String,Object> addNoticeByAdmin(NoticeBean noticeBean,MultipartFile file) throws IOException;
	public Map<String, Object> addNoticeByCorporate(NoticeBean noticeBean,MultipartFile file,Corporate corporate) throws IOException;
	public Map<String, Object> addNoticeByUniversity(NoticeBean noticeBean,MultipartFile file,University university) throws IOException;
	
	public List<Notice> getAllNoticeByAdmin();
	List<Notice> getAllNoticeByCorporate(Corporate corporate);
	List<Notice> getAllNoticeByUniversity(University university);
	
	public Notice getNoticeByAdminBySlug(String slug);
	
	public void deleteNoticeByAdmin(String slug) throws Exception;
	public void deleteNoticeByUniversity(String slug) throws Exception;
	void deleteNoticeByCorporate(String slug) throws Exception;
	public Notice updateNoticeByAdminBySlug(String slug, NoticeBean noticeBean, MultipartFile file) throws IOException, Exception ;
	Notice updateNoticeByCorporateBySlug(String slug, NoticeBean noticeBean, MultipartFile file,Corporate corporate) throws Exception;
	Notice updateNoticeByUniversityBySlug(String slug, NoticeBean noticeBean, MultipartFile file,University university) throws Exception;
}
