package com.hospital.hospital_community.domain.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    public static Claims extractClaims(String token, String secretKey){ // map(key, value) 형식이기 때문에 email로 claim얻을 수 있음
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

    }
    public static String getEmail(String tokne, String secretKey){
        return extractClaims(tokne, secretKey).get("email").toString();
    }
    public static boolean isExpired(String token, String secretKey){
        Date expiredDate = extractClaims(token, secretKey).getExpiration();
        return expiredDate.before(new Date()); // 현재 시각보다 이전이라면 유효기간이 만료된 것임o
    }
    public static String createToken(String email, String key, long expireTimeMs) {
        Claims claims = Jwts.claims(); // 일종의 map
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}
