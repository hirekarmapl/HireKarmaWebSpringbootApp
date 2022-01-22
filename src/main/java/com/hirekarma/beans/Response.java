package com.hirekarma.beans;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Response {

	public Object status;
	
	public Object responseCode;
	
	public Object message;
	
	public Object data;
	
	public List<Object> dataList;
}
