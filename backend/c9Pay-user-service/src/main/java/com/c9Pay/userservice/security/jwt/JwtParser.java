package com.c9Pay.userservice.security.jwt;

import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.web.exception.exceptions.IllegalTokenDetailException;
import com.c9Pay.userservice.web.mvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtParser {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    private String parseToken(String token) {
        if(token.length() >= 7){
            String parsedToken = token.substring(7);
            return jwtTokenUtil.extractId(parsedToken);
        }
        throw new IllegalTokenDetailException();
    }


    public User getUserByToken(String token) {
        String ID = parseToken(token);
        return userService
                .findById(Long.valueOf(ID));
    }

    public String getSerialNumberByToken(String token){
        return getUserByToken(token).getSerialNumber().toString();
    }
    public Long getIdByToken(String token){
        return getUserByToken(token).getId();
    }
}
