package com.hoon.chat.service;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
//Jwt를 생성하고 검증하는 컴포넌트
public class JwtTokenProvider {
    @Value("${jwt.secret}")//application.properties의 jwt.secret과 연결
    private String secretKey;
    
    private long tokenValidTime = 1000L * 60 * 60; // 1시간

    //토큰 생성
    public String createToken(String name){
        Date now = new Date();
        return Jwts.builder()
                .setId(name)
                .setIssuedAt(now) // 토큰 발행 일자
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // 토큰 유효시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘
                .compact();
    }

    //Jwt Token을 복호화해 이름을 return
    public String getUserNameFromJwt(String jwt){
        return getClaims(jwt).getBody().getId();
    }

    public boolean validateToken(String jwt){
        return this.getClaims(jwt) != null;
    }

    //claims : 속성 정보(?), 권한 집합
    //JWT는 속성 정보 (Claim)를 JSON 데이터 구조로 표현한 토큰
    //Jwt토큰 유효성 검증 메서드
    private Jws<Claims> getClaims(String jwt){
        try{
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);
        }catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw ex;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw ex;
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw ex;
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw ex;
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw ex;
        }
    }
}
