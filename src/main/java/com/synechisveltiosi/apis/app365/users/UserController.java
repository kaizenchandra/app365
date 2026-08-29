package com.synechisveltiosi.apis.app365.users;

import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.accounts.exception.AccountNotFoundException;
import com.synechisveltiosi.apis.app365.accounts.service.AccountService;
import com.synechisveltiosi.apis.app365.campaign.dto.CbaUser;
import com.synechisveltiosi.apis.app365.campaign.dto.Militant;
import com.synechisveltiosi.apis.app365.campaign.repository.crm.CrmMilitantRepository;
import com.synechisveltiosi.apis.app365.campaign.repository.crm.CrmTeamMemberRepository;
import com.synechisveltiosi.apis.app365.candidates.CandidateNotFoundException;
import com.synechisveltiosi.apis.app365.candidates.entity.Candidate;
import com.synechisveltiosi.apis.app365.candidates.entity.CandidateDonation;
import com.synechisveltiosi.apis.app365.candidates.service.CandidateService;
import com.synechisveltiosi.apis.app365.common.SocialNetworkProvider;
import com.synechisveltiosi.apis.app365.common.auth.SessionUtils;
import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;
import com.synechisveltiosi.apis.app365.common.auth.dto.SocialTokenRequest;
import com.synechisveltiosi.apis.app365.common.dto.id.IdResponse;
import com.synechisveltiosi.apis.app365.common.dto.places.AddressResponse;
import com.synechisveltiosi.apis.app365.common.http.request.HttpHeader;
import com.synechisveltiosi.apis.app365.common.payment.Amount;
import com.synechisveltiosi.apis.app365.common.payment.DonationRequest;
import com.synechisveltiosi.apis.app365.common.payment.PaymentException;
import com.synechisveltiosi.apis.app365.common.payment.PaymentService;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PageResponse;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PageResponseBuilder;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PaginationResponse;
import com.synechisveltiosi.apis.app365.common.sms.PhoneVerification;
import com.synechisveltiosi.apis.app365.common.sms.PhoneVerificationMapper;
import com.synechisveltiosi.apis.app365.common.sms.dto.VerifyPhoneVerificationCodeRequest;
import com.synechisveltiosi.apis.app365.common.util.RegistrationChannel;
import com.synechisveltiosi.apis.app365.common.util.StringHelper;
import com.synechisveltiosi.apis.app365.common.util.UserSocialToken;
import com.synechisveltiosi.apis.app365.common.util.domain.DefaultPageable;
import com.synechisveltiosi.apis.app365.common.util.mapper.Mapper;
import com.applepolitical.apis.applepolitical365.devices.*;
import com.synechisveltiosi.apis.app365.devices.*;
import com.synechisveltiosi.apis.app365.devices.mapper.DeviceMapper;
import com.synechisveltiosi.apis.app365.notifications.Notification;
import com.synechisveltiosi.apis.app365.notifications.NotificationResponse;
import com.synechisveltiosi.apis.app365.notifications.NotificationService;
import com.synechisveltiosi.apis.app365.users.dto.ResendPhoneVerificationCodeRequest;
import com.synechisveltiosi.apis.app365.users.dto.UserRequest;
import com.synechisveltiosi.apis.app365.users.dto.UserResponse;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.synechisveltiosi.apis.app365.users.exception.UserNotFoundException;
import com.synechisveltiosi.apis.app365.users.mapper.UserMapper;
import com.synechisveltiosi.apis.app365.users.repository.CrmUserRepository;
import com.synechisveltiosi.apis.app365.users.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
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
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final Mapper<User, UserResponse> userResponseMapper;
    private final Mapper<UserRequest, User> userMapper;
    private final Mapper<Device, DeviceResponse> deviceMapper;
    private final Mapper<Notification, NotificationResponse> notificationsResponseMapper;
    private final AccountService accountService;
    private final UserService userService;
    private final PaymentService paymentService;
    private final DeviceService deviceService;
    private final CandidateService candidateService;
    private final NotificationService notificationService;
    private final CrmMilitantRepository crmMilitantRepository;
    private final CrmTeamMemberRepository crmTeamMemberRepository;
    private final CrmUserRepository crmUserRepository;
    private final HttpHeader httpHeader;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserController(AccountService accountService, UserService userService, PaymentService paymentService,
                          DeviceService deviceService, CandidateService candidateService,
                          NotificationService notificationService, HttpHeader httpHeader,
                          CrmMilitantRepository crmMilitantRepository,
                          Mapper<Device, DeviceResponse> deviceMapper, Mapper<User, UserResponse> userResponseMapper,
                          Mapper<Notification, NotificationResponse> notificationsResponseMapper,
                          Mapper<UserRequest, User> userMapper, CrmTeamMemberRepository crmTeamMemberRepository,
                          CrmUserRepository crmUserRepository, ObjectMapper objectMapper) {

        this.userService = userService;
        this.paymentService = paymentService;
        this.deviceService = deviceService;
        this.candidateService = candidateService;
        this.notificationService = notificationService;
        this.deviceMapper = deviceMapper;
        this.userResponseMapper = userResponseMapper;
        this.userMapper = userMapper;
        this.notificationsResponseMapper = notificationsResponseMapper;
        this.accountService = accountService;
        this.httpHeader = httpHeader;
        this.crmMilitantRepository = crmMilitantRepository;
        this.crmTeamMemberRepository = crmTeamMemberRepository;
        this.crmUserRepository = crmUserRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<PageResponse<UserResponse>> getUsers(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "sortBy", required = false) String sortBy) {

        // Construct the pageable
        Pageable pageable = DefaultPageable.builder()
                .with(page, size, sortBy, User.SORTABLE_FIELDS)
                .withDefaults(User.DEFAULT_PAGE, User.MAX_PAGE_SIZE, User.Sortable.DEFAULT_SORT)
                .withMaxSize(User.MAX_PAGE_SIZE)
                .build();

        // Execute the search
        Page<User> userPage = userService.findAll(query, pageable);

        // Prepare the response
        PageResponse<UserResponse> pageResponse = PageResponseBuilder.<UserResponse>builder()
                .withData(userResponseMapper.map(userPage.getContent()))
                .withPagination(PaginationResponse.from(userPage))
                .build();

        return ResponseEntity.ok(pageResponse);
    }

    @PostMapping
    public ResponseEntity<IdResponse> saveUser(
            @Valid @RequestBody UserRequest userRequest, @RequestHeader("Accept-Language") String language,
            @RequestHeader("X-TZ-Id") String timeZoneId, @RequestHeader("X-TZ-Offset") Integer timeZoneOffset)
            throws IOException {

        // Convert the user request
        User user = userMapper.map(userRequest);
        user.setChannel(RegistrationChannel.APPLE_POLITICAL);
        user.setLanguage(language);
        user.setTimeZone(timeZoneId);
        user.setTimeZoneOffset(timeZoneOffset);

        // Persist the new user
        User newUser = userService.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(newUser.getUserId()));
    }

    @PutMapping("/{id}/phoneVerification")
    public ResponseEntity<Void> phoneVerification(
            @PathVariable String id,
            @RequestBody VerifyPhoneVerificationCodeRequest phoneVerificationCode) throws Exception {

        // Convert the request
        PhoneVerification phoneVerification = PhoneVerificationMapper.INSTANCE.from(phoneVerificationCode);

        // Verify the phone number
        userService.verifyPhoneNumber(id, phoneVerification);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/phoneVerification/resendCode")
    public ResponseEntity<Void> phoneVerification(@PathVariable String id) throws Exception {

        // Verify the phone number
        userService.resendPhoneVerificationCode(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/phoneVerification/resendCode")
    public ResponseEntity<IdResponse> resendPhoneVerification(
            @RequestBody ResendPhoneVerificationCodeRequest codeRequest) throws Exception {

        // Verify the phone number
        String userId = userService.resendPhoneVerificationCode(PhoneVerificationMapper.INSTANCE.from(codeRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(userId));
    }

    @GetMapping(value = "/me", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<UserResponse> getMe() throws IOException {
        User user = SessionUtils.getLoggedUser();

        Optional<User> olderUser = userService.findByEmail(user.getEmail());
        if (!olderUser.isPresent()) throw new UserNotFoundException();

        UserResponse userResponse = userResponseMapper.map(olderUser.get());

        // Find the account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse() && StringUtils.isNotBlank(olderUser.get().getIdCard())) {
            // Search the user
            getUserResponseWithCbaHeader(account, userResponse);

            // Add user address from CRM
            AddressResponse address = crmUserRepository.findAddress(account, olderUser.get().getIdCard());
            userResponse.setAddress(address);
        }

        return ResponseEntity.ok(userResponse);
    }

    @PutMapping(value = "/me")
    @SuppressWarnings({"Duplicates", "unchecked"})
    public ResponseEntity<?> editMe(@RequestBody Map<String, Object> userPatchMap) throws IOException {
        User user = SessionUtils.getLoggedUser();

        // Get ID Card
        String idCard = StringHelper.valueOf(userPatchMap.get(User.Patchable.ID_CARD));
        if (StringUtils.isBlank(idCard)) {
            idCard = user.getIdCard();
        }

        // Find the account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse() && StringUtils.isNotBlank(idCard)) {
            // Check if user is a militant already
            Militant militant = crmMilitantRepository.findMilitant(account, idCard,
                    Militant.SearchType.ELECTORAL_ROLL);
            if (militant != null) {
                userPatchMap.put(User.Patchable.FIRST_NAME, militant.getFirstName());
                userPatchMap.put(User.Patchable.LAST_NAME, militant.getLastName());
            }

            Map<String, Object> address = (Map<String, Object>) userPatchMap.get(User.Patchable.ADDRESS);

            // Update the militant information
            if (address != null && militant != null && militant.getExists() != null && militant.getExists()) {
                // Find the user
                Optional<User> userOptional = userService.findById(user.getId());
                if (!userOptional.isPresent()) throw new UserNotFoundException();

                // Create the user to update to the CRM
                User newUser = new User();
                newUser.setIdCard(idCard);
                newUser.setEmail(StringHelper.valueOf(userPatchMap.get(User.Patchable.EMAIL)));
                newUser.setPhone(StringHelper.valueOf(userPatchMap.get(User.Patchable.PHONE)));
                newUser.setPhoneVerificationCode("+1"); // TODO Update with the actual country code value for the user

                crmMilitantRepository.updateMilitant(account,
                        UserMapper.INSTANCE.toMilitantRequest(newUser, address));
            } else if (address != null) { // User is not militant yet
                crmUserRepository.addAddress(account, idCard, address);
            }

            // If address is set, remove it before updating our local copy of the user information because the CRM API
            // will handle it's own address structure
            if (address != null) {
                userPatchMap.remove(User.Patchable.ADDRESS);
                userPatchMap.put(User.Patchable.ID_CARD, idCard);
            }
        }

        // Update the user information
        User updatedUser = userService.patch(user.getId(), userPatchMap);
        UserResponse userResponse = userResponseMapper.map(updatedUser);

        // Search the user
        if (account.getConfiguration().isEnableCrmUse() && StringUtils.isNotBlank(updatedUser.getIdCard())) {
            // Search the user
            getUserResponseWithCbaHeader(account, userResponse);

            // Add user address from CRM
            AddressResponse address = crmUserRepository.findAddress(account, updatedUser.getIdCard());
            userResponse.setAddress(address);
        }

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping(value = "/me/donate")
    public ResponseEntity<?> doDonate(@RequestBody DonationRequest donation) throws PaymentException {
        Amount amount = new Amount(donation.getAmount(), donation.getCurrency());

        // Find the candidate for this user
        Optional<Candidate> candidateOptional = candidateService.findFirstCandidate();
        if (!candidateOptional.isPresent()) throw new CandidateNotFoundException();

        // Route the donation to a specific payment processor
        switch (donation.getPaymentProcessor()) {
            case STRIPE:
                paymentService.charge(donation.getSource(), amount, !StringUtils.isEmpty(donation.getDescription())
                        ? donation.getDescription() : "Special donation to your favorite candidate.");

                // Create candidate donation
                CandidateDonation candidateDonation = new CandidateDonation();
                candidateDonation.setAmount(amount.getTotal());
                candidateDonation.setCurrency(amount.getCurrency().name());

                // Increment candidate donation
                candidateService.donate(candidateOptional.get().getCandidateId(),
                        SessionUtils.getLoggedUser().getId(), candidateDonation);

                // TODO Increment candidate donation, search in Stripe total transactions
                // TODO Save stripe token locally
                // TODO Add payment metadata
                // TODO Send confirmation email to the user
                break;

            default:
                throw new UnsupportedOperationException("Unsupported payment processor: " +
                        donation.getPaymentProcessor().name());
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/me/devices", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<DeviceResponse>> getDevices() {
        User loggedUser = SessionUtils.getLoggedUser();

        //noinspection ConstantConditions
        List<DeviceResponse> devices = deviceMapper.map(deviceService.findAll(loggedUser.getId()));

        return ResponseEntity.ok(devices);
    }

    @PostMapping(value = "/me/devices")
    public ResponseEntity<IdResponse> saveDevices(@Valid @RequestBody DeviceRequest deviceRequest) {
        User loggedUser = SessionUtils.getLoggedUser();

        // Convert the request
        Device device = DeviceMapper.INSTANCE.from(deviceRequest);
        //noinspection ConstantConditions
        device.setUserId(userService.findByEmail(loggedUser.getEmail()).get());

        // Save the device
        Device newDevice = deviceService.save(device);

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(newDevice.getDeviceId()));
    }

    @GetMapping(value = "/me/devices/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<DeviceResponse> getDevice(@PathVariable String id) throws DeviceNotFoundException {
        Optional<Device> deviceOptional = deviceService.findById(id);
        if (!deviceOptional.isPresent()) throw new DeviceNotFoundException();

        DeviceResponse deviceResponse = DeviceMapper.INSTANCE.from(deviceOptional.get());

        return ResponseEntity.ok(deviceResponse);
    }

    @DeleteMapping(value = "/me/devices/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<DeviceResponse> deleteDevice(@PathVariable String id) throws DeviceNotFoundException {
        Optional<Device> deviceOptional = deviceService.findById(id);
        if (!deviceOptional.isPresent()) throw new DeviceNotFoundException();

        // Delete the device
        deviceService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(value = "/me/devices/{id}/firebase/token")
    public ResponseEntity<DeviceResponse> registerFirebaseToken(
            @PathVariable String id, @Valid @RequestBody DeviceRequest deviceRequest)
            throws DeviceNotFoundException {

        // Associate this firebase token with the user device
        deviceService.updateFirebaseToken(id, deviceRequest.getFirebaseToken());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/me/devices/{id}/firebase/token", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<DeviceResponse> removeFirebaseToken(@PathVariable String id) throws DeviceNotFoundException {

        // Remove the token
        deviceService.clearFirebaseToken(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/me/settings", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Map<String, Object>> getSettings() {
        User loggedUser = SessionUtils.getLoggedUser();

        return ResponseEntity.ok(loggedUser.getSettings());
    }

    @PostMapping(value = "/me/settings")
    public ResponseEntity<Map<String, Object>> saveSettings(@RequestBody Map<String, Object> settings) {
        User user = userService.saveSettings(SessionUtils.getLoggedUser().getId(), settings);

        return ResponseEntity.status(HttpStatus.CREATED).body(user.getSettings());
    }

    @PutMapping(value = "/me/settings")
    public ResponseEntity<Map<String, Object>> editSettings(@RequestBody Map<String, Object> settings) {
        User user = userService.patchSettings(SessionUtils.getLoggedUser().getId(), settings);

        return ResponseEntity.ok(user.getSettings());
    }

    @GetMapping(value = "/me/notifications", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<PageResponse<NotificationResponse>> getNotifications(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        // Construct the pageable
        Pageable pageable = DefaultPageable.builder()
                .withPage(page, Notification.DEFAULT_PAGE)
                .withSize(size, Notification.MAX_PAGE_SIZE)
                .withMaxSize(Notification.MAX_PAGE_SIZE)
                .build();

        // Execute the search
        Page<Notification> notificationPage = notificationService.findAll(pageable);

        // Prepare the response
        PageResponse<NotificationResponse> pageResponse = PageResponseBuilder.<NotificationResponse>builder()
                .withData(notificationsResponseMapper.map(notificationPage.getContent()))
                .withPagination(PaginationResponse.from(notificationPage))
                .build();

        return ResponseEntity.ok(pageResponse);
    }

    @DeleteMapping(value = "/me/notifications", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> deleteNotifications() {
        notificationService.deleteAllByUserId(SessionUtils.getLoggedUser().getId());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/me/notifications/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> deleteNotification(@PathVariable String id) {
        notificationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/me/social/tokens", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<UserSocialToken>> getSocialTokens() {
        UserMetaData metaData = SessionUtils.getLoggedUser().getMetaData();

        // Get the user tokens
        List<UserSocialToken> tokens = null;
        if (metaData != null) tokens = metaData.getSocialTokens();

        //noinspection ConstantConditions
        return ResponseEntity.ok(tokens);
    }

    @PostMapping(value = "/me/social/{channel}")
    public ResponseEntity<Void> saveSocialNetwork(
            @PathVariable("channel") SocialNetworkProvider provider, @RequestBody UserSocialToken token) {

        // Set provider
        token.setProvider(provider);

        userService.saveSocialToken(SessionUtils.getLoggedUser().getId(), token);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/me/social/{channel}")
    public ResponseEntity<Void> editSocialNetwork(
            @PathVariable("channel") SocialNetworkProvider provider, @RequestBody UserSocialToken token) {

        // Set provider
        token.setProvider(provider);

        userService.updateSocialToken(SessionUtils.getLoggedUser().getId(), token);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/me/social/{channel}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> deleteSocialNetwork(@PathVariable("channel") SocialNetworkProvider provider) {
        userService.deleteSocialToken(SessionUtils.getLoggedUser().getId(), provider);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/social/signin")
    public ResponseEntity<AccessToken> saveSocialSignUp(
            @RequestBody SocialTokenRequest socialToken, @RequestHeader("Accept-Language") String language,
            @RequestHeader("X-TZ-Id") String timeZoneId, @RequestHeader("X-TZ-Offset") Integer timeZoneOffset)
            throws IOException {

        // Add additional info
        socialToken.setLanguage(language);
        socialToken.setTimeZoneId(timeZoneId);
        socialToken.setTimeZoneOffset(timeZoneOffset);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUserFromSocialNetwork(socialToken));
    }

    private UserResponse getUserResponseWithCbaHeader(Account account, UserResponse user) throws IOException {
        // Find account
        try {
            CbaUser cbaUser = crmTeamMemberRepository.findUser(account, user.getIdCard());
            user.setCbaHeader(cbaUser != null ? cbaUser.getCbaHeader() : null);
        } catch (Exception ex) {
            user.setCbaHeader(null);
        }

        return user;
    }
}
