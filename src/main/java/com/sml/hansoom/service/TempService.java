package com.sml.hansoom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sml.hansoom.persistence.TempRepository;

import lombok.extern.slf4j.Slf4j;

import com.sml.hansoom.model.TempEntity;

@Slf4j
@Service
public class TempService {
	
	@Autowired
	private TempRepository repository;
	
	
	public List<TempEntity> create(final TempEntity entity) {
		//validations
		validate(entity);
		
		repository.save(entity);
		
		log.info("entity id : {} is saved", entity.getId());
		
		return repository.findByUserId(entity.getUserId());
	}
	
	public List<TempEntity> retrieve(final String userId) {
		return repository.findByUserId(userId);
	}
	
	public List<TempEntity> update(final TempEntity entity) {
		validate(entity);
		final Optional<TempEntity> original = repository.findById(entity.getId());
		original.ifPresent(user -> {
			user.setTemp(entity.getTemp());
			repository.save(user);
			log.info("entity id : {} is updated", entity.getId());
		});
		
		return retrieve(entity.getUserId());
	}
	
	public List<TempEntity> delete(final TempEntity entity) {
		validate(entity);
		
		try {
			repository.delete(entity);
			log.info("entity id : {} is deleted", entity.getId());
		} catch(Exception e) {
			log.error("delete error ",entity.getId(), e);
			
			throw new RuntimeException("delete error "+entity.getId());
		}
		
		return retrieve(entity.getUserId());
	}
	
	private void validate(final TempEntity entity) {
		if(entity == null) {
			log.warn("entity cannot be null");
			throw new RuntimeException("entity cannot be null");
		}
		
		if(entity.getUserId() == null) {
			log.warn("unknown user");
			throw new RuntimeException("unknown user");
		}
	}
	

		
	public String testService() {
		//userentity 생성
		TempEntity entity = TempEntity.builder().temp("first user").build();
		//userentity 저장
		repository.save(entity);
		//userentity 검색
		TempEntity savedEntity = repository.findById(entity.getId()).get();
		return savedEntity.getTemp();
	}
}
