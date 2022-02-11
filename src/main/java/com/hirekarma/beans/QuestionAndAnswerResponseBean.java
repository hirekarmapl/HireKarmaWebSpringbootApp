package com.hirekarma.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAndAnswerResponseBean {
private int status;
private String message;

public QuestionAndAnswerResponseBean(int status, String message) {
	super();
	this.status = status;
	this.message = message;
}

public QuestionAndAnswerResponseBean() {

}

public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}


}
