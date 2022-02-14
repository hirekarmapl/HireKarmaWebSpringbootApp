package com.hirekarma.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.CreationTimestamp;

import com.google.api.client.util.DateTime;

import java.sql.Timestamp;

@Entity
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
	@Column(unique = true)
	String slug;
	
	@Lob
	byte featureImage[];
	
	
	String link;
	
	String body;
	
	String keywords;
	
	@CreationTimestamp
	Timestamp createdOn;
	
	
	Timestamp deadLine;




	@Override
	public String toString() {
		return "Notice [id=" + id + ", slug=" + slug + ", featureImage=" + Arrays.toString(featureImage) + ", link="
				+ link + ", body=" + body + ", keywords=" + keywords + ", createdOn=" + createdOn + ", deadLine="
				+ deadLine + "]";
	}




	public Notice(int id, String slug, byte[] featureImage, String link, String body, String keywords,
			Timestamp createdOn, Timestamp deadLine) {
		super();
		this.id = id;
		this.slug = slug;
		this.featureImage = featureImage;
		this.link = link;
		this.body = body;
		this.keywords = keywords;
		this.createdOn = createdOn;
		this.deadLine = deadLine;
	}




	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public String getSlug() {
		return slug;
	}




	public void setSlug(String slug) {
		this.slug = slug;
	}




	public byte[] getFeatureImage() {
		return featureImage;
	}




	public void setFeatureImage(byte[] featureImage) {
		this.featureImage = featureImage;
	}




	public String getLink() {
		return link;
	}




	public void setLink(String link) {
		this.link = link;
	}




	public String getBody() {
		return body;
	}




	public void setBody(String body) {
		this.body = body;
	}




	public String getKeywords() {
		return keywords;
	}




	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}




	public Timestamp getCreatedOn() {
		return createdOn;
	}




	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}




	public Timestamp getDeadLine() {
		return deadLine;
	}




	public void setDeadLine(Timestamp deadLine) {
		this.deadLine = deadLine;
	}




	public Notice() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
