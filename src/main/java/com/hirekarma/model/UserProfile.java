package com.hirekarma.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.hirekarma.beans.AuthenticationProvider;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.validation.constraints.Email;

@Entity
@Table(name = "USER_PROFILE")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "EMAIL")
	@Email(message = "Email is not valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
	private String email;

	@Column(name = "UNIVERSITY_EMAIL_ADDRESS")
	private String universityEmailAddress;

	@Column(name = "PHONE_NO")
	private String phoneNo;

	@Lob
	@Column(name = "IMAGE")
	private byte[] image;

	@Column(name = "USER_TYPE")
	private String userType;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "UNIVERSITY_ADDRESS")
	private String address;

	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;

	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "share_job_id")
	private Long shareJobId;

	@Transient
	private String response;

	@Enumerated(EnumType.STRING)
	@Column(name = "AUTH_PROVIDER")
	private AuthenticationProvider authProvider;

}