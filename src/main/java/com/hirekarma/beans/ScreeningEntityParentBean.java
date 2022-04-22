package com.hirekarma.beans;

import java.util.List;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class ScreeningEntityParentBean {
String title;
List<Long> screeningEntityIds;
String screeningEntityParentSlug;
List<Long> studentIds;
List<String> screeningEntityParentSlugs;
	
	
}
