package com.c9Pay.userservice.web.mvc.repository;


import com.c9Pay.userservice.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * 사용자 정보를  데이터베이스에서 조회하고 조작하기 위한 JpaRepository 인터페이스
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 주어진 사용자 ID를 사용하여 사용자를 조회한다.
     *
     * @param UserId 조회할 사용자의 ID
     * @return 조회된 사용자 객체, 존재하지 않을 경우 Optional.empty()
     */
    Optional<User> findByUserId(String UserId);

    /**
     * 주어진 ID를 사용하여 사용자를 조회한다.
     *
     * @param id 조회할 사용자의 ID
     * @return 조회된 사용자 객체, 존재하지 않을 경우 Optional.empty()
     */
    Optional<User> findById(Long id);

    /**
     * 주어진 개체식별번호를 사용하여 사용자를 조회한다.
     *
     * @param serialNumber 조회할 사용자의 개체식별번호
     * @return 조회된 사용자 객체, 존재하지 않을 경우 Optional.empty()
     */
    Optional<User> findBySerialNumber(UUID serialNumber);

    /**
     * 주어진 사용자 이름을 사용하여 사용자를 삭제한다.
     *
     * @param username 삭제할 사용자의 이름
     */
    void deleteByUsername(String username);
}

