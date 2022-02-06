package com.hirekarma.service;

import java.util.List;
import java.util.Map;

import org.hibernate.engine.jdbc.batch.spi.Batch;

import com.hirekarma.beans.AdminShareJobToUniversityBean;
import com.hirekarma.model.StudentBatch;
import com.hirekarma.model.StudentBranch;

public interface MasterDataService {

	List<Map<String, Object>> fetchMasterData(String value);

	Map<String, Object> createBatch(StudentBatch btch);

	Map<String, Object> createBranch(StudentBranch branch);

}
