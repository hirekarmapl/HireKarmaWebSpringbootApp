package com.hirekarma.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.google.api.client.util.DateTime;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Notice {

	//	
	String body;
	LocalDateTime createdOn = LocalDateTime.now();
	
	LocalDateTime deadLine ;

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	int id;

String imageUrl;
String keywords;

	//	
	String link;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)", unique = true)
	String slug;

	Long universityId;

	LocalDateTime updatedOn = LocalDateTime.now();
	
	

	public LocalDateTime getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(LocalDateTime deadLine) {
		this.deadLine = deadLine;
	}

	public Notice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getBody() {
		return body;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	
//
//	public int getId() {
//		return id;
//	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getKeywords() {
		return keywords;
	}

	public String getLink() {
		return link;
	}

	public String getSlug() {
		return slug;
	}

	public Long getUniversityId() {
		return universityId;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	
//	public void setId(int id) {
//		this.id = id;
//	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

}
