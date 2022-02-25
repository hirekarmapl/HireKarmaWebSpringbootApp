package com.hirekarma.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.model.Stream;
import com.hirekarma.repository.StreamRepository;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/")
public class StreamController {

	@Autowired
	StreamRepository streamRepository;
	
	@PostMapping("/stream")
	public Stream addStream(@RequestBody Stream stream) {
		Stream streamREturn =null;
		try {
			return this.streamRepository.save(stream);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/streams")
	public List<Stream> getAllStreams(){
		List<Stream> streams =  this.streamRepository.findAll();
		return streams;
	}
	
}
