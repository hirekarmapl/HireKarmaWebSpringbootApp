package com.hirekarma.beans;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.model.Skill;
import com.hirekarma.model.University;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProjectResponseBean {

	int id;
	String name;
	String description;
	String link;
}
