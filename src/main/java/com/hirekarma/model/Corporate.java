package com.hirekarma.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "TBL_CORPORATE")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Corporate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "CORPORATE_ID")
	private Long corporateId;
	
	@Column(name = "CORPORATE_NAME")
	private String corporateName;
	
	@Column(name = "CORPORATE_EMAIL")
	private String corporateEmail;
	
	@Column(name = "CORPORATE_PHONE_NUMBER")
	private Long corporatePhoneNumber;
	
	@Column(name = "CORPORATE_ADDRESS")
	private String corporateAddress;
	
	@Lob
    @Column(name = "PROFILE_IMAGE")
    private byte[] profileImage;
	
	@Column(name = "USER_TYPE")
	private String userType;
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;
	
	@Column(name = "STATUS")
	private String status;

	

}
