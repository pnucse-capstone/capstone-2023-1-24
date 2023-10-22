package com.c9pay.authservice.web.controller;

import com.c9pay.authservice.token.KeyManager;
import com.c9pay.authservice.token.TokenProvider;
import com.c9pay.authservice.web.dto.ExchangeToken;
import com.c9pay.authservice.web.dto.SerialNumberResponse;
import com.c9pay.authservice.web.service.SerialNumberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/serial-number")
public class SerialController {
    private final SerialNumberService serialNumberService;
    private final TokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<SerialNumberResponse> createSerialNumber() {
        log.info("create new Serial number");
        UUID serialNumber = serialNumberService.createSerialNumber();

        return ResponseEntity.ok(new SerialNumberResponse(serialNumber));
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<?> verifySerialNumber(@PathVariable("serialNumber") UUID serialNumber) {

        boolean isValid = serialNumberService.verifySerialNumber(serialNumber);

        if (isValid) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{serialNumber}/exchange")
    public ResponseEntity<?> getExchangeToken(@PathVariable("serialNumber") UUID serialNumber) {
        boolean isValid = serialNumberService.verifySerialNumber(serialNumber);

        if (isValid) {
            ExchangeToken exchangeToken = tokenProvider.generateToken(serialNumber.toString());
            return ResponseEntity.ok(exchangeToken);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
