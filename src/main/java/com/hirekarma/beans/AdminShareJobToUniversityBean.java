package com.hirekarma.beans;

import java.sql.Timestamp;
import java.util.List;

import org.json.simple.JSONObject;




public class AdminShareJobToUniversityBean {
	
	private Long shareJobId;
	
	private Long jobId;
	
	private List<Long> universityId;
	
	private String jobStatus;
	
	private Boolean universityResponseStatus;
	
	private String rejectionFeedback;
	
	private Timestamp createdOn;
	
	private String createdBy;
	
	private Timestamp updatedOn;
	
	private String updatedBy;
	
	private String response;
	
	private Long toatlSharedJob;
	
	private JSONObject jsonObject;

	public Long getShareJobId() {
		return shareJobId;
	}

	public void setShareJobId(Long shareJobId) {
		this.shareJobId = shareJobId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public List<Long> getUniversityId() {
		return universityId;
	}

	public void setUniversityId(List<Long> universityId) {
		this.universityId = universityId;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public Boolean getUniversityResponseStatus() {
		return universityResponseStatus;
	}

	public void setUniversityResponseStatus(Boolean universityResponseStatus) {
		this.universityResponseStatus = universityResponseStatus;
	}

	public String getRejectionFeedback() {
		return rejectionFeedback;
	}

	public void setRejectionFeedback(String rejectionFeedback) {
		this.rejectionFeedback = rejectionFeedback;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Long getToatlSharedJob() {
		return toatlSharedJob;
	}

	public void setToatlSharedJob(Long toatlSharedJob) {
		this.toatlSharedJob = toatlSharedJob;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public AdminShareJobToUniversityBean(Long shareJobId, Long jobId, List<Long> universityId, String jobStatus,
			Boolean universityResponseStatus, String rejectionFeedback, Timestamp createdOn, String createdBy,
			Timestamp updatedOn, String updatedBy, String response, Long toatlSharedJob, JSONObject jsonObject) {
		super();
		this.shareJobId = shareJobId;
		this.jobId = jobId;
		this.universityId = universityId;
		this.jobStatus = jobStatus;
		this.universityResponseStatus = universityResponseStatus;
		this.rejectionFeedback = rejectionFeedback;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
		this.response = response;
		this.toatlSharedJob = toatlSharedJob;
		this.jsonObject = jsonObject;
	}

	public AdminShareJobToUniversityBean() {
		super();
		// TODO Auto-generated constructor stub
	}


}
