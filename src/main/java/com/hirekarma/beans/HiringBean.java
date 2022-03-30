package com.hirekarma.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
public class HiringBean {

	Long jobApplyId;
	Long tableId;
	String startTime;
	String endTime;
	String title;
}
