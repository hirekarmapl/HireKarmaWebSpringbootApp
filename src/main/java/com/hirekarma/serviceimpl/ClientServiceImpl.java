package com.hirekarma.serviceimpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.ClientBean;
import com.hirekarma.model.Client;
import com.hirekarma.repository.ClientRepository;
import com.hirekarma.utilty.Utility;
@Service("ClientService")
public class ClientServiceImpl implements ClientService{
	@Autowired
	ClientRepository clientRepository;

	@Override
	public Client addClientByAdmin(ClientBean clientBean, MultipartFile file) throws Exception {
		Client client = new Client();
		BeanUtils.copyProperties(clientBean, client);
		byte[] fileArr = Utility.readFile(file);
		client.setLogo(fileArr);
		return this.clientRepository.save(client);
	}

	@Override
	public void deleteClientByAdminById(int id) {
		this.clientRepository.deleteById(id);
	}

	@Override
	public Client updateClientByAdmin(int id, ClientBean clientBean, MultipartFile file) throws Exception {
		Client client = this.clientRepository.getById(id);
		BeanUtils.copyProperties(clientBean, client);
		byte[] fileArr = Utility.readFile(file);
		client.setLogo(fileArr);
		return this.clientRepository.save(client);
	}

}
