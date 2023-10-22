package com.c9Pay.userservice.web.mvc.controller;

import com.c9Pay.userservice.data.dto.user.UserDto;
import com.c9Pay.userservice.data.dto.user.UserResponse;
import com.c9Pay.userservice.data.dto.user.UserUpdateParam;
import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.security.jwt.JwtTokenUtil;
import com.c9Pay.userservice.web.client.AuthClient;
import com.c9Pay.userservice.web.client.CreditClient;
import com.c9Pay.userservice.web.exception.exceptions.DuplicatedUserException;
import com.c9Pay.userservice.web.exception.exceptions.UserNotFoundException;
import com.c9Pay.userservice.web.mvc.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@SpringBootTest
class UserControllerTest {

    @Autowired private AuthClient authClient;

    @Autowired private CreditClient creditClient;

    @Autowired private JwtTokenUtil jwtTokenUtil;

    @Autowired private UserService userService;

    @Autowired private UserController userController;

    @Autowired private PasswordEncoder passwordEncoder;
    @Test
    @DisplayName("sign-up")
    public void testSingUp(){
        //given
        UserDto form = new UserDto("test-dummy", "test-id",
                "test-password","test@email.com");
        //when
        ResponseEntity<?> signUpResponse = userController.signUp(form);
        User findUser = userService
                .findBySerialNumber(requireNonNull(signUpResponse.getBody())
                        .toString());

        //then
        assertThat(signUpResponse.getStatusCode()).isEqualTo(OK);
        assertThat(findUser.getUserId()).isEqualTo("test-id");
        assertThat(findUser.getUsername()).isEqualTo("test-dummy");
        assertTrue(passwordEncoder.matches("test-password", findUser.getPassword()));
        assertThat(findUser.getEmail()).isEqualTo("test@email.com");
        validateSerialNumber(findUser.getSerialNumber().toString());
        deleteAccount(findUser.getSerialNumber().toString());
        userService.deleteUserById(findUser.getId());
    }

    @Test
    @DisplayName("Check Duplicate User/failure test")
    public void testDuplicatedSignUp(){
        //given
        UserDto one = new UserDto("testA", "test1", "test1", "test@test.com");
        UserDto two = new UserDto("testB", "test1", "test2","test@test.ac.kr");

        //when
        ResponseEntity<?> first = userController.signUp(one);
        User testA = userService.findByUserId("test1");
        //then
        deleteAccount(testA.getSerialNumber().toString());
        assertThat(first.getStatusCode()).isEqualTo(OK);
        assertFalse(passwordEncoder.matches("test2", testA.getPassword()));
        assertThrows(DuplicatedUserException.class, ()->
                userService.signUp(two.toEntity(UUID.randomUUID())));
        userService.deleteOneByName(testA.getUsername());
    }

    @Test
    @DisplayName("get user information")
    public void testGetUserDetails(){
        //given
        UserDto dto = new UserDto("testA","test", "testB", "test@test.test");
        userController.signUp(dto);
        User findUser = userService.findByUserId("test");
        //when
        String token = jwtTokenUtil.generateToken(String.valueOf(findUser.getId()));
        ResponseEntity<?> response = userController
                .getUserDetail( "Bearer+"+  token);
        UserResponse body = (UserResponse) response.getBody();
        //then
        assertTrue(jwtTokenUtil.validateToken(token));
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(requireNonNull(body).getUsername()).isEqualTo("testA");
        assertThat(requireNonNull(body).getUserId()).isEqualTo("test");
    }

    @Test
    @DisplayName("update user test")
    public void testUpDateUser(){
        //given
        UserDto dto = new UserDto("testA", "test123",
                "testB", "test@test.test");
        userController.signUp(dto);
        User findUser = userService.findByUserId("test123");
        //when
        String token = jwtTokenUtil.generateToken(String.valueOf(findUser.getId()));
        ResponseEntity<?> response = userController.getUserDetail("Bearer+"+ token);
        UserResponse body = (UserResponse) response.getBody();
        //then
        assertTrue(jwtTokenUtil.validateToken(token));
        assertTrue(jwtTokenUtil.validateToken(token));
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(requireNonNull(body).getUserId()).isEqualTo("test123");
        assertThat(requireNonNull(body).getUsername()).isEqualTo("testA");
        assertThat(requireNonNull(body).getEmail()).isEqualTo("test@test.test");
    }


