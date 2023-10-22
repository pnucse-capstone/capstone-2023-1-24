package com.c9Pay.creditservice.web.controller;

import com.c9Pay.creditservice.data.dto.charge.AccountDetails;
import com.c9Pay.creditservice.data.entity.Account;
import com.c9Pay.creditservice.web.docs.CreditControllerDocs;
import com.c9Pay.creditservice.web.service.AccountService;
import com.c9Pay.creditservice.web.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class CreditController implements CreditControllerDocs {

    private final AccountService accountService;

    private final LogService logService;
    @Override
    @PostMapping("/{serialNumber}")
    public ResponseEntity<?> createAccount(@PathVariable String serialNumber){
       log.info("Starting registration for a new credit account");
       accountService.createNewAccount(serialNumber);
       return ResponseEntity.ok().build();
   }

   @Override
   @DeleteMapping("/{serialNumber}")
   public ResponseEntity<?> deleteAccount(@PathVariable String serialNumber){
       accountService.deleteAccount(serialNumber);
       return ResponseEntity.ok().build();
   }
   @Override
   @GetMapping(value = "/{serialNumber}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDetails> getAccount(@PathVariable String serialNumber){
       Account findAccount= accountService.getAccountInfo(serialNumber);
       AccountDetails details = new AccountDetails();
       details.setCredit(findAccount.getCreditAmount());
       details.setSerialNumber(findAccount.getSerialNumber());
       return ResponseEntity.ok(details);
   }


}
