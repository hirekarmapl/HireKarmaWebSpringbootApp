package com.hirekarma.beans;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JobBean {

	private Long jobId;

	private Long corporateId;

	private String jobTitle;

	private String category;

	private String jobType;

	private Boolean wfhCheckbox;

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

	private String response;

}
