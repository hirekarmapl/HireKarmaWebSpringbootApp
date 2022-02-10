package com.hirekarma.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.model.Skill;
import com.sun.istack.NotNull;

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
	@NotBlank
	@NotNull
	private String name;
	@NotBlank
	@NotNull
	@Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
	        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
	private String email;
	private String universityEmailAddress;
	private String phoneNo;
	private byte[] image;
	private String userType;
	@NotBlank
	@NotNull
	@Size(min = 5,max=13,message="Password should have atleast min 5 letters and max 5 letters")
	private String password;
	private String address;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	private MultipartFile file;
	private Long branch;
	private Double cgpa;
	private Long batch;
	private Long universityId;
	private List<Skill> skills = new ArrayList<Skill>();
	
}
