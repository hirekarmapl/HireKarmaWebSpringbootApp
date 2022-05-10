package com.hirekarma.beans;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

public class NoticeBean {

	int id;
	

	
	String imageUrl;
	
	Long universityId;
	
	Long corporateId;
//	
	String link;
//	
	String body;
	
	String keywords;
	
	String deadLineString;
	
	
	
	MultipartFile file;
	

	public String getDeadLineString() {
		return deadLineString;
	}

	public void setDeadLineString(String deadLineString) {
		this.deadLineString = deadLineString;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getUniversityId() {
		return universityId;
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}



	public NoticeBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
