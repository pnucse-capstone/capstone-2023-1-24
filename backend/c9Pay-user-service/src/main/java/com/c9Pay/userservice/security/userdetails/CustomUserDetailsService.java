package com.c9Pay.userservice.security.userdetails;


import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.web.mvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 정보를 로드하는 커스텀 UserDetailsService 클래스
 */
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    /**
     * 주어진 사용자 아이디를 사용하여 사용자 정보를 로드한다.
     *
     * @param userId 사용자 아이디
     * @return UserDetails 객체
     * @throws UsernameNotFoundException 주어진 아이디에 해당하는 사용자 정보를 찾지 못한 경우 발생
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> findUser = userRepository.findById(Long.valueOf(userId));
        if(findUser.isPresent()){
            User user = findUser.get();
            List<GrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority("USER"));
            return new CustomUserDetails(user.getId().toString(), user.getPassword(), authorityList);
        }
        return null;
    }

}

