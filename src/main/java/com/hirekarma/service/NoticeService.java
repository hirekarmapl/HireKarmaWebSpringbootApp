package com.hirekarma.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.NoticeBean;
import com.hirekarma.model.Notice;

@Service
public interface NoticeService {
	public Notice addNoticeByAdmin(NoticeBean noticeBean,MultipartFile file) throws IOException;
	public void deleteNoticeByAdmin(String slug);
	public List<Notice> getAllNoticeByAdmin();
	public Notice getNoticeByAdminBySlug(String slug);
	public Notice updateNoticeByAdminBySlug(String slug, NoticeBean noticeBean, MultipartFile file) throws IOException ;
}
