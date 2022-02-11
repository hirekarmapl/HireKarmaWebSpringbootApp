package com.hirekarma.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

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
//@Data
//@ToString
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

			@Override
			public String toString() {
				return "University [universityId=" + universityId + ", userId=" + userId + ", universityName="
						+ universityName + ", universityEmail=" + universityEmail + ", universityAddress="
						+ universityAddress + ", universityPhoneNumber=" + universityPhoneNumber + ", universityImage="
						+ Arrays.toString(universityImage) + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn
						+ ", status=" + status + "]";
			}

			public University() {
				super();
				// TODO Auto-generated constructor stub
			}

			public Long getUniversityId() {
				return universityId;
			}

			public void setUniversityId(Long universityId) {
				this.universityId = universityId;
			}

			public Long getUserId() {
				return userId;
			}

			public void setUserId(Long userId) {
				this.userId = userId;
			}

			public String getUniversityName() {
				return universityName;
			}

			public void setUniversityName(String universityName) {
				this.universityName = universityName;
			}

			public String getUniversityEmail() {
				return universityEmail;
			}

			public void setUniversityEmail(String universityEmail) {
				this.universityEmail = universityEmail;
			}

			public String getUniversityAddress() {
				return universityAddress;
			}

			public void setUniversityAddress(String universityAddress) {
				this.universityAddress = universityAddress;
			}

			public Long getUniversityPhoneNumber() {
				return universityPhoneNumber;
			}

			public void setUniversityPhoneNumber(Long universityPhoneNumber) {
				this.universityPhoneNumber = universityPhoneNumber;
			}

			public byte[] getUniversityImage() {
				return universityImage;
			}

			public void setUniversityImage(byte[] universityImage) {
				this.universityImage = universityImage;
			}

			public Timestamp getCreatedOn() {
				return createdOn;
			}

			public void setCreatedOn(Timestamp createdOn) {
				this.createdOn = createdOn;
			}

			public Timestamp getUpdatedOn() {
				return updatedOn;
			}

			public void setUpdatedOn(Timestamp updatedOn) {
				this.updatedOn = updatedOn;
			}

			public Boolean getStatus() {
				return status;
			}

			public void setStatus(Boolean status) {
				this.status = status;
			}

			public static long getSerialversionuid() {
				return serialVersionUID;
			}
			
			
			
			

}
