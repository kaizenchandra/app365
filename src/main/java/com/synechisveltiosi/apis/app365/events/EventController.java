package com.synechisveltiosi.apis.app365.events;

import com.synechisveltiosi.apis.app365.calendar.dto.CalendarEntryRequest;
import com.synechisveltiosi.apis.app365.calendar.mapper.CalendarMapper;
import com.synechisveltiosi.apis.app365.common.auth.SessionUtils;
import com.synechisveltiosi.apis.app365.common.dto.comments.CommentRequest;
import com.synechisveltiosi.apis.app365.common.dto.comments.CommentResponse;
import com.synechisveltiosi.apis.app365.common.dto.id.IdResponse;
import com.synechisveltiosi.apis.app365.common.files.FileSystem;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.CursorResponse;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PageResponse;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PageResponseBuilder;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PaginationResponse;
import com.synechisveltiosi.apis.app365.common.util.StringHelper;
import com.synechisveltiosi.apis.app365.common.util.domain.DefaultPageable;
import com.synechisveltiosi.apis.app365.common.util.mapper.Mapper;
import com.synechisveltiosi.apis.app365.events.dto.EventResponse;
import com.synechisveltiosi.apis.app365.events.entity.Event;
import com.synechisveltiosi.apis.app365.events.entity.EventComment;
import com.synechisveltiosi.apis.app365.events.helper.EventHelper;
import com.synechisveltiosi.apis.app365.events.mapper.EventCommentMapper;
import com.synechisveltiosi.apis.app365.events.service.EventService;
import com.synechisveltiosi.apis.app365.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/events",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {

    private final EventService eventService;
    private final Mapper<EventComment, CommentResponse> commentResponseMapper;
    private FileSystem fileSystem;

    @Autowired
    public EventController(EventService eventService, Mapper<EventComment, CommentResponse> commentResponseMapper, FileSystem fileSystem) {
        this.eventService = eventService;
        this.commentResponseMapper = commentResponseMapper;
        this.fileSystem = fileSystem;
    }

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<PageResponse<EventResponse>> getAll(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "summary", required = false) boolean summary
            ) {

        User loggedUser = SessionUtils.getLoggedUser();

        // Construct the pageable
        Pageable pageable = DefaultPageable.builder()
                .with(page, size, sortBy, Event.SORTABLE_FIELDS)
                .withDefaults(Event.DEFAULT_PAGE, Event.MAX_PAGE_SIZE, Event.Sortable.DEFAULT_SORT)
                .withMaxSize(Event.MAX_PAGE_SIZE)
                .build();

        // Execute the search
        Page<Event> eventPage = eventService.findAll(query, pageable);

        List<EventResponse> responseData = EventHelper.processEventUserHasJoined(loggedUser.getId(), eventPage.getContent());

        if (! summary) {
            responseData = EventHelper.nullifyListOnlyFields(responseData);
        }

        // Prepare the response
        PageResponse<EventResponse> pageResponse = PageResponseBuilder.<EventResponse>builder()
                .withData(responseData)
                .withPagination(PaginationResponse.from(eventPage))
                .build();

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping(value = "/me/joined", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<PageResponse<EventResponse>> getAllJoinedByMe(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "sortBy", required = false) String sortBy) {

        User loggedUser = SessionUtils.getLoggedUser();

        // Construct the pageable
        Pageable pageable = DefaultPageable.builder()
                .with(page, size, sortBy, Event.SORTABLE_FIELDS)
                .withDefaults(Event.DEFAULT_PAGE, Event.MAX_PAGE_SIZE, Event.Sortable.DEFAULT_SORT)
                .withMaxSize(Event.MAX_PAGE_SIZE)
                .build();

        // Execute the search
        Page<Event> eventPage = eventService.findAllJoined(loggedUser.getId(), query, pageable);

        // Prepare the response
        PageResponse<EventResponse> pageResponse = PageResponseBuilder.<EventResponse>builder()
                .withData(EventHelper.nullifyListOnlyFields(
                        EventHelper.processEventUserHasJoined(loggedUser.getId(), eventPage.getContent())))
                .withPagination(PaginationResponse.from(eventPage))
                .build();

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<EventResponse> getOne(@PathVariable @NotBlank String id) {
        Optional<Event> eventOptional = eventService.findById(id);

        // Get the event
        EventResponse eventResponse = EventHelper.processEventUserHasJoined(SessionUtils.getLoggedUser().getId(),
                eventOptional.orElseThrow(EventNotFoundException::new));

        // Find next event if any and add next cursor
        Optional<Event> nextEventOptional = eventService.findNextFrom(eventOptional.get().getId());
        nextEventOptional.ifPresent(event -> eventResponse.setCursor(new CursorResponse(event.getEventId())));

        return ResponseEntity.ok(eventResponse);
    }

    @PostMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody EventRequest request) {

        Event toEvent = request.mapToEvent();
        toEvent.setUserId(SessionUtils.getLoggedUser());

        Event event = eventService.save(toEvent);

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(event.getEventId()));
    }

    @PutMapping(value = "/{id}/upload-cover-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadCover(@PathVariable @NotBlank String id, @RequestParam(value = "file") MultipartFile file) throws IOException, URISyntaxException {

        Optional<Event> foundEvent = eventService.findById(id);

        if (! foundEvent.isPresent()) {
            throw new EventNotFoundException();
        }

        Event event = foundEvent.get();

        String fileName = String.format("%s-%s", StringHelper.slug(event.getTitle()), event.getEventId());
        String coverPictureUrl = this.fileSystem.store(file, "events", fileName);

        event.setCoverPicture(coverPictureUrl);

        eventService.save(event);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{id}/remove-cover-picture", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> removeCover(@PathVariable @NotBlank String id) {

        Optional<Event> foundEvent = eventService.findById(id);

        if (! foundEvent.isPresent()) {
            throw new EventNotFoundException();
        }

        Event event = foundEvent.get();

        this.fileSystem.delete(event.getCoverPicture());

        event.setCoverPicture(String.format("https://via.placeholder.com/500x500?text=%s", event.getTitle()));

        eventService.save(event);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping(value = "/{id}/join")
    public ResponseEntity<Void> join(@PathVariable @NotBlank String id) {
        User loggedUser = SessionUtils.getLoggedUser();

        // Join the event
        eventService.join(id, loggedUser.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{id}/join", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> unjoin(@PathVariable @NotBlank String id) {
        User loggedUser = SessionUtils.getLoggedUser();

        // Unjoin the event
        eventService.unjoin(id, loggedUser.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/{id}/likes")
    public ResponseEntity<Void> doLike(@PathVariable @NotBlank String id) {
        User loggedUser = SessionUtils.getLoggedUser();

        // Like the event
        eventService.like(id, loggedUser.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{id}/likes", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> doUnlike(@PathVariable @NotBlank String id) {
        User loggedUser = SessionUtils.getLoggedUser();

        // Unlike the event
        eventService.unlike(id, loggedUser.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/{id}/share")
    public ResponseEntity<Void> doShare(@PathVariable @NotBlank String id) {
        User loggedUser = SessionUtils.getLoggedUser();

        // Share the event
        eventService.share(id, loggedUser.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{id}/comments", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<PageResponse<CommentResponse>> getComments(
            @PathVariable @NotBlank String id,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sortBy", required = false) String sortBy) {

        // Construct the pageable
        Pageable pageable = DefaultPageable.builder()
                .with(page, size, sortBy, EventComment.SORTABLE_FIELDS)
                .withDefaults(EventComment.DEFAULT_PAGE, EventComment.MAX_PAGE_SIZE, EventComment.Sortable.DEFAULT_SORT)
                .withMaxSize(EventComment.MAX_PAGE_SIZE)
                .build();

        // Execute the search
        Page<EventComment> commentPage = eventService.findAllComments(id, pageable);

        // Prepare the response
        PageResponse<CommentResponse> pageResponse = PageResponseBuilder.<CommentResponse>builder()
                .withData(commentResponseMapper.map(commentPage.getContent()))
                .withPagination(PaginationResponse.from(commentPage))
                .build();

        return ResponseEntity.ok(pageResponse);
    }

    @PostMapping(value = "/{id}/comments")
    public ResponseEntity<Void> saveComment(
            @PathVariable @NotBlank String id, @Valid @RequestBody CommentRequest commentRequest) {

        User loggedUser = SessionUtils.getLoggedUser();

        eventService.saveComment(loggedUser.getId(), id, EventCommentMapper.INSTANCE.from(commentRequest));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/{id}/calendar")
    public ResponseEntity<Void> addToCalendar(
            @PathVariable @NotBlank String id, @Valid @RequestBody CalendarEntryRequest calendarRequest) {

        User loggedUser = SessionUtils.getLoggedUser();

        eventService.addToCalendar(loggedUser.getId(), id, CalendarMapper.INSTANCE.from(calendarRequest));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{id}/calendar", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> removeFromCalendar(@PathVariable @NotBlank String id) {
        User loggedUser = SessionUtils.getLoggedUser();

        eventService.removeFromCalendar(loggedUser.getId(), id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
