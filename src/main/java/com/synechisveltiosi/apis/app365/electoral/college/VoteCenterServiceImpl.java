package com.synechisveltiosi.apis.app365.electoral.college;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteCenterServiceImpl implements VoteCenterService {

    private final VoteCenterRepository voteCenterRepository;

    @Autowired
    public VoteCenterServiceImpl(VoteCenterRepository voteCenterRepository) {
        this.voteCenterRepository = voteCenterRepository;
    }

    @Override
    public Optional<VoteCenter> findFirst() {
        return voteCenterRepository.findFirstByOrderByCreatedAtDesc();
    }
}
