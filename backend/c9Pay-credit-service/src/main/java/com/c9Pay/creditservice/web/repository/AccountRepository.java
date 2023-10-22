package com.c9Pay.creditservice.web.repository;
import java.util.Optional;

import com.c9Pay.creditservice.data.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountBySerialNumber(String serialNumber);

}
