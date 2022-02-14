package com.hirekarma.beans;

public class NoticeBean {

	String link;
	
	String body;
	
	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	String keywords;
	String deadline;

	public NoticeBean(String link, String body, String keywords) {
		super();
		this.link = link;
		this.body = body;
		this.keywords = keywords;
	}

	@Override
	public String toString() {
		return "NoticeBean [link=" + link + ", body=" + body + ", keywords=" + keywords + "]";
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

}
