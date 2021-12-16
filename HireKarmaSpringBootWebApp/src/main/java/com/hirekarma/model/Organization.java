package com.hirekarma.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

@Entity
@Table(name = "ORGANIZATION")
public class Organization implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "ORGANIZATION_ID")
	private Long organizationId;
	
	@Column(name = "CORP_USER_ID")
	private Long corpUserId;
	
	@Column(name = "ORG_NAME")
	private String orgName;
	
	@Column(name = "ORG_EMAIL")
	private String orgEmail;
	
	@Column(name = "CIN_GST_NUM")
	private String cinGstNum;
	
	@Column(name = "LOGO")
	private byte[] logo;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;
	
	@Column(name = "STATUS")
	private String status;

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getCorpUserId() {
		return corpUserId;
	}

	public void setCorpUserId(Long corpUserId) {
		this.corpUserId = corpUserId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Organization [organizationId=" + organizationId + ", corpUserId=" + corpUserId + ", orgName=" + orgName
				+ ", orgEmail=" + orgEmail + ", cinGstNum=" + cinGstNum + ", logo=" + Arrays.toString(logo)
				+ ", description=" + description + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", status="
				+ status + "]";
	}
}
