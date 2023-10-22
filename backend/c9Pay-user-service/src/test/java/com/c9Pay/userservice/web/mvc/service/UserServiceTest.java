package com.c9Pay.userservice.web.mvc.service;

import com.c9Pay.userservice.data.dto.user.UserUpdateParam;
import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.web.exception.exceptions.DuplicatedUserException;
import com.c9Pay.userservice.web.exception.exceptions.UserNotFoundException;
import com.c9Pay.userservice.web.mvc.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserServiceTest {

    @Autowired UserRepository userRepository;
    @Autowired UserService userService;

    UUID serialNumber;

    User user;

    @BeforeEach
    void setUp(){
        serialNumber = UUID.randomUUID();
        user = User.builder()
                .userId(serialNumber.toString())
                .password("test")
                .email("test@test.test")
                .serialNumber(serialNumber)
                .username(serialNumber.toString())
                .build();
    }
    @Test
    @DisplayName("회원가입 성공 테스트")
    void signUpTest() {
        //when
        userService.signUp(user);
        //then
        assertTrue(user.equals(userRepository.findBySerialNumber(serialNumber).get()));
     }


     @Test
     @DisplayName("회원가입 실패 테스트")
     void signUpFailTest() {
        //given
         User omission = User.builder()
                 .password("test")
                 .username("test")
                 .serialNumber(serialNumber)
                 .email("test@test.test")
                 .build();

         //when
         userService.signUp(user);
         //then
         assertThrows(DuplicatedUserException.class, () -> userService.signUp(user));
         assertThrows(DataIntegrityViolationException.class,() -> userService.signUp(omission));
      }


      @Test
      @DisplayName("아이디 중복 확인 테스트")
      void checkDuplicateUserIdTest() {
          //given
          String userId = "userId";
          //when
          userService.signUp(user);
          //then
          assertTrue(userService.validateDuplicateUserId(userId));
          assertFalse(userService.validateDuplicateUserId(serialNumber.toString()));
    }
    @Test
    @DisplayName("회원 이름으로 삭제 테스트")
    @Transactional
    void deleteUserByNameTest() {
        //given
        User target = User.builder()
                .userId("aa")
                .username("aa")
                .password("aa")
                .serialNumber(serialNumber)
                .email("aa@bb.cc")
                .build();
        userService.signUp(target);
        //when
        userService.deleteOneByName(target.getUsername());
        //then
        assertThrows(UserNotFoundException.class, () -> userService.findByUserId("aa"));
     }

     @Test
     @DisplayName("테이블 ID로 회원 삭제 테스트")
     void deleteByUserId() throws Exception{
         //given
         userRepository.save(user);
         //when
         userService.deleteUserById(user.getId());
         //then
         assertThrows(UserNotFoundException.class, ()-> userService.findByUserId(serialNumber.toString()));
      }

     @Test
     @DisplayName("회원 개체식별번호로 엔티디 검색 테스트")
     void findBySerialNumberTest(){
        //given
         userRepository.save(user);
         //when
         User findUser = userService.findBySerialNumber(serialNumber.toString());
         //then
         assertTrue(user.equals(findUser));
     }

     @Test
     @DisplayName("회원 아이디로 엔테티 검색 테스트")
     void findByUserIdTest() throws Exception{
         //given
         userRepository.save(user);
         //when
         User findUser = userService.findByUserId(serialNumber.toString());
         //then
         assertTrue(user.equals(findUser));
      }

      @Test
      @DisplayName("테이블 아이디를 통해 엔티티 검색 테스트")
      void findByIdTest() throws Exception{
        //given
        userRepository.save(user);
        //when
        User findUser = userService.findById(user.getId());
        //then
          assertTrue(user.equals(findUser));
    }

    @Test
    @DisplayName("사용자 엔티티 수정 테스트")
    @Transactional
    void updateUserTest(){
        //given
        UserUpdateParam param = new UserUpdateParam("aa", "aa", "AA@BB.CC", "cc");
        //when
        userRepository.save(user);
        assertFalse(user.equals(param));
        User byUserId = userService.findByUserId(user.getUserId());
        userService.updateUserById(user.getId(), param);

        //then
        assertTrue(user.equals(param));
     }
}