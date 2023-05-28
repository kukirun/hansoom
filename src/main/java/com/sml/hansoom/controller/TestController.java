package com.sml.hansoom.controller;

import java.util.ArrayList;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sml.hansoom.dto.ResponseDTO;

import com.sml.hansoom.dto.UserDTO;

import com.sml.hansoom.model.UserEntity;
import com.sml.hansoom.security.TokenProvider;
import com.sml.hansoom.service.UserService;

import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired 
	private ApplicationContext applicationContext;
	
	@GetMapping("/")
	public ResponseEntity<?> temp() {
		
		return ResponseEntity.ok().body("hi");
	}
	
	@PostMapping("/")
	public ResponseEntity<?> temp2() {
		
		return ResponseEntity.ok().body("hi");
	}

}
