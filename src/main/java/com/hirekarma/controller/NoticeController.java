package com.hirekarma.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.util.DateTime;
import com.hirekarma.beans.BlogBean;
import com.hirekarma.beans.NoticeBean;
import com.hirekarma.model.Notice;
import com.hirekarma.repository.NoticeRepository;
import com.hirekarma.service.NoticeService;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/admin/")
public class NoticeController {
	
	@Autowired
	NoticeRepository noticeRepository;
	
	@Autowired
	NoticeService noticeService;
	
	@PreAuthorize("hasRole('admin')")
	@GetMapping("/notice/dummy")
	public Notice getDummy() {
		Notice notice = new Notice();
		
		return notice;
		
	}
	
	@PreAuthorize("hasRole('admin')")
	@RequestMapping(value = "/notice", method = RequestMethod.POST, consumes = "multipart/form-data")
	public Notice addNotice(@RequestPart("data") NoticeBean noticeBean, @RequestPart(value="file",required = false) MultipartFile file) {
		try {
			return this.noticeService.addNoticeByAdmin(noticeBean, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
	@PreAuthorize("hasRole('admin')")
	@DeleteMapping("/notice/{slug}")
	public void deleteNoticeByAdmin(@PathVariable("slug")String slug) {
		noticeService.deleteNoticeByAdmin(slug);
	}
	@PreAuthorize("hasRole('admin')")
	@GetMapping("/notice")
	public List<Notice> getAllNoticeByAdmin(){
		return this.noticeService.getAllNoticeByAdmin();
	}
	@PreAuthorize("hasRole('admin')")
	@GetMapping("/notice/{slug}")
	public Notice getNoticeByAdminBySlug(@PathVariable("slug")String slug) {
		return this.noticeService.getNoticeByAdminBySlug(slug);
	}
	
	@PreAuthorize("hasRole('admin')")
	@RequestMapping(value = "/notice/{slug}", method = RequestMethod.PUT, consumes = "multipart/form-data")
	public Notice updateNoticeByAdminBySlug(@PathVariable("slug")String slug,@RequestPart("data") NoticeBean noticeBean, @RequestPart("file") MultipartFile file) {
		try {
			return this.noticeService.updateNoticeByAdminBySlug(slug,noticeBean, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
}
