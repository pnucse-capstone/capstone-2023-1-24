package com.c9Pay.userservice.security.jwt;

import com.c9Pay.userservice.constant.CookieConstant;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtTokenUtilTest {


    @Autowired JwtTokenUtil jwtTokenUtil;
    @Test
    @DisplayName("generate new jwt")
    public void testGenerateNewToken() {
        //given
        String dummyText = "dummy-text";
        //when
        String token = jwtTokenUtil.generateToken(dummyText);
        //then
        assertTrue(jwtTokenUtil.validateToken(token));
     }

     @Test
     @DisplayName("time validate token")
    public void testValidateToken() throws InterruptedException {
        //given
        String dummyText  ="100000000";
        Key key = jwtTokenUtil.getKey();
         //when
         String token = Jwts.builder()
                 .setSubject(dummyText)
                 .setIssuedAt(new Date(System.currentTimeMillis()))
                 .setExpiration(Date.from(Instant.now().plus(10000, ChronoUnit.MILLIS)))
                 .signWith(key).compact();
         //then
         boolean valid = jwtTokenUtil.validateToken(token);
         assertTrue(valid);
         Thread.sleep(10000);
         boolean invalid = jwtTokenUtil.validateToken(token);
         assertFalse(invalid);
     }


     @Test
    @DisplayName("Extract Subject")
    public void testExtractSubject(){

        String id = "12316545642132349879";
        //given
         String token = jwtTokenUtil.generateToken(id);
         //when
         String extractToken = jwtTokenUtil.extractId(token);
         //then
         Assertions.assertThat(id).isEqualTo(extractToken);
     }

}