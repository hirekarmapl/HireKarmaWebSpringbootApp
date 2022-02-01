package com.hirekarma.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "BADGES")
@Data
@ToString
public class Badges {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "BADGE_ID")
	private Long badgeId;
	
	@Column(name = "BADGE_NAME")
	private String badgeName;

}
