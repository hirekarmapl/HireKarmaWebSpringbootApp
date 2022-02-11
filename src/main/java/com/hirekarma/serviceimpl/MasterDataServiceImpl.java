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

import com.hirekarma.exception.AdminException;
import com.hirekarma.model.StudentBatch;
import com.hirekarma.model.StudentBranch;
import com.hirekarma.repository.BadgesRepository;
import com.hirekarma.repository.StudentBatchRepository;
import com.hirekarma.repository.StudentBranchRepository;
import com.hirekarma.service.MasterDataService;

@Service("masterDataServiceImpl")
public class MasterDataServiceImpl implements MasterDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UniversityServiceImpl.class);

	@Autowired
	public StudentBranchRepository branchRepository;

	@Autowired
	public StudentBatchRepository batchRepository;

	@Autowired
	public BadgesRepository badgesRepository;

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

		case "badge":

			LOGGER.info("Inside Badge Details..");

			list = badgesRepository.getBadgeDetails();
			it = list.iterator();
			while (it.hasNext()) {
				Object[] state = it.next();
				Map<String, Object> st = new HashMap<String, Object>();
				st.put("badgeId", state[0]);
				st.put("badgeName", state[1]);
				dataList.add(st);
			}

			break;
		}

		return dataList;
	}

	@Override
	public Map<String, Object> createBatch(StudentBatch btch) {

		Map<String, Object> response = new HashMap<String, Object>();
		StudentBatch studentBatch = new StudentBatch();

		try {
			LOGGER.debug("Inside MasterDataServiceImpl.createBatch(-)");

			if (btch.getBatchName() != null && !btch.getBatchName().equals("")
					&& !btch.getBatchName().equalsIgnoreCase("null")) {

				studentBatch.setBatchName(btch.getBatchName());
				studentBatch = batchRepository.save(studentBatch);

				LOGGER.debug("Batch Created At MasterDataServiceImpl.createBatch(-)");
				response.put("response", studentBatch);

			} else {
				throw new AdminException("Batch Name Can't Be Blank Or Null");
			}

		} catch (Exception e) {
			throw e;
		}
		return response;
	}

	@Override
	public Map<String, Object> createBranch(StudentBranch branch) {
		Map<String, Object> response = new HashMap<String, Object>();
		StudentBranch studentBranch = new StudentBranch();

		try {
			LOGGER.debug("Inside MasterDataServiceImpl.createBatch(-)");

			if (branch.getBranchName() != null && !branch.getBranchName().equals("")
					&& !branch.getBranchName().equalsIgnoreCase("null")) {

				studentBranch.setBranchName(branch.getBranchName());
				studentBranch = branchRepository.save(studentBranch);

				LOGGER.debug("Branch Created At MasterDataServiceImpl.createBranch(-)");
				response.put("response", studentBranch);

			} else {
				throw new AdminException("Branch Name Can't Be Blank Or Null");
			}

		} catch (Exception e) {
			throw e;
		}
		return response;

	}

}
