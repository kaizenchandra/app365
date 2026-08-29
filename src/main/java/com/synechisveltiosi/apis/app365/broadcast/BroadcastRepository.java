package com.synechisveltiosi.apis.app365.broadcast;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BroadcastRepository extends JpaRepository<Broadcast, Long> {

    List<Broadcast> findAllByStatusAndActiveIsTrue(Broadcast.Status status);
}
