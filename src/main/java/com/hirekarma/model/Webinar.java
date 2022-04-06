package com.hirekarma.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class Webinar {

	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)")
	@Id
	String id;
	
	String title;
	
	String description;
	
	LocalDateTime scheduledAt;
	
	String meetLink;
	
	@Column(nullable = false)
	Boolean isAccepted;
	
	@Column(nullable = false)
	Boolean isDisable;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getScheduledAt() {
		return scheduledAt;
	}

	public void setScheduledAt(LocalDateTime scheduledAt) {
		this.scheduledAt = scheduledAt;
	}

	public String getMeetLink() {
		return meetLink;
	}

	public void setMeetLink(String meetLink) {
		this.meetLink = meetLink;
	}

	public Boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public Boolean isDisable() {
		return isDisable;
	}

	public void setDisable(Boolean isDisable) {
		this.isDisable = isDisable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public Boolean getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(Boolean isDisable) {
		this.isDisable = isDisable;
	}

	public Webinar(String id, String title, String description, LocalDateTime scheduledAt, String meetLink,
			Boolean isAccepted, Boolean isDisable) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.scheduledAt = scheduledAt;
		this.meetLink = meetLink;
		this.isAccepted = isAccepted;
		this.isDisable = isDisable;
	}

	public Webinar() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
