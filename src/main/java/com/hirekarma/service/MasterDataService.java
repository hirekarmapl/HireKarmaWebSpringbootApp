package com.hirekarma.service;

import java.util.List;
import java.util.Map;

public interface MasterDataService {

	List<Map<String, Object>> fetchMasterData(String value);

}
