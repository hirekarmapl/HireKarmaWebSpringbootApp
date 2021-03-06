package com.hirekarma.beans;

import java.sql.Timestamp;
import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class OrganizationBean {
	
	private Long organizationId;
	private Long corporateId;
	private String orgName;
	private String orgEmail;
	private String cinGstNum;
	private byte[] logo;
	private String description;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	private Boolean status;
	private MultipartFile file;
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public Long getCorporateId() {
		return corporateId;
	}
	public void setCorporateId(Long corporateId) {
		this.corporateId = corporateId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgEmail() {
		return orgEmail;
	}
	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}
	public String getCinGstNum() {
		return cinGstNum;
	}
	public void setCinGstNum(String cinGstNum) {
		this.cinGstNum = cinGstNum;
	}
	public byte[] getLogo() {
		return logo;
	}
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "OrganizationBean [organizationId=" + organizationId + ", corporateId=" + corporateId + ", orgName="
				+ orgName + ", orgEmail=" + orgEmail + ", cinGstNum=" + cinGstNum + ", logo=" + Arrays.toString(logo)
				+ ", description=" + description + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", status="
				+ status + ", file=" + file + "]";
	}
	

}
