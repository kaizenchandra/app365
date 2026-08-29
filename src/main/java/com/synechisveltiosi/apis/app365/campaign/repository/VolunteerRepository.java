package com.synechisveltiosi.apis.app365.campaign.repository;

import com.synechisveltiosi.apis.app365.campaign.entity.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    // TODO Inject task summary for each volunteer
    Page<Volunteer> findAllByActiveIsTrue(Pageable pageable);

    Optional<Volunteer> findByUserId_IdAndActiveIsTrue(Long userId);
}
