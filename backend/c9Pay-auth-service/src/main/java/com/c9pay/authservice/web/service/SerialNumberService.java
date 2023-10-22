package com.c9pay.authservice.web.service;

import com.c9pay.authservice.entity.SerialNumber;
import com.c9pay.authservice.web.repository.SerialNumberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SerialNumberService {
    private final SerialNumberRepository serialNumberRepository;

    public UUID createSerialNumber() {
        SerialNumber serialNumber = serialNumberRepository.save(new SerialNumber());
        return serialNumber.getSerialNumber();
    }

    public boolean verifySerialNumber(UUID serialNumber) {
        return serialNumberRepository.findById(serialNumber).isPresent();
    }
}
