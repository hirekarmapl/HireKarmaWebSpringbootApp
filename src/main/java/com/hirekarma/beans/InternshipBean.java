package com.hirekarma.beans;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InternshipBean {
	
	private Long internshipId;
	private Long corporateId;
	private String internshipTitle;
	private String internshipType;
	private String skills;
	private String city;
	private Integer openings;
	private Double salary;
	private String about;
	private String description;
	private byte[] descriptionFile;
	private Boolean status;
	private Boolean deleteStatus;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	private MultipartFile file;
}
