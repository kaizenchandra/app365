package com.synechisveltiosi.apis.app365.news;

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
import com.synechisveltiosi.apis.app365.news.dto.NewsResponse;
import com.synechisveltiosi.apis.app365.news.entity.News;
import com.synechisveltiosi.apis.app365.news.entity.NewsComment;
import com.synechisveltiosi.apis.app365.news.helper.NewsHelper;
import com.synechisveltiosi.apis.app365.news.mapper.NewsCommentMapper;
import com.synechisveltiosi.apis.app365.news.service.NewsService;
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
@RequestMapping(value = "/news",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {

    private final NewsService newsService;
    private final Mapper<NewsComment, CommentResponse> commentResponseMapper;
    private final FileSystem fileSystem;

    @Autowired
    public NewsController(NewsService newsService, Mapper<NewsComment, CommentResponse> commentResponseMapper, FileSystem fileSystem) {
        this.newsService = newsService;
        this.commentResponseMapper = commentResponseMapper;
        this.fileSystem = fileSystem;
    }

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<PageResponse<NewsResponse>> getAll(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "summary", required = false) boolean summary
            ) {

        User loggedUser = SessionUtils.getLoggedUser();

        // Construct the pageable
        Pageable pageable = DefaultPageable.builder()
                .with(page, size, sortBy, News.SORTABLE_FIELDS)
                .withDefaults(News.DEFAULT_PAGE, News.MAX_PAGE_SIZE, News.Sortable.DEFAULT_SORT)
                .withMaxSize(News.MAX_PAGE_SIZE)
                .build();

        // Execute the search
        Page<News> newsPage = newsService.findAll(query, pageable);

        List<NewsResponse> responseData = NewsHelper.processNewsMetadata(loggedUser.getId(), newsPage.getContent());

        if (! summary) {
            responseData = NewsHelper.nullifyListOnlyFields(responseData);
        }

        // Prepare the response
        PageResponse<NewsResponse> pageResponse = PageResponseBuilder.<NewsResponse>builder()
                .withData(responseData)
                .withPagination(PaginationResponse.from(newsPage))
                .build();

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<NewsResponse> getOne(@PathVariable @NotBlank String id) {
        Optional<News> newsOptional = newsService.findById(id);

        // Get the news
        NewsResponse newsResponse = NewsHelper.processNewsMetada(SessionUtils.getLoggedUser().getId(),
                newsOptional.orElseThrow(NewsNotFoundException::new));

        // Find next news if any and add next cursor
        Optional<News> nextNewsOptional = newsService.findNextFrom(newsOptional.get().getId());
        nextNewsOptional.ifPresent(news -> newsResponse.setCursor(new CursorResponse(news.getNewsId())));

        return ResponseEntity.ok(newsResponse);
    }

    @PostMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody NewsRequest request) {

        News toNews = request.mapToNews();

        toNews.setUserId(SessionUtils.getLoggedUser());
        News news = newsService.save(toNews);

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(news.getNewsId()));
    }

    @PutMapping(value = "/{id}/upload-cover-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadCover(@PathVariable @NotBlank String id, @RequestParam(value = "file") MultipartFile file) throws IOException, URISyntaxException {

        Optional<News> foundNews = newsService.findById(id);

        if (! foundNews.isPresent()) {
            throw new NewsNotFoundException();
        }

        News news = foundNews.get();

        String fileName = String.format("%s-%s", StringHelper.slug(news.getTitle()), news.getNewsId());
        String coverPictureUrl = this.fileSystem.store(file, "news", fileName);

        news.setCoverPicture(coverPictureUrl);

        newsService.save(news);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{id}/remove-cover-picture", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> removeCover(@PathVariable @NotBlank String id) {

        Optional<News> foundNews = newsService.findById(id);

        if (! foundNews.isPresent()) {
            throw new NewsNotFoundException();
        }

        News news = foundNews.get();

        this.fileSystem.delete(news.getCoverPicture());

        news.setCoverPicture(String.format("https://via.placeholder.com/500x500?text=%s", news.getTitle()));

        newsService.save(news);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/{id}/likes")
    public ResponseEntity<Void> doLike(@PathVariable @NotBlank String id) {
        User loggedUser = SessionUtils.getLoggedUser();

        // Like the news
        newsService.like(id, loggedUser.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{id}/likes", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> doUnlike(@PathVariable @NotBlank String id) {
        User loggedUser = SessionUtils.getLoggedUser();

        // Unlike the news
        newsService.unlike(id, loggedUser.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/{id}/share")
    public ResponseEntity<Void> doShare(@PathVariable @NotBlank String id) {
        User loggedUser = SessionUtils.getLoggedUser();

        // Share the news
        newsService.share(id, loggedUser.getId());

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
                .with(page, size, sortBy, NewsComment.SORTABLE_FIELDS)
                .withDefaults(NewsComment.DEFAULT_PAGE, NewsComment.MAX_PAGE_SIZE, NewsComment.Sortable.DEFAULT_SORT)
                .withMaxSize(NewsComment.MAX_PAGE_SIZE)
                .build();

        // Execute the search
        Page<NewsComment> commentPage = newsService.findAllComments(id, pageable);

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

        newsService.saveComment(loggedUser.getId(), id, NewsCommentMapper.INSTANCE.from(commentRequest));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
