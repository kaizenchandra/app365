package com.synechisveltiosi.apis.app365.broadcast;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Clivens Petit <clivens.petit@magicsoftbay.com>
 */
@Service
public class BroadcastServiceImpl implements BroadcastService {

    private final BroadcastRepository broadcastRepository;

    @Autowired
    public BroadcastServiceImpl(BroadcastRepository broadcastRepository) {
        this.broadcastRepository = broadcastRepository;
    }

    @Override
    public Optional<Broadcast> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Broadcast id should not be null or 0");

        return broadcastRepository.findById(id);
    }

    @Override
    public List<Broadcast> findAllPending() {
        return broadcastRepository.findAllByStatusAndActiveIsTrue(Broadcast.Status.PENDING);
    }

    @Override
    public Page<Broadcast> findAllPending(Pageable pageable) {
        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return broadcastRepository.findAll(pageable);
    }

    @Override
    public Broadcast save(Broadcast broadcast) {
        return broadcastRepository.save(broadcast);
    }

    @Transactional
    @Override
    public Broadcast update(Broadcast broadcast) {
        return broadcastRepository.save(broadcast);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Broadcast id should not be null or 0");

        broadcastRepository.deleteById(id);
    }
}
