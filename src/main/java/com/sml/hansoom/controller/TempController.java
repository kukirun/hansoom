package com.sml.hansoom.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sml.hansoom.dto.ResponseDTO;
import com.sml.hansoom.dto.TestRequestBodyDTO;
import com.sml.hansoom.dto.TempDTO;
import com.sml.hansoom.model.TempEntity;
import com.sml.hansoom.service.TempService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("user2")
public class TempController {
	
	@Autowired
	private TempService service;
	
	@GetMapping
	public ResponseEntity<?> retrieveUserList() {
		String temporaryUserId = "temporary-user";
		List<TempEntity> entities = service.retrieve(temporaryUserId);
		List<TempDTO> dtos = entities.stream().map(TempDTO::new).collect(Collectors.toList());
		ResponseDTO<TempDTO> response = ResponseDTO.<TempDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody TempDTO dto) {
		try {
			String temporaryUserId = "temporary-user";
			
			TempEntity entity = TempDTO.toEntity(dto);
			entity.setId(null);
			entity.setUserId(temporaryUserId);

			List<TempEntity> entities = service.create(entity);


			List<TempDTO> dtos = entities.stream().map(TempDTO::new).collect(Collectors.toList());

			ResponseDTO<TempDTO> response = ResponseDTO.<TempDTO>builder().data(dtos).build();
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TempDTO> response = ResponseDTO.<TempDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody TempDTO dto) {
		String temporaryUserId = "temporary-user";
		
		TempEntity entity = TempDTO.toEntity(dto);
		entity.setUserId(temporaryUserId);
		
		List<TempEntity> entities = service.update(entity);
		
		List<TempDTO> dtos = entities.stream().map(TempDTO::new).collect(Collectors.toList());
		
		ResponseDTO<TempDTO> response = ResponseDTO.<TempDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteUser(@RequestBody TempDTO dto) {
		try {
			String temporaryUserId = "temporary-user";
			TempEntity entity = TempDTO.toEntity(dto);
			entity.setUserId(temporaryUserId);
			
			List<TempEntity> entities = service.delete(entity);
			
			List<TempDTO> dtos = entities.stream().map(TempDTO::new).collect(Collectors.toList());
			
			ResponseDTO<TempDTO> response = ResponseDTO.<TempDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok().body(response);
		} catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<TempDTO> response = ResponseDTO.<TempDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/test")
	public ResponseEntity<?> testUser() {
		String str = service.testService();
		List<String> list = new ArrayList<>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.ok().body(response);
	}
	
}
