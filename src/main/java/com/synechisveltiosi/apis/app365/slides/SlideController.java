package com.synechisveltiosi.apis.app365.slides;

import com.synechisveltiosi.apis.app365.common.util.mapper.Mapper;
import com.synechisveltiosi.apis.app365.slides.dto.SlideResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/slides",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class SlideController {

    private final Mapper<Slide, SlideResponse> mapper;
    private final SlideService slideService;

    @Autowired
    public SlideController(Mapper<Slide, SlideResponse> mapper, SlideService slideService) {
        this.mapper = mapper;
        this.slideService = slideService;
    }

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<SlideResponse>> getAll() {
        return ResponseEntity.ok(mapper.map(slideService.findAll()));
    }
}
