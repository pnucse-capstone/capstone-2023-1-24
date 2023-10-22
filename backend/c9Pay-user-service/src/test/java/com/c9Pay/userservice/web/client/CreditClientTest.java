package com.c9Pay.userservice.web.client;

import com.c9Pay.userservice.data.dto.credit.AccountDetails;
import com.c9Pay.userservice.data.dto.credit.ChargeForm;
import com.c9Pay.userservice.web.mvc.controller.CreditController;
import com.c9Pay.userservice.web.mvc.controller.UserController;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
class CreditClientTest {


    /**
     * Credit-service 기동 후 테스트 돌릴 것.
     */

    @Autowired CreditClient creditClient;

    @Test
    @DisplayName("Send an account creation request to the credit service.")
    public void testCreateAccount(){
        ResponseEntity<?> account = creditClient.createAccount("test-id");
        assertThat(account.getStatusCode()).isEqualTo(HttpStatus.OK);
        deleteAccount("test-id");
    }

    @Test
    @DisplayName("Sending a request to charge 5000 credits to the credit service")
    public void testLoad(){
        String serialNumber= "test-serial-number";
        ResponseEntity<?> createResponse = creditClient.createAccount(serialNumber);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<?> response = creditClient.loadCredit(serialNumber, new ChargeForm(5000L));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<AccountDetails> account = creditClient.getAccount(serialNumber);
        assertThat(account.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(account.getBody()).getCredit()).isEqualTo(5000L);
        assertThat(account.getBody().getSerialNumber()).isEqualTo(serialNumber);
        deleteAccount(serialNumber);
    }


    @Test
    @DisplayName("Error test when requesting to create an account with duplicate object identification numbers")
    public void testDuplicatedSerialNumber(){
        String duplicateName = "duplicated-id";
        ResponseEntity<?> first = creditClient.createAccount(duplicateName);
        assertThat(first.getStatusCode()).isEqualTo(HttpStatus.OK);
        try {
            creditClient.createAccount(duplicateName);
        } catch (FeignException e) {
            assertThat(e.status()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
        deleteAccount(duplicateName);
    }


    private void deleteAccount(String serialNumber){
        ResponseEntity<?> deleteResponse = creditClient.deleteAccount(serialNumber);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}