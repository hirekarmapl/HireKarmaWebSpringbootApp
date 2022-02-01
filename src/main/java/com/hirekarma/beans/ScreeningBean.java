package com.hirekarma.beans;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScreeningBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long screeningId;
	private Long corporateId;
	private String questions[];
	private String status;
	private Timestamp createdOn;
	private Timestamp updatedOn;

}
