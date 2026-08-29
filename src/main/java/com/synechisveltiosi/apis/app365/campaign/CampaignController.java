package com.synechisveltiosi.apis.app365.campaign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.accounts.exception.AccountNotFoundException;
import com.synechisveltiosi.apis.app365.accounts.service.AccountService;
import com.synechisveltiosi.apis.app365.address.helper.AddressHelper;
import com.synechisveltiosi.apis.app365.calendar.dto.ScheduleRequest;
import com.synechisveltiosi.apis.app365.calendar.dto.ScheduleResponse;
import com.synechisveltiosi.apis.app365.campaign.dto.*;
import com.synechisveltiosi.apis.app365.campaign.entity.*;
import com.synechisveltiosi.apis.app365.campaign.mapper.ScheduleRequestToScheduleMapper;
import com.synechisveltiosi.apis.app365.campaign.mapper.ScheduleToScheduleResponseMapper;
import com.synechisveltiosi.apis.app365.campaign.repository.crm.CrmMilitantRepository;
import com.synechisveltiosi.apis.app365.campaign.repository.crm.CrmOrganismRepository;
import com.synechisveltiosi.apis.app365.campaign.repository.crm.CrmTeamMemberRepository;
import com.synechisveltiosi.apis.app365.campaign.service.ScheduleService;
import com.synechisveltiosi.apis.app365.campaign.service.TaskService;
import com.synechisveltiosi.apis.app365.campaign.service.TeamMemberService;
import com.synechisveltiosi.apis.app365.campaign.service.VolunteerService;
import com.synechisveltiosi.apis.app365.common.auth.SessionUtils;
import com.synechisveltiosi.apis.app365.common.dto.id.IdCardRequest;
import com.synechisveltiosi.apis.app365.common.dto.id.IdResponse;
import com.synechisveltiosi.apis.app365.common.dto.places.AddressResponse;
import com.synechisveltiosi.apis.app365.common.http.request.HttpHeader;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PageResponse;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PageResponseBuilder;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PaginationResponse;
import com.synechisveltiosi.apis.app365.common.util.domain.DefaultPageable;
import com.synechisveltiosi.apis.app365.common.util.mapper.Mapper;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.synechisveltiosi.apis.app365.users.exception.UserNotFoundException;
import com.synechisveltiosi.apis.app365.users.mapper.UserMapper;
import com.synechisveltiosi.apis.app365.users.repository.CrmUserRepository;
import com.synechisveltiosi.apis.app365.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/campaign",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class CampaignController {

    private static final Logger logger = LoggerFactory.getLogger(CampaignController.class);

    private final Mapper<Task, TaskResponse> taskResponseMapper;
    private final Mapper<Volunteer, VolunteerResponse> volunteerResponseMapper;
    private final Mapper<ExtendedTeamMember, TeamMemberResponse> teamMemberResponseMapper;
    private final TeamMemberService teamMemberService;
    private final TaskService taskService;
    private final VolunteerService volunteerService;
    private final ScheduleService scheduleService;
    private final AccountService accountService;
    private final UserService userService;
    private final HttpHeader httpHeader;
    private final CrmTeamMemberRepository crmTeamMemberRepository;
    private final CrmMilitantRepository crmMilitantRepository;
    private final CrmOrganismRepository crmOrganismRepository;
    private final CrmUserRepository crmUserRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public CampaignController(
            TeamMemberService teamMemberService, TaskService taskService, VolunteerService volunteerService,
            ScheduleService scheduleService, Mapper<Task, TaskResponse> taskResponseMapper,
            Mapper<Volunteer, VolunteerResponse> volunteerResponseMapper,
            Mapper<ExtendedTeamMember, TeamMemberResponse> teamMemberResponseMapper,
            AccountService accountService, UserService userService, HttpHeader httpHeader,
            CrmTeamMemberRepository crmTeamMemberRepository, CrmMilitantRepository crmMilitantRepository,
            CrmOrganismRepository crmOrganismRepository, CrmUserRepository crmUserRepository,
            ObjectMapper objectMapper) {

        this.teamMemberService = teamMemberService;
        this.taskService = taskService;
        this.volunteerService = volunteerService;
        this.scheduleService = scheduleService;
        this.taskResponseMapper = taskResponseMapper;
        this.volunteerResponseMapper = volunteerResponseMapper;
        this.teamMemberResponseMapper = teamMemberResponseMapper;
        this.accountService = accountService;
        this.userService = userService;
        this.httpHeader = httpHeader;
        this.crmTeamMemberRepository = crmTeamMemberRepository;
        this.crmMilitantRepository = crmMilitantRepository;
        this.crmOrganismRepository = crmOrganismRepository;
        this.crmUserRepository = crmUserRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = "/volunteers", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<PageResponse<VolunteerResponse>> getVolunteers(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "sortBy", required = false) String sortBy) {

        // Construct the pageable
        Pageable pageable = DefaultPageable.builder()
                .with(page, size, sortBy, Volunteer.SORTABLE_FIELDS)
                .withDefaults(Volunteer.DEFAULT_PAGE, Volunteer.MAX_PAGE_SIZE, Volunteer.Sortable.DEFAULT_SORT)
                .withMaxSize(Volunteer.MAX_PAGE_SIZE)
                .build();

        // Execute the search
        Page<Volunteer> volunteerPage = volunteerService.findAll(query, pageable);

        // Prepare the response
        PageResponse<VolunteerResponse> pageResponse = PageResponseBuilder.<VolunteerResponse>builder()
                .withData(volunteerResponseMapper.map(volunteerPage.getContent()))
                .withPagination(PaginationResponse.from(volunteerPage))
                .build();

        return ResponseEntity.ok(pageResponse);
    }

    @DeleteMapping(value = "/volunteers/me", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> deleteVolunteer() {
        // Remove the user a volunteer
        volunteerService.deleteByUserId(SessionUtils.getLoggedUser().getId());

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/volunteers/me/tasks", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<TaskResponse>> getVolunteerTasks() {
        List<Task> tasks = volunteerService.findAllTasksByUserId(SessionUtils.getLoggedUser().getId());

        return ResponseEntity.ok(taskResponseMapper.map(tasks));
    }

    @DeleteMapping(value = "/volunteers/me/tasks/{taskId}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> deleteVolunteerTask(@NotBlank @PathVariable String taskId) {
        // Remove this for the user
        volunteerService.deleteByUserIdAndTaskId(SessionUtils.getLoggedUser().getId(), taskId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/volunteers/me/tasks/{taskId}/schedules", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<ScheduleResponse> getVolunteerTaskSchedules(@NotBlank @PathVariable String taskId) {
        List<Schedule> schedules = volunteerService.findAllSchedulesByUserIdAndTaskId(
                SessionUtils.getLoggedUser().getId(), taskId);

        ScheduleResponse scheduleResponse = ScheduleToScheduleResponseMapper.mapForSingleTask(schedules);
        if (scheduleResponse == null) throw new ScheduleNotFoundException();

        return ResponseEntity.ok(scheduleResponse);
    }

    @PostMapping(value = "/volunteers/me/schedules")
    public ResponseEntity<Void> addVolunteerSchedule(@RequestBody ScheduleRequest scheduleRequest) {

        // Get the user id
        Long userId = SessionUtils.getLoggedUser().getId();

        // Add schedule entry for this user
        volunteerService.addVolunteerSchedules(userId, ScheduleRequestToScheduleMapper.map(scheduleRequest));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/volunteers/me/schedules/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> removeVolunteerSchedule(@NotBlank @PathVariable String id) {
        // Remove the schedule
        scheduleService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/team/levels", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getTeamLevels() throws Throwable {

        // Find account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse()) {
            List<TeamLevel> levelMembers = crmTeamMemberRepository.findLevelMembers(account, SessionUtils.getLoggedUser().getIdCard());

            return ResponseEntity.ok(levelMembers);
        }

        return ResponseEntity.ok(teamMemberService.findLevelMembers(SessionUtils.getLoggedUser().getIdCard()));
    }

    @GetMapping(value = "/team/members", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getTeamMembers(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) throws Throwable {

        // Construct the pageable
        Pageable pageable = DefaultPageable.builder()
                .withPage(page, ExtendedTeamMember.DEFAULT_PAGE)
                .withSize(size, ExtendedTeamMember.MAX_PAGE_SIZE)
                .withMaxSize(ExtendedTeamMember.MAX_PAGE_SIZE)
                .build();

        PageResponseBuilder<TeamMemberResponse> builder = PageResponseBuilder.builder();

        // Find account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse()) {
            Page<TeamMemberResponse> teamMemberPage = crmTeamMemberRepository.findAllTeamMember(account,
                    SessionUtils.getLoggedUser(), pageable);

            // Prepare the response
            builder.withData(teamMemberPage.getContent())
                    .withPagination(PaginationResponse.from(teamMemberPage));
        } else {
            // Execute the search
            Page<ExtendedTeamMember> teamMemberPage =
                    teamMemberService.findAllTeamMembers(SessionUtils.getLoggedUser().getId(),
                            SessionUtils.getLoggedUser().getIdCard(), pageable);

            builder.withData(teamMemberResponseMapper.map(teamMemberPage.getContent()))
                    .withPagination(PaginationResponse.from(teamMemberPage));
        }

        return ResponseEntity.ok(builder.build());
    }

    @SuppressWarnings("Duplicates")
    @GetMapping(value = "/militant")
    public ResponseEntity<?> getMilitant(@NotBlank @RequestHeader("X-Type") Militant.SearchType type, @NotBlank @RequestHeader("X-ID-Card") String idCard) throws IOException {
        Militant militant;

        // Find account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse()) {
            militant = crmMilitantRepository.findMilitant(account, idCard, type);
        } else {
            throw new BadRequestException();
        }

        return ResponseEntity.ok(militant);
    }

    @PostMapping(value = "/user-as-militant")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<?> addUserAsMilitant(@Valid @RequestBody IdCardRequest idCardRequest) throws IOException {
        // Find account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse()) {
            crmMilitantRepository.addUserAsMilitant(account, idCardRequest);
        } else {
            throw new BadRequestException();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/militant")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<?> addMilitant(@Valid @RequestBody MilitantRequest militantRequest) throws IOException {
        // Find account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse()) {
            try {
                crmMilitantRepository.addMilitant(account, militantRequest);
            } catch (CoordinatorNotFoundException ex) {
                // Try to find the user
                Optional<User> userOptional = userService.findById(SessionUtils.getLoggedUser().getUserId());
                if (!userOptional.isPresent()) throw new UserNotFoundException();

                logger.debug("Find coordinator address.");
                AddressResponse address = crmUserRepository.findAddress(account, userOptional.get().getIdCard());

                logger.debug("Coordinator not found.");
                crmMilitantRepository.addMilitant(account,
                        UserMapper.INSTANCE.toMilitantRequest(userOptional.get(),
                                AddressHelper.from(address, objectMapper)));

                // Add the militant
                crmMilitantRepository.addMilitant(account, militantRequest);
            }
        } else {
            throw new BadRequestException();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/team")
    public ResponseEntity<Void> addCBAHeader(@Valid @RequestBody CbaHeaderRequest request) throws IOException {
        // Find account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse()) {

            crmTeamMemberRepository.addHeader(account, request);
        } else {
            throw new BadRequestException();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/team/members")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<Void> addTeamMembers(@Valid @RequestBody MilitantRequest request) throws IOException {
        // Find account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse()) {
            try {
                crmTeamMemberRepository.save(account, request);
            } catch (CoordinatorNotFoundException ex) {
                // Try to find the user
                Optional<User> userOptional = userService.findById(SessionUtils.getLoggedUser().getUserId());
                if (!userOptional.isPresent()) throw new UserNotFoundException();

                logger.debug("Find coordinator address.");
                AddressResponse address = crmUserRepository.findAddress(account, userOptional.get().getIdCard());

                logger.debug("Coordinator not found.");
                crmMilitantRepository.addMilitant(account,
                        UserMapper.INSTANCE.toMilitantRequest(userOptional.get(),
                                AddressHelper.from(address, objectMapper)));

                // Add the militant
                crmTeamMemberRepository.save(account, request);
            }
        } else {
            // Add this citizen as member of the logged in user
            //TODO: Change the implementations here in our API
//            teamMemberService.save(SessionUtils.getLoggedUser().getId(), "");
            throw new BadRequestException();
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/team/members/{id}")
    public ResponseEntity<?> updateMembers(@NotBlank @PathVariable String id,
                                           @RequestBody TeamMemberRequest memberRequest) throws IOException {

        // Find account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse()) {
            crmTeamMemberRepository.update(account, id, memberRequest);

            return ResponseEntity.noContent().build();
        }

        throw new BadRequestException();
    }

    @PutMapping(value = "/team/members/{id}/addresses")
    public ResponseEntity<Void> pathTeamMemberAddress(
            @NotBlank @PathVariable String id, @RequestBody Map<String, Object> addressPatch) throws IOException {

        // Find account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse()) {
            crmTeamMemberRepository.patchAddress(account, id, addressPatch);
        } else {
            // Save the address for this member
            teamMemberService.patchAddress(id, addressPatch);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/team/members/email/validate", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> verifyTeamMemberEmailAddress(
            @NotBlank @RequestHeader("X-Email") String email,
            @RequestHeader(value = "X-Id-Card", required = false) String idCard) throws IOException {

        // Find account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse()) {

            crmTeamMemberRepository.verifyTeamMembersEmailAddress(account, email, idCard);
            return ResponseEntity.noContent().build();
        }

        throw new BadRequestException();

    }

    @GetMapping(value = "/militants/email/validate", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> verifyMilitantEmailAddress(
            @NotBlank @RequestHeader("X-Email") String email,
            @RequestHeader(value = "X-Id-Card", required = false) String idCard) throws IOException {

        // Find account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse()) {

            crmMilitantRepository.verifyMilitantsEmailAddress(account, email, idCard);
            return ResponseEntity.noContent().build();
        }

        throw new BadRequestException();
    }

    @GetMapping(value = "/team/members/phone/{type}/validate", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> verifyTeamMembersPhoneNumber(
            @NotBlank @PathVariable("type") String type,
            @NotBlank @RequestHeader("X-CountryID") String countryID,
            @NotBlank @RequestHeader("X-Phone") String phone,
            @RequestHeader(value = "X-Id-Card", required = false) String idCard) throws IOException {

        // Find account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse()) {

            crmTeamMemberRepository.verifyTeamMembersPhoneNumbers(account, countryID, type, phone, idCard);
            return ResponseEntity.noContent().build();
        }

        throw new BadRequestException();
    }

    @GetMapping(value = "/militants/phone/{type}/validate", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> verifyMilitantsPhoneNumber(
            @NotBlank @PathVariable("type") String type,
            @NotBlank @RequestHeader("X-CountryID") String countryID,
            @NotBlank @RequestHeader("X-Phone") String phone,
            @RequestHeader(value = "X-Id-Card", required = false) String idCard) throws IOException {

        // Find account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse()) {

            crmMilitantRepository.verifyMilitantsPhoneNumbers(account, countryID, type, phone, idCard);
            return ResponseEntity.noContent().build();
        }

        throw new BadRequestException();
    }

    @DeleteMapping(value = "/team/members", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> removeTeamMembers(@NotBlank @RequestHeader("X-ID-Card") String idCard) {
        // Remove member
        teamMemberService.deleteByIdCard(idCard);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/tasks", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<TaskResponse>> getTasks() {
        return ResponseEntity.ok(taskResponseMapper.map(taskService.findAll()));
    }

    @PostMapping(value = "/tasks", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> creteTask(@Valid @RequestBody TaskRequest request) {

        Task task = taskService.save(request.mapToTask());

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(task.getTaskId()));
    }

    @PutMapping(value = "/tasks/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> updateTask(@NotNull @PathVariable String id, @Valid @RequestBody TaskRequest request) {

        Optional<Task> taskOptional = taskService.findById(id);

        if (!taskOptional.isPresent()) {
            throw new TaskNotFoundException();
        }

        Task foundTask = taskOptional.get();
        foundTask.setName(request.getName());

        Task task = taskService.save(foundTask);

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(task.getTaskId()));
    }

    @DeleteMapping(value = "/tasks/{id}")
    public ResponseEntity<?> deleteTask(@NotNull @PathVariable String id) {

        taskService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/associated-organisms")
    public ResponseEntity<?> associatedOrganisms() throws IOException {
        List<Organism> organisms = new ArrayList<>();

        // Find account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse()) {
            organisms = crmOrganismRepository.findAssociatedOrganism(account);
        } else {
            throw new BadRequestException();
        }

        return ResponseEntity.ok(organisms);
    }

    @GetMapping(value = "/support-source")
    public ResponseEntity<?> getSupportSource() throws IOException {

        List<Organism> organisms = new ArrayList<>();

        // Find account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse()) {
            organisms = crmOrganismRepository.findSupportSource(account);
        } else {
            throw new BadRequestException();
        }

        return ResponseEntity.ok(organisms);
    }

    @GetMapping(value = "/support-source/{id}/associated-organisms")
    public ResponseEntity<?> associatedFromSupportSource(@PathVariable @NotBlank String id) throws IOException {
        List<Organism> organisms = new ArrayList<>();

        // Find account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse()) {
            organisms = crmOrganismRepository.findAssociatedOrganismFromSupportSource(account, id);
        } else {
            throw new BadRequestException();
        }

        return ResponseEntity.ok(organisms);
    }
}
