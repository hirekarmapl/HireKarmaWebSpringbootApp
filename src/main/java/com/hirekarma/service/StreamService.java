package com.hirekarma.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hirekarma.model.Stream;

@Service
public interface StreamService {
	public Stream addStream(Stream stream);
	public Stream addBranchtoStream(List<Long> branchIds,int streamId) throws Exception;
}
