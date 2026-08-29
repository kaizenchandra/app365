package com.synechisveltiosi.apis.app365.slides;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlideServiceImpl implements SlideService {

    private final SlideRepository slideRepository;

    @Autowired
    public SlideServiceImpl(SlideRepository slideRepository) {
        this.slideRepository = slideRepository;
    }

    @Override
    public List<Slide> findAll() {
        return slideRepository.findAll();
    }
}
