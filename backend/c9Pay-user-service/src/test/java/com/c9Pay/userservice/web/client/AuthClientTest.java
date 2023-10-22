package com.c9Pay.userservice.web.client;


import com.c9Pay.userservice.data.dto.auth.SerialNumberResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthClientTest {


    @Autowired private AuthClient authClient;
    @Test
    @DisplayName("Getting object identification numbers from authentication services")
    public void testGetSerialNumber() {
        ResponseEntity<SerialNumberResponse> response = authClient.getSerialNumber();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

    }

    @Test
    @DisplayName("validate a generated serial number")
    public void verify(){
        String serialNumber = Objects.requireNonNull(authClient.getSerialNumber()
                        .getBody()).getSerialNumber().toString();

        log.info("Generated Serial Number is :{} ",serialNumber);
        ResponseEntity<?> response = authClient.validateSerialNumber(serialNumber);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}