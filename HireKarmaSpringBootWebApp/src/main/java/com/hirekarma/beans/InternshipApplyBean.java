package com.hirekarma.beans;

public class InternshipApplyBean {
	
	private Long internshipApplyId;
	private Long studentId;
	private String hireReason;
	private String coverLetter;
	private String earliestJoiningDate;
	
	public Long getInternshipApplyId() {
		return internshipApplyId;
	}
	public void setInternshipApplyId(Long internshipApplyId) {
		this.internshipApplyId = internshipApplyId;
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
		return "InternshipApplyBean [internshipApplyId=" + internshipApplyId + ", studentId=" + studentId
				+ ", hireReason=" + hireReason + ", coverLetter=" + coverLetter + ", earliestJoiningDate="
				+ earliestJoiningDate + "]";
	}
}
