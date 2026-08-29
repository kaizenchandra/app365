package com.synechisveltiosi.apis.app365.users.repository;

import com.synechisveltiosi.apis.app365.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String id);

    Optional<User> findByEmail(String email);

    void deleteByUserId(String id);
}
