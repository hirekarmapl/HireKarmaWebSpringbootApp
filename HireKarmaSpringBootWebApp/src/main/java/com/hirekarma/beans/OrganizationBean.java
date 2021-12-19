package com.hirekarma.beans;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class OrganizationBean {
	
	private String orgName;
	private String orgEmail;
	private String cinGstNum;
	private byte[] logo;
	private String description;
	private Long corpUserId;
	private MultipartFile file;
	
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
	public Long getCorpUserId() {
		return corpUserId;
	}
	public void setCorpUserId(Long corpUserId) {
		this.corpUserId = corpUserId;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	@Override
	public String toString() {
		return "OrganizationBean [orgName=" + orgName + ", orgEmail=" + orgEmail + ", cinGstNum=" + cinGstNum
				+ ", logo=" + Arrays.toString(logo) + ", description=" + description + ", corpUserId=" + corpUserId
				+ ", file=" + file + "]";
	}
}
