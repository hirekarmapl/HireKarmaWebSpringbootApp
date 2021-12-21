package com.hirekarma.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.InternshipApplyBean;
import com.hirekarma.exception.InternshipApplyException;
import com.hirekarma.model.InternshipApply;
import com.hirekarma.repository.InternshipApplyRepository;
import com.hirekarma.service.InternshipApplyService;

@Service("internshipApplyServiceImpl")
public class InternshipApplyServiceImpl implements InternshipApplyService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InternshipApplyServiceImpl.class);
	
	@Autowired
	private InternshipApplyRepository internshipApplyRepository;
	
	@Override
	public InternshipApplyBean insert(InternshipApplyBean internshipApplyBean) {
		LOGGER.debug("Inside InternshipApplyServiceImpl.insert()");
		InternshipApply internshipApply=null,internshipApplyReturn=null;
		InternshipApplyBean applyBean=null;
		try {
			LOGGER.debug("Inside try block of InternshipApplyServiceImpl.insert()");
			internshipApply=new InternshipApply();
			BeanUtils.copyProperties(internshipApplyBean, internshipApply);
			internshipApply.setDeleteStatus("Active");
			internshipApplyReturn=internshipApplyRepository.save(internshipApply);
			applyBean=new InternshipApplyBean();
			BeanUtils.copyProperties(internshipApplyReturn, applyBean);
			LOGGER.info("Data saved using InternshipApplyServiceImpl.insert()");
			return applyBean;
		}
		catch (Exception e) {
			LOGGER.error("Data Insertion failed using InternshipApplyServiceImpl.insert(-): "+e);
			throw new InternshipApplyException(e.getMessage());
		}
	}

}
