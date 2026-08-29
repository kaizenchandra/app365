package com.synechisveltiosi.apis.app365.broadcast;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BroadcastService {

    Optional<Broadcast> findById(Long id);

    List<Broadcast> findAllPending();

    Page<Broadcast> findAllPending(Pageable pageable);

    Broadcast save(Broadcast broadcast);

    Broadcast update(Broadcast broadcast);

    void deleteById(Long id);
}
