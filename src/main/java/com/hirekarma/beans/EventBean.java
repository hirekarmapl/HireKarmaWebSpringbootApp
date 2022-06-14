package com.hirekarma.beans;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.model.Corporate;
import com.hirekarma.model.University;
import com.hirekarma.model.UserProfile;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class EventBean {
	String startTimeString;
	String endTimeString;
	String speaker;
	String host;
	String coverImage;
	String keywords;
	String title;
	MultipartFile img;
	String meetingLink;
	Corporate corporate;
	University university;
	UserProfile userProfile;
	List<Long> userIds;
}
