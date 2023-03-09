package com.sml.hansoom.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sml.hansoom.dto.ResponseDTO;
import com.sml.hansoom.dto.TestRequestBodyDTO;

@RestController
@RequestMapping("test")
public class TestController {
	
	@GetMapping("testGetMapping")
	public String testController() {
		return "hi";
	}
	@GetMapping("/{id}")//http://localhost:8080/test/123
	public String testController2(@PathVariable(required = false) int id) {
		return "hi2 "+id;
	}
	@GetMapping("/testRequestParam")//http://localhost:8080/test/testRequestParam?id=123
	public String testController3(@RequestParam(required = false) int id) {
		return "hi3 " + id;
	}
	@GetMapping("/testRequestBody")
	public String testController4(@RequestBody TestRequestBodyDTO dto) {
		return "hi4 " + dto.getId() + " message " + dto.getMessage();
	}
	/*
	 {
    	"id" : 123,
    	"message" : "hi"
	}
	 */
	@GetMapping("/testResponseBody") // http://localhost:8080/test/testResponseBody
	public ResponseDTO<String> testController5() {
		List<String> list = new ArrayList<>();
		list.add("hi");
		ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
		return responseDTO;
	}
	@GetMapping("/testResponseEntity") // http://localhost:8080/test/testResponseEntity HTTP status 조작 가능
	public ResponseEntity<?> testController6() {
		List<String> list = new ArrayList<>();
		list.add("hi 400");
		ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
		
		return ResponseEntity.badRequest().body(responseDTO);
	}
	
	
	
}
