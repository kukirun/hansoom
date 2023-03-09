package com.sml.hansoom.dto;

import com.sml.hansoom.model.TempEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
	private String token;
	private String email;
	private String username;
	private String id;
	private String password;
	
}
