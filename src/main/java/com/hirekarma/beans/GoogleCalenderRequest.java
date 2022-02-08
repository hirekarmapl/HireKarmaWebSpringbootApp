package com.hirekarma.beans;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
@Component
public class GoogleCalenderRequest {
	
	@NotNull
	@NotBlank
	String eventName;

	@NotNull
	@NotBlank
	String description;

	@NotNull
	@NotBlank
	String location;


	@NotEmpty
	List< String> attendees;

	@NotNull
	@NotBlank
	String startTime;

	@NotNull
	@NotBlank
	String endTime;

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	

	public List<String> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<String> attendees) {
		this.attendees = attendees;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "GoogleCalenderRequest [eventName=" + eventName + ", description=" + description + ", location="
				+ location + ", attendees=" + attendees + ", startTime="
				+ startTime + ", endTime=" + endTime + "]";
	}

	public GoogleCalenderRequest(@NotNull @NotBlank String eventName, @NotNull @NotBlank String description,
			@NotNull @NotBlank String location,
			@NotEmpty List<@NotBlank String> attendees, @NotNull @NotBlank String startTime,
			@NotNull @NotBlank String endTime) {
		super();
		this.eventName = eventName;
		this.description = description;
		this.location = location;
		this.attendees = attendees;
		this.startTime = startTime;
		this.endTime = endTime;
		
		
	}

	public GoogleCalenderRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
