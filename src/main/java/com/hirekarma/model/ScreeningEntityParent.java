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
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class ScreeningEntityParent {

	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)")
	@Id
	String id;
	
	String title;
	
	Long coporateId;
	
	Long universityId;
	
	
	
	@ManyToMany
	Set<ScreeningEntity> screeningEntities;
	
	LocalDateTime createdOn= LocalDateTime.now();

	LocalDateTime updatedOn;
	
	boolean deleted;
}
