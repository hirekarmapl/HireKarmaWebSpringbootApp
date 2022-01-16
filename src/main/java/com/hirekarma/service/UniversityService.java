package com.hirekarma.service;

import com.hirekarma.beans.ShareJobBean;
import com.hirekarma.beans.UniversityJobShareBean;

public interface UniversityService {

	ShareJobBean universityResponse(ShareJobBean jobBean);

	UniversityJobShareBean shareJobStudent(UniversityJobShareBean universityJobShareBean);

}
