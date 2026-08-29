package com.synechisveltiosi.apis.app365.electoral.college;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteCenterRepository extends JpaRepository<VoteCenter, Long> {

    Optional<VoteCenter> findFirstByOrderByCreatedAtDesc();
}
