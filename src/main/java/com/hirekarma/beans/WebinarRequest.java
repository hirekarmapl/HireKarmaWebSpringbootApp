package com.hirekarma.beans;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class WebinarRequest {
String id;
	
	String title;
	
	String description;
	
	LocalDateTime scheduledAt;
	
	String meetLink;
	
	Boolean isAccepted=false;
	
	Boolean isDisable=false;
	
	String scheduledDate;
	
}
