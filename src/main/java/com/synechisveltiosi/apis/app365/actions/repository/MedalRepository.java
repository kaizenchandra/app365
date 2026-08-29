package com.synechisveltiosi.apis.app365.actions.repository;

import com.synechisveltiosi.apis.app365.actions.entity.Medal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedalRepository extends JpaRepository<Medal, Long> {

    Optional<Medal> findByMedalId(String medalId);
}
