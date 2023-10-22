package com.c9Pay.creditservice.data.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Log {

    @Id @GeneratedValue
    @Column(name = "log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
    @CreatedDate
    private LocalDateTime chargeTime;


    @Column(nullable = false)
    private String source;
    @Column(nullable = false)
    private String destination;
    @Column(nullable = false)
    private Long amount;

    @Column(nullable =false)
    private String description;

}
