package com.hirekarma.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.model.Stream;
import com.hirekarma.repository.StreamRepository;
import com.hirekarma.service.StreamService;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/")
public class StreamController {

	@Autowired
	StreamRepository streamRepository;
	
	@Autowired
	StreamService streamService;
	@PostMapping("/stream")
	public Stream addStream(@RequestBody Stream stream) {
		Stream streamREturn =null;
		try {
			
			streamREturn = this.streamService.addStream(stream);
			return streamREturn;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@PutMapping("/stream/{id}")
	public Stream addBranchtoStreamwithBranchId(@RequestBody List<Long> branchIds,@PathVariable("id")int id) {
		try {
			Stream stream =  this.streamService.addBranchtoStream(branchIds, id);
			System.out.println(stream.getId());
			return stream;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/stream/{id}")
	public Map<String,Object> getAStream(@PathVariable("id") int id) {
		Stream stream =this.streamRepository.getById(id);
		System.out.println(stream.getId());
		return Map.of("branches",stream.getBranchs());
	}
	@GetMapping("/streams")
	public List<Stream> getAllStreams(){
		List<Stream> streams =  this.streamRepository.findAll();
		return streams;
	}
	
}
