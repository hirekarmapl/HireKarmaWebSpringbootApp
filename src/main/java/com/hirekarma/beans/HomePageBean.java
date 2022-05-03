package com.hirekarma.beans;

import com.hirekarma.model.HomePage;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HomePageBean {
	String url;
	String xpath;
	String data;
}
