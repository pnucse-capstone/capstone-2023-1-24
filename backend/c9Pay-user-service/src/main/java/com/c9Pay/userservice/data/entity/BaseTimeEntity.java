package com.c9Pay.userservice.data.entity;

import com.c9Pay.userservice.data.dto.user.UserUpdateParam;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
/**
 * 생성일시와 수정일시를 관리하는 추상 엔티티 클래스
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    /**
     * UserUpdateParam 객체와 동등성을 비교한다.
     *
     * @param user UserUpdateParam 객체
     * @return 동등하면 true, 그렇지 않으면 false
     */
    public abstract boolean equals(UserUpdateParam user);

    public abstract boolean equals(User compare);
}
