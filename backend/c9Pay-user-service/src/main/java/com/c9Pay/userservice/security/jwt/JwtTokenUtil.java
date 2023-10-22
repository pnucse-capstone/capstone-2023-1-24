package com.c9Pay.userservice.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import static com.c9Pay.userservice.constant.BearerConstant.BEARER_PREFIX;
import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;

/**
 * JWT 토큰 유틸리티 클래스입니다. JWT 토큰 생성, 검증 등의 기능을 제공한다.
 */
@Slf4j
@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.time}")
    private long EXPIRATION_TIME;

    private Key key;

    @PostConstruct
    public void createSecureKey(){
        key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    /**
     * Http 요청 cookie에서  jwt 인증을 위한 토큰 값을 가져온다.
     *
     * @param request HTTP 요청 객체
     * @return 추출한 JWT 토큰 문자열
     */
    public String getToken(HttpServletRequest request){
        try{
            Optional<Cookie> cookies = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(AUTHORIZATION_HEADER))
                    .filter(cookie-> cookie.getValue().startsWith(BEARER_PREFIX))
                    .findFirst();

            if(cookies.isPresent()) {
                String bearerToken = cookies.get().getValue();
                if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX))
                    return bearerToken.substring(7);
            }
        }catch (Exception e){

            return null;
        }

        return null;
    }

    public Key getKey() {
        return key;
    }


    /**
     * 사용자 ID를 기반으로 JWT 토큰을 생성한다.
     *
     * @param id 사용자의 ID
     * @return 생성된 JWT 토큰 문자열
     */
    public String generateToken(String id){
        Claims claims = Jwts.claims();
        claims.put("type", "user");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plus(EXPIRATION_TIME, ChronoUnit.MILLIS)))
                .signWith(key).compact();
    }

    /**
     * 주어진 JWT 토큰의 유효성을 검사한다,
     *
     * @param token 검사할 JWT 토큰 문자열
     * @return 유효한 토큰일 경우 true, 그렇지 않을 경우 false 반환
     */
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            log.info("Invalid Jwt signature");
            log.trace("Invalid Jwt signature: trace{}", e);
        }catch (MalformedJwtException e){
            log.info("Invalid Jwt token");
            log.trace("Invalid Jwt token trace: {}", e);
        }catch (ExpiredJwtException e){
            log.info("Expired Jwt token");
            log.trace("Expired Jwt token trace: {}", e);
        }catch (UnsupportedJwtException e){
            log.info("Unsupported Jwt token");
            log.trace("Unsupported Jwt token trace: {}", e);
        }catch (IllegalArgumentException e){
            log.info("Jwt token compact of handler are invalid");
            log.trace("Jwt token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

    /**
     * JWT 토큰에서 사용자 DB ID를 추출한다.
     *
     * @param token JWT 토큰 문자열
     * @return 추출한 사용자 ID 문자열
     */
    public String extractId(String token){
        return extractClaim(token, Claims::getSubject);}

    /**
     * 주어진 JWT 토큰의 유효성을 검사하고, 해당 토큰이 주어진 사용자의 토큰인지도 검사한다.
     *
     * @param token        검사할 JWT 토큰 문자열
     * @param userDetails 검사할 사용자의 UserDetails 객체
     * @return 주어진 토큰이 유효하고 주어진 사용자의 토큰일 경우 true, 그렇지 않을 경우 false 반환
     */
    public Boolean validateToken(String token, UserDetails userDetails){
        final var id = extractId(token);
        return id.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 주어진 JWT 토큰에서 모든 클레임 정보를 추출한다.
     *
     * @param token 추출할 JWT 토큰 문자열
     * @return 토큰에서 추출한 클레임 정보를 포함한 Claims 객체
     */
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 주어진 JWT 토큰에서 특정 클레임을 추출한다.
     *
     * @param token           추출할 JWT 토큰 문자열
     * @param claimsResolver 특정 클레임을 추출하는 함수형 인터페이스
     * @param <T>             추출할 클레임의 타입
     * @return 추출한 클레임 정보
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    /**
     * 주어진 JWT 토큰에서 만료 시간을 추출한다.
     *
     * @param token 추출할 JWT 토큰 문자열
     * @return 토큰의 만료 시간
     */
    private Date extractExpiration(String token){ return extractClaim(token, Claims::getExpiration);}

    /**
     * 주어진 JWT 토큰이 만료되었는지 검사한다.
     *
     * @param token 검사할 JWT 토큰 문자열
     * @return 토큰이 만료되었을 경우 true, 그렇지 않을 경우 false 반환
     */
    public Boolean isTokenExpired(String token){ return extractExpiration(token).before(new Date());}

}
