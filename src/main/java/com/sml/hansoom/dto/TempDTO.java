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
public class TempDTO {
	private String id;
	private String temp;
	
	public TempDTO(final TempEntity entity) {
		this.id = entity.getId();
		this.temp = entity.getTemp();
	}
	
	public static TempEntity toEntity(final TempDTO dto) {
		return TempEntity.builder()
				.id(dto.getId())
				.temp(dto.getTemp())
				.build();
	}
}
