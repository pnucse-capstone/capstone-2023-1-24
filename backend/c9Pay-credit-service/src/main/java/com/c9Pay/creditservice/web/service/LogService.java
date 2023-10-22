package com.c9Pay.creditservice.web.service;

import com.c9Pay.creditservice.data.dto.log.ChargeLogDto;
import com.c9Pay.creditservice.data.entity.Account;
import com.c9Pay.creditservice.data.entity.Log;
import com.c9Pay.creditservice.web.repository.AccountRepository;
import com.c9Pay.creditservice.web.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;
    private final AccountRepository accountRepository;
    @Transactional
    public void createChargeLog(ChargeLogDto dto){
        String source = dto.getSource();
        Account origin = accountRepository.findAccountBySerialNumber(source).orElseThrow();
        Log log = Log.builder()
                .account(origin)
                .source(origin.getSerialNumber())
                .amount(dto.getAmount())
                .destination("N/A")
                .description("입금")
                .build();
        logRepository.save(log);
    }

    @Transactional
    public void transferLog(ChargeLogDto dto){
        String source = dto.getSource();
        String destination = dto.getDestination();
        Account origin = accountRepository.findAccountBySerialNumber(source).orElseThrow();
        Account dest  = accountRepository.findAccountBySerialNumber(destination).orElseThrow();
        Log sourceLog = Log.builder()
                .description("송금")
                .destination(destination)
                .amount(-1*dto.getAmount())
                .account(origin)
                .source(origin.getSerialNumber())
                .build();
        Log destLog = Log.builder()
                .description("입금")
                .source(dest.getSerialNumber())
                .destination(source)
                .amount(dto.getAmount())
                .account(dest)
                .build();
        logRepository.save(sourceLog);
        logRepository.save(destLog);
    }

}
