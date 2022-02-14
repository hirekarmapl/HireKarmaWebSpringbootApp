package com.hirekarma.beans;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EmailBean {
	
	private String toEmail;
	
	private List<String> toListEmail ;
	
	private String attachment;
	
	private String body;
	
	private String subject;
	
	private String meetLink;
	
	private String response;
	
	private String fromEmail;

}
