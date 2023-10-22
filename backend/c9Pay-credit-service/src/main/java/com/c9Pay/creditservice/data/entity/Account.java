package com.c9Pay.creditservice.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Id @GeneratedValue
    @Column(name ="account_id")
    private Long id;

    @Column(unique = true,  nullable = false, name="serial_number")
    private String serialNumber;

    @Column(nullable = false)
    private Long creditAmount;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Log> logs = new ArrayList<>();

    public void incrementCredit(Long credit){
        this.creditAmount += credit;
    }
    public void decrementCredit(Long credit){
        if(this.creditAmount < credit) throw new IllegalStateException();
        else this.creditAmount -= credit;
    }
}
