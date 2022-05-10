package com.hirekarma.beans;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.hirekarma.model.Category;
import com.hirekarma.model.Corporate;
import com.sun.istack.NotNull;

public class BlogBean {

	String title;
	
	Integer categoryid;
	String altImage;
	
	String body;
	
	String keyword;
	
	String metaTitle;
	
	String metaDescription;
	
	


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}

	public String getAltImage() {
		return altImage;
	}

	public void setAltImage(String altImage) {
		this.altImage = altImage;
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

	

	public Integer getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}

	public Long getCorporateid() {
		return corporateid;
	}

	public void setCorporateid(Long corporateid) {
		this.corporateid = corporateid;
	}

	public BlogBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	Long corporateid;

	
}
