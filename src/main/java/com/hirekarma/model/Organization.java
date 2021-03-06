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
@Table(name = "ORGANIZATION")
//@Data
//@ToString
public class Organization implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "ORGANIZATION_ID")
	private Long organizationId;
	
	@Column(name = "CORPORATE_ID")
	private Long corporateId;
	
	@Column(name = "ORG_NAME")
	private String orgName;
	
	@Column(name = "ORG_EMAIL")
	private String orgEmail;
	
	@Column(name = "CIN_GST_NUM")
	private String cinGstNum;
	
	@Lob
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
	private Boolean status;

	@Override
	public String toString() {
		return "Organization [organizationId=" + organizationId + ", corporateId=" + corporateId + ", orgName="
				+ orgName + ", orgEmail=" + orgEmail + ", cinGstNum=" + cinGstNum + ", logo=" + Arrays.toString(logo)
				+ ", description=" + description + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", status="
				+ status + "]";
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}
