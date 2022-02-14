package com.hirekarma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.ClientBean;
import com.hirekarma.beans.NoticeBean;
import com.hirekarma.model.Client;
import com.hirekarma.repository.ClientRepository;
import com.hirekarma.serviceimpl.ClientService;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/admin/")
public class ClientController {
	@Autowired
	ClientService clientService;
	
	@Autowired
	ClientRepository clientRepository;
	

	
	@PreAuthorize("hasRole('admin')")
	@DeleteMapping("/client/{id}")
	public void deleteClientByAdminById(@PathVariable int id) {
		this.clientService.deleteClientByAdminById(id);
	}
	
	@PreAuthorize("hasRole('admin')")
	@GetMapping("/client/")
	public List<Client> getAllClientByAdmin() {
		return this.clientRepository.findAll();
	}
	
	@PreAuthorize("hasRole('admin')")
	@GetMapping("/client/{id}")
	public Client getClientByAdminById(@PathVariable int id) {
		return this.clientRepository.getById(id);
	}
	
	@PreAuthorize("hasRole('admin')")
	@RequestMapping(value = "/client/", method = RequestMethod.POST, consumes = "multipart/form-data")
	public Client addClientByAdmin(@RequestPart("data") ClientBean clientBean, @RequestPart("file") MultipartFile file) {
		try {
			return this.clientService.addClientByAdmin(clientBean, file);
		} catch (Exception e) {
			return null;
		}
	}
	@PreAuthorize("hasRole('admin')")
	@RequestMapping(value = "/client/{id}", method = RequestMethod.PUT, consumes = "multipart/form-data")
	public Client updateClientByAdmin(@PathVariable int id,@RequestPart("data") ClientBean clientBean, @RequestPart("file") MultipartFile file) {
		try {
			return this.clientService.updateClientByAdmin(id,clientBean, file);
		} catch (Exception e) {
			return null;
		}
	}
}
