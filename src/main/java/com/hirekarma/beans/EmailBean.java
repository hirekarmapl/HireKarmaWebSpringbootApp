package com.hirekarma.beans;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EmailBean {
	
	private String toEmail;
	
	private List<String> toListMail;
	
	private String response;

}
