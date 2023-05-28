package com.sml.hansoom.security;

import java.io.IOException;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	
//	@Autowired
	private TokenProvider tokenProvider;
//	@Autowired
//	private ApplicationContext applicationContext;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			
			log.info("★Run JwtAuthenticationFilter★"+"   "+request.getRequestURL());
			tokenProvider = (TokenProvider)BeanGetter.getBean("tokenProvider");
			//요청에서 토큰 가져오기
			String token = tokenProvider.parseBearerToken(request);
			log.info("filter is running");
			//토큰 검사하기 jwt이므로 인가 서버에 요청하지 않고도 검증 가능
			if(token!=null && !token.equalsIgnoreCase("null")) {

				//userid 가져오기 위조된 경우 예외처리
				String userEmail = tokenProvider.validateAndGetUserId(token);
				log.info("authenticated email : "+userEmail);
				
				//인증 완료. securitycontextholder에 등록해야 인증된 사용자라고 생각한다
				AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userEmail, // 인증된 사용자의 정보. 문자열이 아니어도 아무것이나 넣기 가능 보통 userdetails라는 오브젝트 넣는데 우린 안 넣음, 생성자의 첫 매개변수로 넣은 것이 AuthenticationPrincipal(principal)이다.
						null, //
						AuthorityUtils.NO_AUTHORITIES
						);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
				securityContext.setAuthentication(authentication);
				SecurityContextHolder.setContext(securityContext);
				
			}
			
			
		} catch(Exception ex) {
			logger.error("could not set user authentication in security contex\n" ,ex);
			if(ex.getClass().getName().equals("io.jsonwebtoken.ExpiredJwtException")) {
				System.out.println("로그인 만료");
				response.sendRedirect("/api/auth/signin");
			}
		}
		filterChain.doFilter(request, response);
		
		
	}
	

}




 