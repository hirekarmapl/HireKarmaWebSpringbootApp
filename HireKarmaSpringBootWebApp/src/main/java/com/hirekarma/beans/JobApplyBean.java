package com.hirekarma.beans;

public class JobApplyBean {
	
	private Long jobApplyId;
	private Long studentId;
	private String hireReason;
	private String coverLetter;
	private String earliestJoiningDate;
	
	public Long getJobApplyId() {
		return jobApplyId;
	}
	public void setJobApplyId(Long jobApplyId) {
		this.jobApplyId = jobApplyId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getHireReason() {
		return hireReason;
	}
	public void setHireReason(String hireReason) {
		this.hireReason = hireReason;
	}
	public String getCoverLetter() {
		return coverLetter;
	}
	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}
	public String getEarliestJoiningDate() {
		return earliestJoiningDate;
	}
	public void setEarliestJoiningDate(String earliestJoiningDate) {
		this.earliestJoiningDate = earliestJoiningDate;
	}
	
	@Override
	public String toString() {
		return "JobApplyBean [jobApplyId=" + jobApplyId + ", studentId=" + studentId + ", hireReason=" + hireReason
				+ ", coverLetter=" + coverLetter + ", earliestJoiningDate=" + earliestJoiningDate + "]";
	}
}
