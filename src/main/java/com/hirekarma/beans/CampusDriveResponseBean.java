package com.hirekarma.beans;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CampusDriveResponseBean {

	private Long campusDriveId;
	
	private Long jobId;
	
	private Long corporateId;
	
	private Long universityId;
	
	private Boolean universityAsk;
	
	private Boolean corporateResponse;
	
	private Timestamp universityAskedOn;
	
	private Timestamp corporateResponseOn;
}