    @Test
    @DisplayName("Delete user")
    public void testDeleteUser(){
        //given
        UserDto dto = new UserDto("TEST", "TEST","TEST11", "korea@japan.china");
        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<?> responseEntity = userController.signUp(dto);
        String serialNumber = requireNonNull(responseEntity.getBody()).toString();
        User findUser=  userService.findByUserId("TEST");
        String token = jwtTokenUtil.generateToken(String.valueOf(findUser.getId()));
        HttpServletResponse response = new MockHttpServletResponse();
        //when
        ResponseEntity<?> deleteResponse = userController.deleteUser("Bearer+" + token, response);
        //then
        assertThat(deleteResponse.getStatusCode()).isEqualTo(OK);
        assertThrows(UserNotFoundException.class,() ->userService.findByUserId("TEST"));
        findAccount(serialNumber);
    }

    @Test
    @DisplayName("update user")
    @Transactional
    public void testUpdateUser(){
        //given
        UserDto dto = new UserDto("Dummy", "Dummy","TEST11", "korea@japan.china");
        ResponseEntity<?> responseEntity = userController.signUp(dto);
        String serialNumber = requireNonNull(responseEntity.getBody()).toString();
        User findUser=  userService.findByUserId("Dummy");
        String token = jwtTokenUtil.generateToken(String.valueOf(findUser.getId()));
        HttpServletResponse response = new MockHttpServletResponse();
        UserUpdateParam param = new UserUpdateParam("aa","bb", "cc", "dd");
        //when
        ResponseEntity<?> updateResponse = userController.updateUserInfo("Bearer+" + token, param,response);
        User updatedUser = userService.findByUserId("dd");
        //then
        assertThat(updateResponse.getStatusCode()).isEqualTo(OK);
        assertTrue(updatedUser.equals(param));
        assertTrue(passwordEncoder.matches("bb", updatedUser.getPassword()));
        deleteAccount(serialNumber);
        userService.deleteOneByName("aa");
        assertThrows(UserNotFoundException.class,()-> userService.findByUserId("dd"));
        findAccount(serialNumber);
    }


    @Test
    @DisplayName("get serial number")
    public void testGetSerialNumber(){
        //given
        UserDto dto = new UserDto("Dummy", "Dummy2","TEST11", "korea@japan.china");
        ResponseEntity<?> signUpResponse = userController.signUp(dto);
        User findUser = userService.findByUserId("Dummy2");
        String token = jwtTokenUtil.generateToken(String.valueOf(findUser.getId()));
        //when
        ResponseEntity<?> serialNumber = userController.getSerialNumber("Bearer+" + token);
        //then
        assertThat(signUpResponse.getStatusCode()).isEqualTo(OK);
        assertThat(serialNumber.getStatusCode()).isEqualTo(OK);
        assertThat(serialNumber.getBody()).isEqualTo(findUser.getSerialNumber().toString());
        userService.deleteUserById(findUser.getId());
        deleteAccount(requireNonNull(serialNumber.getBody()).toString());
    }
    @Test
    @DisplayName("user id duplication check/ success")
    public void testDuplication() {
        //given
        //when
        ResponseEntity<?> response = userController.checkDuplicated("newId");
        //then
        assertThat(response.getStatusCode()).isEqualTo(OK);
     }

     @Test
     @DisplayName("user id duplication check/ fail")
     public void testDuplication2() {
         //given
         UserDto dto = new UserDto("Dummy", "Dummy","TEST11", "korea@japan.china");
         User savedUser = dto.toEntity(UUID.randomUUID());
         userService.signUp(savedUser);
         //when
         ResponseEntity<?> fail = userController.checkDuplicated("Dummy");
         //then
         assertThat(fail.getStatusCode()).isEqualTo(BAD_REQUEST);
         userService.deleteOneByName("Dummy");
      }

    private void deleteAccount(String serialNumber){
        ResponseEntity<?> deleteResponse = creditClient.deleteAccount(serialNumber);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(OK);
    }
    private void validateSerialNumber(String id){
        ResponseEntity<?> responseEntity = authClient.validateSerialNumber(id);
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
    }

    private void findAccount(String serialNumber) {
        try{
            creditClient.getAccount(serialNumber);
            fail();
        }catch (Exception ignored){}
    }
}