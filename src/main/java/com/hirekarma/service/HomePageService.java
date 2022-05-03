package com.hirekarma.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hirekarma.beans.HomePageBean;

@Service
public interface HomePageService {
	Map<String,Object> add(HomePageBean homePageBean) throws Exception;
}
