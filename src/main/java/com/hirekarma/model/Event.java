package com.hirekarma.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Event {

	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)")
	@Id
	String id;
	LocalDateTime startTime;
	LocalDateTime endTime;
	String speaker;
	String host;
	String coverImage;
	String keywords;
	String title;
	String meetingLink;
	@ManyToOne
	Corporate corporate;
	@ManyToOne
	University university;
	@ManyToMany
	Set<UserProfile> userProfiles;
	
}
