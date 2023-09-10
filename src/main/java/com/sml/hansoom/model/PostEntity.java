package com.sml.hansoom.model;

import java.sql.Timestamp;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "posts")
public class PostEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postNum;
	@Column(nullable = false)
	private String postSubject;
	@Column(nullable = false)
	private String postContent;
	@Column(nullable = false)
	private String userId;
	@Column(nullable = false)
	private Timestamp timeStamp;
}
