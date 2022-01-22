package com.hirekarma.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "CAMPUS_DRIVE_RESPONSE")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CampusDriveResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CAMPUS_DRIVE_ID")
	@NotNull
	private Long campusDriveId;

	@Column(name = "JOB_ID")
	private Long jobId;

	@Column(name = "CORPORATE_ID")
	private Long corporateId;

	@Column(name = "UNIVERSITY_ID")
	private Long universityId;

	@Column(name = "UNIVERSITY_ASK_FOR_CAMPUS")
	private Boolean universityAsk;

	@Column(name = "CORPORATE_RESPONSE")
	private Boolean corporateResponse;

	@Column(name = "UNIVERSITY_ASKED_ON")
	@CreationTimestamp
	private Timestamp universityAskedOn;

	@Column(name = "CORPORATE_RESPONSE_ON")
	private Timestamp corporateResponseOn;

}
