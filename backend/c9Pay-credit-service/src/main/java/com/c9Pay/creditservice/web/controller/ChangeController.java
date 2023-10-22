package com.c9Pay.creditservice.web.controller;

import com.c9Pay.creditservice.data.dto.charge.ChargeAmount;
import com.c9Pay.creditservice.data.dto.charge.ChargeForm;
import com.c9Pay.creditservice.data.dto.log.ChargeLogDto;
import com.c9Pay.creditservice.web.docs.ChangeControllerDocs;
import com.c9Pay.creditservice.web.service.AccountService;
import com.c9Pay.creditservice.web.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class ChangeController implements ChangeControllerDocs {

    private final AccountService accountService;
    private final LogService logService;
    @Override
    @PostMapping("/{identifier}/load")
    public ResponseEntity<?> loadCredit(@PathVariable String identifier, @RequestBody ChargeForm form, HttpServletRequest request){
        accountService.loadCredit(identifier, form.getQuantity());
        logService.createChargeLog(new ChargeLogDto(identifier,form.getQuantity()));
        log.info("충전 금액: {}", form.getQuantity());
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/{from}/transfer/{to}")
    public ResponseEntity<?> transfer(@PathVariable(name= "to") String to, @PathVariable(name="from") String from,
                                      @RequestBody ChargeAmount chargeAmount){
        accountService.transfer(from, to, chargeAmount.getCreditAmount());
        logService.transferLog(new ChargeLogDto(from, to, chargeAmount.getCreditAmount()));
        return ResponseEntity.ok("transfer successful");
    }
}
