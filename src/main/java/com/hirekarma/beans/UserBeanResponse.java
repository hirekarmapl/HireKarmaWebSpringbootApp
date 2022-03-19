package com.hirekarma.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.model.Skill;
import com.hirekarma.model.Stream;
import com.hirekarma.model.StudentBatch;
import com.hirekarma.model.StudentBranch;
import com.hirekarma.model.University;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserBeanResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long userId;
	private String imageUrl;
	private String name;

	private String email;
	private String universityEmailAddress;
	private String phoneNo;
	private byte[] image;
	private String userType;

	private String password;
	private String address;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	private MultipartFile file;
	private Long branch;
	private Long batch;
	private String studentBatchName;
	private String studentBranchName;
	private Long universityId;
	private String universityName;
	private Boolean profileUpdateStatus;
	private String websiteUrl;
	private Stream stream;
	private Double cgpa;
	private University university;
	private List<Skill> skills = new ArrayList<Skill>();
	private String about;
}
