package com.hirekarma.model;

import java.time.LocalDateTime;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.GeneratorType;

import com.sun.istack.NotNull;
@Entity
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	@NotNull
	String title;
	
	@ManyToOne
	Category category;
	
	String imageUrl;
	
	@NotNull
	String body;
	
	String keyword;
	
	String metaTitle;
	
	String metaDescription;
	
	@ManyToOne
	Corporate corporate;
	

	boolean ispublic =  false;
	
	LocalDateTime createdOn ;
	
	LocalDateTime updatedOn;
	
	
	
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(unique = true)
	@NotNull
	String slug;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	

	

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMetaTitle() {
		return metaTitle;
	}

	
	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public Corporate getCorporate() {
		return corporate;
	}

	public void setCorporate(Corporate corporate) {
		this.corporate = corporate;
	}

	public boolean isIspublic() {
		return ispublic;
	}

	public void setIspublic(boolean ispublic) {
		this.ispublic = ispublic;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public Blog(int id, String title, Category category, byte[] image, String altImage, String body, String keyword,
			String metaTitle, String metaDescription, Corporate corporate, boolean ispublic, String slug) {
		super();
		this.id = id;
		this.title = title;
		this.category = category;
		this.body = body;
		this.keyword = keyword;
		this.metaTitle = metaTitle;
		this.metaDescription = metaDescription;
		this.corporate = corporate;
		this.ispublic = ispublic;
		this.slug = slug;
	}

	public Blog() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
