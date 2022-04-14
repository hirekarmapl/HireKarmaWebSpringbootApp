package com.hirekarma.beans;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class StudentMentorBooking {

	String startTime;
	String endTime;
	String mentorId;
	String date;
	String reason;
}
