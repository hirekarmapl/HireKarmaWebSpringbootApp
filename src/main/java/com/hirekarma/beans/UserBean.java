package com.hirekarma.beans;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long userId;
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
	private Double cgpa;
	private Long batch;
	private Long universityId;
}
