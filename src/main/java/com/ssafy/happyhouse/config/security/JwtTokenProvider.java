package com.ssafy.happyhouse.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKey = "kangseunghyun";

    // 토큰 유효시간 30분
    private long tokenValidTime = 30 * 60 * 1000L;
//    private long tokenValidTime = 10 * 1000L;//테스트용 토큰 유효시간 10초
    
    private final UserDetailsService userDetailsService;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        System.out.println("roles 출력 : "+roles);
        Date now = new Date();    	
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과 
                                                                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
    	System.out.println("JwtTokenProvider-getUserPk : "+this.getUserPk(token));
//    	System.out.println("JwtTokenProvider-getAuthentication : "+this.getAuthentication(token));
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
//    	System.out.println("method : "+request.getMethod());
    	System.out.println("[resolveToken] : "+request.getHeader("X-AUTH-TOKEN"));
//    	System.out.println(request.getHeader("Authorization"));
//    	System.out.println(request.getHeader("headers"));
//    	System.out.println("header gettest : "+request.getHeader("test"));
//    	System.out.println("parameter getparamtest : "+request.getParameter("test"));
//    	System.out.println("attribute getattrtest : "+request.getAttribute("test"));
//    	System.out.println("token : "+request.getParameter("X-AUTH-TOKEN"));
//    	System.out.println("[resolveToken] token : "+request.getParameter("X-AUTH-TOKEN"));
//    	return (String) request.getAttribute("X-AUTH-TOKEN");
//    	return request.getParameter("X-AUTH-TOKEN");//cors때문에 임시로 해둠

//        System.out.println("=======auth check=======");
//    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
//    	Collection<? extends GrantedAuthority>  authorities = authentication.getAuthorities();    	
//    	System.out.println(authorities.toString());
    	
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}