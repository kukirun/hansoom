package com.sml.hansoom.controller;

import java.io.IOException;
import java.util.ArrayList;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
import com.sml.hansoom.security.BeanGetter;
import com.sml.hansoom.security.JwtAuthenticationFilter;
import com.sml.hansoom.security.TokenProvider;
import com.sml.hansoom.service.UserService;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/post")
public class postController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();	
	
	@PostMapping("/read")
	public ResponseEntity<?> readPost() {
		
		return ResponseEntity.ok().body("로그인 페이지");
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createPost(@RequestBody UserDTO userDTO) {
		try {
			log.info("signup");
			UserEntity user = UserEntity.builder()
					.email(userDTO.getEmail())
					.username(userDTO.getUsername())
					.password(passwordEncoder.encode(userDTO.getPassword()))
					.build();
		
			UserEntity registeredUser = userService.create(user);
			UserDTO responseUserDTO = UserDTO.builder()
					.email(registeredUser.getEmail())
					.id(registeredUser.getId())
					.username(registeredUser.getUsername())
					.build();
			
			return ResponseEntity.ok().body(responseUserDTO);
			
		} catch (Exception e) {
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			
			return ResponseEntity
					.badRequest()
					.body(responseDTO);
		}
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> updatePost() {
		return ResponseEntity.ok().body("로그인 페이지");
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> deletePost(@RequestBody UserDTO userDTO) {
		log.info("signin");
		UserEntity user = userService.getByCredentials(
				userDTO.getEmail(),
				userDTO.getPassword(),
				passwordEncoder);
		
		if(user != null) {
			//토큰 생성
			final String token  = tokenProvider.create(user);
			final String refreshToken = tokenProvider.publishRefresh(user);
			
			final UserDTO responseUserDTO = UserDTO.builder()
					.email(user.getEmail())
					.id(user.getId())
					.token(token)
					.refreshToken(refreshToken)
					.build();
			
			userService.updateRefreshToken(responseUserDTO);
			
			return ResponseEntity.ok().body(responseUserDTO);
		} else {
			ResponseDTO responseDTO = ResponseDTO.builder()
					.error("login faied")
					.build();
			return ResponseEntity
					.badRequest()
					.body(responseDTO);
		}
	}
	
	@PostMapping("/extend")
	public ResponseEntity<?> extendLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String refreshToken = tokenProvider.parseBearerToken(request);
		String userEmail;
		UserEntity userEntity;
		UserDTO responseUserDTO;
		try {
			//토큰에서 유저 아이디 추출 및 유효성 검사
			userEmail = tokenProvider.validateRefreshAndGetUserId(refreshToken);
			userEntity= userService.getByEmail(userEmail);
			//리프래시 토큰에서 유저 이메일을 추출해서 db에 저장된 리프래시 토큰을 불러와 비교. 맞으면 토큰 재발급
			if(userEntity.getRefreshToken().equals(refreshToken)) {
				String newToken = tokenProvider.create(userEntity);
				responseUserDTO = UserDTO.builder()
						.token(newToken)
						.build();
				log.info("토큰 재발급" + responseUserDTO);
				return ResponseEntity.ok().body(responseUserDTO);
			} else {
				//틀리면 catch구문
				throw new io.jsonwebtoken.ExpiredJwtException(null, null, userEmail, null);
			}
		} catch(Exception ex) {
			log.error("could not set user authentication in security contex\n" +ex);
			if(ex.getClass().getName().equals("io.jsonwebtoken.ExpiredJwtException")) {
				return ResponseEntity.ok().body("연장 불가 재로그인 필요"); //리프래시 토큰 만료시
			} else {
				return ResponseEntity.ok().body("연장 오류"+ex.getClass().getName()); // 다른 오류가 났을대
			}
			
		}
		
	}
	
}
