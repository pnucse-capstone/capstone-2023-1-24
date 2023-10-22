package com.c9pay.authservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SerialNumber {
    @Id @GeneratedValue(generator = "UUID")
    private UUID serialNumber;
}
