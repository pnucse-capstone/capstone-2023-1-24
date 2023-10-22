package com.c9Pay.userservice.web.mvc.controller;

import com.c9Pay.userservice.data.dto.credit.AccountDetails;
import com.c9Pay.userservice.data.dto.credit.ChargeForm;
import com.c9Pay.userservice.data.dto.user.LoginForm;
import com.c9Pay.userservice.data.dto.user.UserDto;
import com.c9Pay.userservice.web.client.CreditClient;
import com.c9Pay.userservice.web.mvc.service.UserService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;

import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest
class CreditControllerTest {

    @Autowired
    UserController userController;

    @Autowired
    CreditController creditController;
    @Autowired
    LoginController loginController;
    @Autowired
    CreditClient creditClient;
    @Autowired
    UserService userService;
    @Test
    public void testCreditCharge() {
        //given
        UserDto dto = new UserDto("aa", "testbb", "Cc", "oko@kooko.ok");
        ChargeForm form = new ChargeForm(3000L);
        LoginForm login = new LoginForm("testbb", "Cc");
        userController.signUp(dto);
        MockHttpServletResponse response = new MockHttpServletResponse();
        loginController.login(login, response);
        //when
        Cookie cookie = response.getCookie(AUTHORIZATION_HEADER);
        String token = cookie != null ? cookie.getValue(): null;
        String serialNumber = userService.findByUserId("testbb").getSerialNumber().toString();
        ResponseEntity<?> responseEntity = creditController.chargeCredit(form, token);
        ResponseEntity<AccountDetails> accountCredit = creditClient.getAccount(serialNumber);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(accountCredit.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(accountCredit.getBody().getCredit())
                .isEqualTo(3000L);
        deleteAccount(serialNumber);
     }

    private void deleteAccount(String serialNumber){
        ResponseEntity<?> deleteResponse = creditClient.deleteAccount(serialNumber);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(OK);
    }

}