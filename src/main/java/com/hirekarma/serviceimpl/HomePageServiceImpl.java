package com.hirekarma.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.HomePageBean;
import com.hirekarma.model.HomePage;
import com.hirekarma.repository.HomePageRepository;
import com.hirekarma.service.HomePageService;
@Service("HomePageService")
public class HomePageServiceImpl implements HomePageService {

	@Autowired
	HomePageRepository homePageRepository;
	@Override
	public Map<String, Object> add(HomePageBean homePageBean) throws Exception {
		Map<String,Object> result = new HashMap<String, Object>();
		System.out.println(homePageBean);
		if(homePageBean.getData()==null||homePageBean.getXpath()==null||homePageBean.getUrl()==null||homePageBean.getData().equals("")||homePageBean.getXpath().equals("")||homePageBean.getUrl().equals("")) {
			throw new Exception("all the fields are mandatory");			
		}
		
		HomePage homePage = homePageRepository.findByUrlAndXpath(homePageBean.getUrl(), homePageBean.getXpath());
		
		if(homePage==null) {
			HomePage homePageSaved = new HomePage();
			BeanUtils.copyProperties(homePageBean, homePageSaved);
			this.homePageRepository.save(homePageSaved);
		}else {
			homePage.setData(homePageBean.getData());
			this.homePageRepository.save(homePage);
		}
		return result;
	}

}
