package com.hirekarma.beans;

import java.sql.Timestamp;
import java.util.Arrays;

public class CampusDriveInviteBean {
	
	private Long campusDriveId;	
	private Long jobId;
	private Long corporateId;
	private Long universityId;
	private Boolean universityAsk;
	private Boolean corporateResponse;
	private Timestamp universityAskedOn;
	private Timestamp corporateResponseOn;
	private Long userId;
	private String universityName;
	private String universityEmail;
	private String universityAddress;
	private Long universityPhoneNumber;
	private byte[] universityImage;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	private Boolean status;
	
	public Long getCampusDriveId() {
		return campusDriveId;
	}
	public void setCampusDriveId(Long campusDriveId) {
		this.campusDriveId = campusDriveId;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public Long getCorporateId() {
		return corporateId;
	}
	public void setCorporateId(Long corporateId) {
		this.corporateId = corporateId;
	}
	public Long getUniversityId() {
		return universityId;
	}
	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}
	public Boolean getUniversityAsk() {
		return universityAsk;
	}
	public void setUniversityAsk(Boolean universityAsk) {
		this.universityAsk = universityAsk;
	}
	public Boolean getCorporateResponse() {
		return corporateResponse;
	}
	public void setCorporateResponse(Boolean corporateResponse) {
		this.corporateResponse = corporateResponse;
	}
	public Timestamp getUniversityAskedOn() {
		return universityAskedOn;
	}
	public void setUniversityAskedOn(Timestamp universityAskedOn) {
		this.universityAskedOn = universityAskedOn;
	}
	public Timestamp getCorporateResponseOn() {
		return corporateResponseOn;
	}
	public void setCorporateResponseOn(Timestamp corporateResponseOn) {
		this.corporateResponseOn = corporateResponseOn;
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
	
	@Override
	public String toString() {
		return "CampusDriveInviteBean [campusDriveId=" + campusDriveId + ", jobId=" + jobId + ", corporateId="
				+ corporateId + ", universityId=" + universityId + ", universityAsk=" + universityAsk
				+ ", corporateResponse=" + corporateResponse + ", universityAskedOn=" + universityAskedOn
				+ ", corporateResponseOn=" + corporateResponseOn + ", userId=" + userId + ", universityName="
				+ universityName + ", universityEmail=" + universityEmail + ", universityAddress=" + universityAddress
				+ ", universityPhoneNumber=" + universityPhoneNumber + ", universityImage="
				+ Arrays.toString(universityImage) + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn
				+ ", status=" + status + "]";
	}
}
