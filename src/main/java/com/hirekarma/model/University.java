package com.hirekarma.model;

import java.io.Serializable;
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

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "TBL_UNIVERSITY")
@Data
@ToString
public class University implements Serializable{
			
			private static final long serialVersionUID = 1L;

			@Id
			@GeneratedValue(strategy = GenerationType.AUTO)
			@NotNull
			@Column(name = "UNIVERSITY_ID")
			private Long universityId;
			
			@Column(name = "USER_ID")
			private Long userId;
			
			@Column(name = "University_name")
			private String universityName;
			
			@Column(name = "UNIVERSITY_EMAIL")
			private String universityEmail;
			
			@Column(name = "UNIVERSITY_ADDRESS")
			private String universityAddress;
			
			@Column(name = "UNIVERSITY_PHONE_NUMBER")
			private Long universityPhoneNumber;
			
			@Lob
			@Column(name = "UNIVERSITY_IMAGE")
			private byte[] universityImage;
			
			@CreationTimestamp
			@Column(name = "CREATED_ON")
			private Timestamp createdOn;
			
			@Column(name = "UPDATED_ON")
			private Timestamp updatedOn;

			@Column(name = "STATUS")
			private Boolean status;
			
			

}
