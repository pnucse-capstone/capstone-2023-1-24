package com.c9pay.authservice.controller;

import com.c9pay.authservice.entity.SerialNumber;
import com.c9pay.authservice.web.repository.SerialNumberRepository;
import com.c9pay.authservice.web.service.SerialNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SerialControllerTest {
    @Mock
    SerialNumberRepository serialNumberRepository;
    @InjectMocks
    SerialNumberService service;

    UUID uuid;
    SerialNumber serialNumber;

    @BeforeEach
    public void setUp() {
        uuid = UUID.randomUUID();
        serialNumber = new SerialNumber(uuid);
    }

    @Test
    public void 개체식별번호_생성() {
        // given
        given(serialNumberRepository.save(any())).willReturn(serialNumber);

        // when 
        UUID created = service.createSerialNumber();

        // then
        assertEquals(uuid, created);
    }

    @Test
    public void 개체식별번호_검증() {
        // given
        UUID badUuid = UUID.randomUUID();

        given(serialNumberRepository.findById(any())).willAnswer((a)->{
            Object argument = a.getArgument(0);
            if (argument == uuid) return Optional.of(serialNumber);
            else return Optional.empty();
        });

        // when
        boolean valid = service.verifySerialNumber(uuid);
        boolean invalid = service.verifySerialNumber(badUuid);

        // then
        assertTrue(valid);
        assertFalse(invalid);
    }
}