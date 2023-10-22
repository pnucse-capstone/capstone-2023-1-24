package com.c9Pay.userservice.data.entity;

import com.c9Pay.userservice.data.dto.user.UserUpdateParam;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;


/**
 * 사용자 정보를 저장하는 엔티티 클래스
 */
@Entity
@Getter
@Table(name= "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Override
    public boolean equals(UserUpdateParam user) {
        return (this.email.equals(user.getEmail())
                && this.username.equals(user.getUsername())
                && this.userId.equals(user.getUserId()));
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private UUID serialNumber;



    /**
     * 사용자 정보를 업데이트한다.
     *
     * @param param UserUpdateParam 객체
     */
    public void updateUser(UserUpdateParam param) {
        this.email = param.getEmail();
        this.password = param.getPassword();
        this.userId = param.getUserId();
        this.username = param.getUsername();
    }
    /**
     * 패스워드를 인코딩하여 저장한다.
     *
     * @param password 인코딩된 패스워드
     */
    public void encodePassword(String password){
        this.password = password;
    }

    @Override
    public boolean equals(User compare) {
        return this.userId.equals(compare.userId) &&
                this.email.equals(compare.getEmail()) &&
                this.id.equals(compare.getId()) &&
                this.password.equals(compare.getPassword())&&
                this.serialNumber.equals(compare.getSerialNumber()) &&
                this.username.equals(compare.getUsername());
    }
}
