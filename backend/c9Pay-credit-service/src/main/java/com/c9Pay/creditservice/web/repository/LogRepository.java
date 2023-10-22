package com.c9Pay.creditservice.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.c9Pay.creditservice.data.entity.Log;
public interface LogRepository extends JpaRepository<Log, Long> {
}
