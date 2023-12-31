package com.example.justJwt.jwt.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.justJwt.jwt.domain.User;
import org.springframework.stereotype.Component;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtServiceImpl {
    private String secretKey = "asdfaesfaef"; // 서명에 사용할 secretKey
    private long exp = 1000L * 60 * 60; // 토큰 사용가능 시간, 1시간



    // 토큰 생성하는 메서드
    public String createToken(User user) { // 토큰에 담고싶은 값 파라미터로 가져오기
        User test = User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();

        return Jwts.builder()
                .setHeaderParam("typ", "JWT") // 토큰 타입
                .setSubject("userToken") // 토큰 제목
                .setExpiration(new Date(System.currentTimeMillis() + exp)) // 토큰 유효시간
                .claim("user", test) // 토큰에 담을 데이터
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // secretKey를 사용하여 해싱 암호화 알고리즘 처리
                .compact(); // 직렬화, 문자열로 변경
    }

    // 토큰에 담긴 정보를 가져오기 메서드
    public Map<String, Object> getInfo(String token) throws Exception {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token); // secretKey를 사용하여 복호화
        } catch(Exception e) {
            throw new Exception();
        }

        return claims.getBody();
    }

    public String getId(String token) throws Exception {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token); // secretKey를 사용하여 복호화
            String id = claims.getBody().get("user", LinkedHashMap.class).get("id").toString();
            return id;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    // interceptor에서 토큰 유효성을 검증하기 위한 메서드
    public void checkValid(String token) {
        Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
    }
}
