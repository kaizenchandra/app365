package com.synechisveltiosi.apis.app365.actions;

import com.synechisveltiosi.apis.app365.actions.dto.MedalResponse;
import com.synechisveltiosi.apis.app365.actions.entity.Medal;
import com.synechisveltiosi.apis.app365.actions.exception.MedalNotFoundException;
import com.synechisveltiosi.apis.app365.actions.service.MedalService;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PageResponse;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PageResponseBuilder;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PaginationResponse;
import com.synechisveltiosi.apis.app365.common.util.domain.DefaultPageable;
import com.synechisveltiosi.apis.app365.common.util.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/actions",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ActionController {

    private final Mapper<Medal, MedalResponse> medalResponseMapper;
    private final MedalService medalService;

    @Autowired
    public ActionController(Mapper<Medal, MedalResponse> medalResponseMapper, MedalService medalService) {
        this.medalResponseMapper = medalResponseMapper;
        this.medalService = medalService;
    }

    @GetMapping(value = "/medals", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<PageResponse<MedalResponse>> getMedals(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sortBy", required = false) String sortBy) {

        // Construct the pageable
        Pageable pageable = DefaultPageable.builder()
                .with(page, size, sortBy, Medal.SORTABLE_FIELDS)
                .withDefaults(Medal.DEFAULT_PAGE, Medal.MAX_PAGE_SIZE, Medal.Sortable.DEFAULT_SORT)
                .withMaxSize(Medal.MAX_PAGE_SIZE)
                .build();

        // Execute the search
        Page<Medal> medalPage = medalService.findAll(pageable);

        // Prepare the response
        PageResponse<MedalResponse> pageResponse = PageResponseBuilder.<MedalResponse>builder()
                .withData(medalResponseMapper.map(medalPage.getContent()))
                .withPagination(PaginationResponse.from(medalPage))
                .build();

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping(value = "/medals/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<MedalResponse> getMedal(@PathVariable String id) {
        Optional<Medal> medalOptional = medalService.findById(id);
        if (!medalOptional.isPresent()) throw new MedalNotFoundException();

        return ResponseEntity.ok(medalResponseMapper.map(medalOptional.get()));
    }
}
