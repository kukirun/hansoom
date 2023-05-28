package com.sml.hansoom.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sml.hansoom.model.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenProvider {
	private static final String SECRET_KEY = "NMA8JPctFuna59f5";
	private static final String REFRESH_KEY = "EYa1Toacqsdk12h9";
	
	public String create(UserEntity userEntity) {
		//기한은 1일
		Date expiryDate = Date.from(
				Instant.now()
				.plus(1,ChronoUnit.DAYS));
		
		return Jwts.builder()
				//header 내용 및 서명 시크릿키
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.setSubject(userEntity.getEmail())
				.setIssuer("hansoom")
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.compact();
	}
	
	public String publishRefresh(UserEntity userEntity) {
		//기한은 1일
		Date expiryDate = Date.from(
				Instant.now()
				.plus(7,ChronoUnit.DAYS));
		
		return Jwts.builder()
				//header 내용 및 서명 시크릿키
				.signWith(SignatureAlgorithm.HS512, REFRESH_KEY)
				.setSubject(userEntity.getEmail())
				.setIssuer("hansoom")
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.compact();
	}
	
	public String reissue() {
	
		
		
		return null;
	}
	
	public String validateAndGetUserId(String token) {
		//parseclaimsjws 가 base64로 디코딩 및 파싱
		// 헤더와 페이로드를 시크릿키를 이용해 서명후 토큰과 비교
		// 위조되지 않았으며 ㄴ페이로드claims 리턴 아니면 예외
		// 우린 userID가 필요하니 getbody
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
	}
	
	public String validateRefreshAndGetUserId(String token) {
		//parseclaimsjws 가 base64로 디코딩 및 파싱
		// 헤더와 페이로드를 시크릿키를 이용해 서명후 토큰과 비교
		// 위조되지 않았으며 ㄴ페이로드claims 리턴 아니면 예외
		// 우린 userID가 필요하니 getbody
		Claims claims = Jwts.parser()
				.setSigningKey(REFRESH_KEY)
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
	}
	
	public String parseBearerToken(HttpServletRequest request) {
		//http 요청의 헤더를 파싱해 bearer 토큰을 리턴
		String bearerToken = request.getHeader("Authorization");
		log.info(bearerToken);
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
