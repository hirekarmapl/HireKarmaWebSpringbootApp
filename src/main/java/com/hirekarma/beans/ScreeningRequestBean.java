package com.hirekarma.beans;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ScreeningRequestBean {

	private String slug;
	private List<Long> jobApplyIds;
	private List<String> slugs;
}
