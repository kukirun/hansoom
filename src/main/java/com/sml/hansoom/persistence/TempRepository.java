package com.sml.hansoom.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sml.hansoom.model.TempEntity;

@Repository
public interface TempRepository extends JpaRepository<TempEntity, String>{//테이블에 매핑될 엔티티 클래스 , 엔티티의 기본키 타입
//	@Query("select name from Users t where t.userId = ?1")
	List<TempEntity> findByUserId(String userId);
}
