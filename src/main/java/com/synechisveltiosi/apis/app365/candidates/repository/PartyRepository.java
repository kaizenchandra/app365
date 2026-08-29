package com.synechisveltiosi.apis.app365.candidates.repository;

import com.synechisveltiosi.apis.app365.candidates.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

    Optional<Party> findByPartyId(String partyId);
}
