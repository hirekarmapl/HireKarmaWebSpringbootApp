package com.hirekarma.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.model.HireKarmaUser;
import com.hirekarma.repository.HireKarmaUserRepository;
import com.hirekarma.service.HireKarmaUserService;

@Service("hireKarmaUserService")
public class HireKarmaUserServiceImpl implements HireKarmaUserService{
	
	@Autowired
	private HireKarmaUserRepository hireKarmaUserRepository;

	@Override
	public HireKarmaUser insert(HireKarmaUser hireKarmaUser) {
		return hireKarmaUserRepository.save(hireKarmaUser);
	}

}
