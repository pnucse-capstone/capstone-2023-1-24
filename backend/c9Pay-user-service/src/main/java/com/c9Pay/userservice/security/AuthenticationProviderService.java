package com.c9Pay.userservice.security;

import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.web.exception.exceptions.UserNotFoundException;
import com.c9Pay.userservice.web.mvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.c9Pay.userservice.constant.BearerConstant.BEARER_PREFIX;


/**
 * 커스텀 인증 제공자 (AuthenticationProvider) 서비스
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationProviderService implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 인증을 수행한다.
     *
     * @param authentication 인증 객체
     * @return 인증된 사용자 정보
     * @throws AuthenticationException 인증 예외
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id =authentication.getName();
        String password= authentication.getCredentials().toString();
        User user = userRepository.findById(Long.valueOf(id))
                .orElseThrow(UserNotFoundException::new);
        return checkPassword(user, password, passwordEncoder);
    }


    /**
     * 해당 제공자가 해당 인증 객체를 지원하는지 확인한다.
     *
     * @param authentication 인증 객체
     * @return 해당 인증 객체를 지원하면 true, 그렇지 않으면 false
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * 패스워드를 확인하고 인증을 생성한다.
     *
     * @param user         사용자 정보
     * @param rawPassword  입력받은 패스워드
     * @param encoder      패스워드 인코더
     * @return 인증 객체
     * @throws BadCredentialsException 패스워드가 일치하지 않을 때 발생하는 예외
     */
    private Authentication checkPassword(User user, String rawPassword, PasswordEncoder encoder){
        if(encoder.matches(rawPassword,user.getPassword())){
            return new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(BEARER_PREFIX)));
        }else throw new BadCredentialsException("103");
    }
}