package com.hirekarma.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.ClientBean;
import com.hirekarma.model.Client;
@Service
public interface ClientService  {

	Client addClientByAdmin(ClientBean clientBean,MultipartFile file) throws Exception;
	void deleteClientByAdminById(int id);
	Client updateClientByAdmin(int id,ClientBean clientBean,MultipartFile file) throws Exception;
}
