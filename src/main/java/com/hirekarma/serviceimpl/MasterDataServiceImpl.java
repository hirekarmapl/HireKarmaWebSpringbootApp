package com.hirekarma.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.repository.StudentBatchRepository;
import com.hirekarma.repository.StudentBranchRepository;
import com.hirekarma.repository.StudentCGPARepository;
import com.hirekarma.service.MasterDataService;

@Service("masterDataServiceImpl")
public class MasterDataServiceImpl implements MasterDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UniversityServiceImpl.class);

	@Autowired
	public StudentBranchRepository branchRepository;

	@Autowired
	public StudentBatchRepository batchRepository;

	@Autowired
	public StudentCGPARepository cgpaRepository;

	@Override
	public List<Map<String, Object>> fetchMasterData(String value) {

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		List<Object[]> list;
		Iterator<Object[]> it;
		System.out.println("Fetching datas for -- " + value);

		switch (value) {

		case "branch":

			LOGGER.info("Inside Branch Details..");

			list = branchRepository.getBranchList();
			it = list.iterator();
			while (it.hasNext()) {
				Object[] state = it.next();
				Map<String, Object> st = new HashMap<String, Object>();
				st.put("BranchId", state[0]);
				st.put("BranchName", state[1]);
				dataList.add(st);
			}

			break;

		case "batch":

			LOGGER.info("Inside Batch Details..");

			list = batchRepository.getBatchList();
			it = list.iterator();
			while (it.hasNext()) {
				Object[] state = it.next();
				Map<String, Object> st = new HashMap<String, Object>();
				st.put("BatchId", state[0]);
				st.put("BatchName", state[1]);
				dataList.add(st);
			}

			break;

		case "cgpa":

			LOGGER.info("Inside CGPA Details..");

			list = cgpaRepository.getCgpaList();
			it = list.iterator();
			while (it.hasNext()) {
				Object[] state = it.next();
				Map<String, Object> st = new HashMap<String, Object>();
				st.put("CgpaId", state[0]);
				st.put("CgpaName", state[1]);
				dataList.add(st);
			}

			break;
		}

		return dataList;
	}

}
