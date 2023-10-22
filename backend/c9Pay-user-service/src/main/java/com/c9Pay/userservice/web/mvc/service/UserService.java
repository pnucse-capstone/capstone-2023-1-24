package com.c9Pay.userservice.web.mvc.service;

import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.security.jwt.JwtTokenUtil;
import com.c9Pay.userservice.web.exception.exceptions.DuplicatedUserException;
import com.c9Pay.userservice.web.exception.exceptions.UserNotFoundException;
import com.c9Pay.userservice.data.dto.user.UserUpdateParam;
import com.c9Pay.userservice.web.mvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    /**
     * 새로운 사용자를 등록한다.
     *
     * @param user 등록할 사용자 객체
     * @throws DuplicatedUserException 이미 존재하는 사용자 ID일 경우 발생하는 예외
     */
    @Transactional
    public void signUp(User user){
        if(!validateDuplicateUserId(user.getUserId()))
            throw new DuplicatedUserException();
        String encodedPassword= passwordEncoder.encode(user.getPassword());
        user.encodePassword(encodedPassword);
        userRepository.save(user);
    }

    /**
     * 사용자 인증을 처리하고 인증 토큰을 반환한다.
     *
     * @param userId 사용자 아이디
     * @param password 사용자 비밀번호
     * @return 인증된 사용자의 인증 토콘
     * @throws UserNotFoundException 사용자가 존재하지 않을 경우 발생하는 예외
     */
    public String authenticate(String userId, String password){
        User findUser = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        findUser.getId().toString(),
                        password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenUtil.generateToken(findUser.getId().toString());
    }

    /**
     * 사용자 ID를 기반으로 사용자를 조회한다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 조회된 사용자 객체
     * @throws UserNotFoundException 사용자가 존재하지 않을 경우 발생하는 예외
     */
    public User findByUserId(String userId){
        return userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
    }

    /**
     * 주어진 사용자 ID가 중복되는지 검사한다.
     *
     * @param userId 검사할 사용자 ID
     * @return 중복되지 않으면 true, 중복되면 false 반환
     */
    public boolean validateDuplicateUserId(String userId){
        return userRepository.findByUserId(userId).isEmpty();
    }

    /**
     * 주어진 ID를 사용하여 사용자를 삭제한다.
     *
     * @param id 삭제할 사용자의 ID
     * @throws UserNotFoundException 사용자가 존재하지 않을 경우 발생하는 예외
     */
    @Transactional
    public void deleteUserById(Long id){
        userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
    }


    /**
     * 주어진 ID를 사용하여 사용자 정보를 업데이트한다.
     *
     * @param id 업데이트할 사용자의 ID
     * @param param 업데이트할 사용자 정보를 담은 UserUpdateParam 객체
     * @throws DuplicatedUserException 동일한 사용자 ID가 이미 존재할 경우 발생하는 예외
     */
    @Transactional
    public void updateUserById(Long id, UserUpdateParam param){
        User findUser = findById(id);
        String encode = passwordEncoder.encode(param.getPassword());
        if (!validateDuplicateUserId(param.getUserId()))
            throw new DuplicatedUserException();
        param.setPassword(encode);
        findUser.updateUser(param);
    }

    /**
     * 주어진 시리얼 번호를 사용하여 사용자를 조회한다.
     *
     * @param serialNumber 조회할 사용자의 객체식별번호
     * @return 조회된 사용자 객체
     * @throws UserNotFoundException 사용자가 존재하지 않을 경우 발생하는 예외
     */
    public User findBySerialNumber(String serialNumber){
        return userRepository.findBySerialNumber(UUID.fromString(serialNumber))
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     * 주어진 ID를 사용하여 사용자를 조회한다.
     *
     * @param id 조회할 사용자의 ID
     * @return 조회된 사용자 객체
     * @throws UserNotFoundException 사용자가 존재하지 않을 경우 발생하는 예외
     */
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }


    /**
     * 주어진 이름을 사용하여 사용자를 삭제한다.
     *
     * @param name 삭제할 사용자의 이름
     */
    public void deleteOneByName(String name){
        userRepository.deleteByUsername(name);
    }
}
