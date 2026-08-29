package com.synechisveltiosi.apis.app365.users.service;

import com.synechisveltiosi.apis.app365.actions.entity.ActionType;
import com.synechisveltiosi.apis.app365.address.entity.Address;
import com.synechisveltiosi.apis.app365.address.helper.AddressHelper;
import com.synechisveltiosi.apis.app365.address.service.AddressService;
import com.synechisveltiosi.apis.app365.common.SocialNetworkProvider;
import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;
import com.synechisveltiosi.apis.app365.common.auth.dto.OAuthUserRequest;
import com.synechisveltiosi.apis.app365.common.auth.dto.OAuthUserResponse;
import com.synechisveltiosi.apis.app365.common.auth.dto.SocialTokenRequest;
import com.synechisveltiosi.apis.app365.common.auth.service.OAuth2Service;
import com.synechisveltiosi.apis.app365.common.repository.DefaultRsqlRepository;
import com.synechisveltiosi.apis.app365.common.repository.RsqlRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.ApiStatusCode;
import com.synechisveltiosi.apis.app365.common.rest.response.ErrorResponse;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.*;
import com.synechisveltiosi.apis.app365.common.service.social.Google.GoogleService;
import com.synechisveltiosi.apis.app365.common.service.social.SocialNetworkService;
import com.synechisveltiosi.apis.app365.common.service.social.facebook.FacebookService;
import com.synechisveltiosi.apis.app365.common.sms.PhoneVerification;
import com.synechisveltiosi.apis.app365.common.sms.PhoneVerificationChannel;
import com.synechisveltiosi.apis.app365.common.sms.PhoneVerificationService;
import com.synechisveltiosi.apis.app365.common.util.RandomUtils;
import com.synechisveltiosi.apis.app365.common.util.RegistrationChannel;
import com.synechisveltiosi.apis.app365.common.util.StringHelper;
import com.synechisveltiosi.apis.app365.common.util.UserSocialToken;
import com.synechisveltiosi.apis.app365.users.UserMetaData;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.synechisveltiosi.apis.app365.users.event.UserActionOccurredEvent;
import com.synechisveltiosi.apis.app365.users.exception.DuplicateUserException;
import com.synechisveltiosi.apis.app365.users.exception.UserNotFoundException;
import com.synechisveltiosi.apis.app365.users.mapper.UserMapper;
import com.synechisveltiosi.apis.app365.users.repository.UserRepository;
import cz.jirutka.rsql.parser.RSQLParserException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final long MINUTES_IN_A_DAY = 60 * 24;

    private final UserRepository userRepository;
    private final RsqlRepository<User> rsqlRepository;
    private final SocialNetworkService googleService;
    private final SocialNetworkService facebookService;
    private final OAuth2Service oAuth2Service;
    private final PhoneVerificationService phoneVerificationService;
    private final AddressService addressService;
    private final ApplicationEventPublisher publisher;
    private final int maxPhoneVerificationCodePerMinute;
    private final int maxPhoneVerificationCodePerDay;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, FacebookService facebookService, GoogleService googleService,
                           OAuth2Service oAuth2Service,
                           @Qualifier("textMagicSmsService") PhoneVerificationService phoneVerificationService,
                           AddressService addressService, EntityManager entityManager,
                           ApplicationEventPublisher publisher,
                           @Value("${app365.twilio.max-phone-verification-code-per-minute}") int maxPhoneVerificationCodePerMinute,
                           @Value("${app365.twilio.max-phone-verification-code-per-day}") int maxPhoneVerificationCodePerDay) {

        this.userRepository = userRepository;
        this.facebookService = facebookService;
        this.googleService = googleService;
        this.oAuth2Service = oAuth2Service;
        this.phoneVerificationService = phoneVerificationService;
        this.addressService = addressService;
        this.publisher = publisher;
        this.maxPhoneVerificationCodePerMinute = maxPhoneVerificationCodePerMinute;
        this.maxPhoneVerificationCodePerDay = maxPhoneVerificationCodePerDay;

        rsqlRepository = new DefaultRsqlRepository<>(entityManager, User.class)
                .withAllowedFields(User.SEARCHABLE_FIELDS);
    }

    @Override
    public Page<User> findAll(String query, Pageable pageable) throws RSQLParserException {
        pageable = pageable == null ? Pageable.unpaged() : pageable;

        if (StringUtils.isEmpty(query))
            return userRepository.findAll(pageable);

        return rsqlRepository.findAll(query, pageable);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findByUserId(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public User save(User user) throws IOException, ConflictException, UnsupportedChannelException {
        // Validate user is not null
        if (user == null) throw new BadRequestException("Invalid user.");

        // Validate registration channel channel
        if (user.getChannel() == null) throw new UnsupportedChannelException("Invalid registration channel.");

        // Validate the email and name
        if (!EmailValidator.getInstance().isValid(user.getEmail()))
            throw new BadRequestException("Invalid email address.");

        // Type conversion
        OAuthUserRequest userRequest = UserMapper.INSTANCE.toOAuthUserRequest(user);

        ConflictException duplicatedUserException = null;
        ConstraintViolationException violationException = null;
        User newUser = null;

        try {
            // Create or get the old user
            OAuthUserResponse authUserResponse = this.oAuth2Service.createUser(userRequest);

            // Set the auth user id
            user.setAuthUserId(authUserResponse.getId());

            // Generate user phone verification code
            String code = String.format("%06d", RandomUtils.generate(0, 999999));
            user.setPhoneVerificationCode(code);

            // Create the user locally
            newUser = userRepository.save(user);
        } catch (ConflictException ex) {
            duplicatedUserException = ex;
        } catch (ConstraintViolationException ex) {
            violationException = ex;
        }

        // The email already taken by someone else, translate the exception
        if (user.getChannel() == RegistrationChannel.APPLE_POLITICAL
                && (duplicatedUserException != null || violationException != null)) {

            throw new ConflictException("User already exist.");
        }

        // Make sure were are dealing with the same user from the social network provider
        if (duplicatedUserException != null) {
            Optional<User> oldUser = findByEmail(user.getEmail());
            if (oldUser.isPresent() && oldUser.get().getChannel() == user.getChannel()) { // Update user password
                newUser = oldUser.get();
                // TODO Update password in the future to something more cryptic or reset it to a random UUID
            } else {
                throw duplicatedUserException;
            }
        }

        // The user already exist someone, translate the exception
        if (violationException != null) {
            throw new ConflictException("User already exist.");
        }

        // Send the verification code if phone information is available
        if (!StringUtils.isEmpty(newUser.getPhoneCountryCode()) && !StringUtils.isEmpty(newUser.getPhone())) {
            PhoneVerification phoneVerification = new PhoneVerification();
            phoneVerification.setPhoneCountryCode(user.getPhoneCountryCode());
            phoneVerification.setPhone(user.getPhone());
            phoneVerification.setChannel(PhoneVerificationChannel.SMS);
            phoneVerification.setCode(user.getPhoneVerificationCode());

            dispatchPhoneVerificationCode(user.getChannel(), phoneVerification, newUser);

            // Update the last sent verification phone code time
            newUser.setLastPhoneVerificationCodeSentAt(new Date());
            newUser.incrementEmittedPhoneCodeCount();
        }

        // Persist changes
        this.update(newUser);

        return newUser;
    }

    @Transactional
    @Override
    public AccessToken createUserFromSocialNetwork(SocialTokenRequest socialToken) throws IOException,
            ConflictException, UnsupportedChannelException {

        // Load the user
        User user;

        if (socialToken.getChannel() == RegistrationChannel.GOOGLE) {
            user = googleService.loadWithToken(socialToken.getToken());
        } else if (socialToken.getChannel() == RegistrationChannel.FACEBOOK) {
            user = facebookService.loadWithToken(socialToken.getToken());
        } else {
            throw new UnsupportedChannelException("Invalid registration channel.");
        }

        // Make the usy
        if (user == null) throw new BadRequestException("Invalid user from social network provider.");

        // Generate user password TODO Change password generation strategy
        String password = DigestUtils.sha1Hex(user.getEmail() + user.getChannel().name() + user.getId());
        user.setPassword(password);
        user.setVerified(Boolean.TRUE);
        user.setActive(Boolean.TRUE);
        user.setLanguage(socialToken.getLanguage());
        user.setTimeZone(socialToken.getTimeZoneId());
        user.setTimeZoneOffset(socialToken.getTimeZoneOffset());

        // Create the user
        this.save(user);

        // Request an access token for the user and return it
        return this.oAuth2Service.authenticate(user.getEmail(), password);
    }

    @Transactional
    @Override
    public void verifyPhoneNumber(String id, PhoneVerification phoneVerification) throws Exception {
        if (StringUtils.isEmpty(id)) throw new BadRequestException("Invalid user id.");

        Optional<User> userOptional = findById(id);
        if (!userOptional.isPresent()) throw new NotFoundException("User not found");

        if (userOptional.get().isPhoneVerified())
            throw new NotModifiedException("This phone number is already verified.");

        User user = userOptional.get();

        // Create phone verification
        phoneVerification.setPhoneCountryCode(user.getPhoneCountryCode());
        phoneVerification.setPhone(user.getPhone());

        // Verify the phone number
        phoneVerificationService.verify(user, phoneVerification);

        // Verify and activate the user if he was registered manual
        if (user.getChannel() == RegistrationChannel.APPLE_POLITICAL) {
            user.setVerified(true);
            user.setActive(true);
            user.setPhoneVerificationCode(null);
        }

        user.setPhoneVerified(true);

        // Persist user state
        this.update(user);

        // Verify the user in the authentication service
        this.oAuth2Service.activeUser(user.getAuthUserId(), user.isActive());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(user.getId(), ActionType.PHONE));
    }

    @Transactional
    @Override
    public void resendPhoneVerificationCode(String id) throws Exception {
        if (StringUtils.isEmpty(id)) throw new BadRequestException("Invalid user id.");

        Optional<User> userOptional = findById(id);
        if (!userOptional.isPresent()) throw new NotFoundException("User not found");

        if (userOptional.get().isPhoneVerified())
            throw new NotModifiedException("This phone number is already verified.");

        User user = userOptional.get();

        // Generate user phone verification code
        String code = String.format("%06d", RandomUtils.generate(0, 999999));
        user.setPhoneVerificationCode(code);

        PhoneVerification phoneVerification = new PhoneVerification();
        phoneVerification.setPhoneCountryCode(user.getPhoneCountryCode());
        phoneVerification.setPhone(user.getPhone());
        phoneVerification.setCode(user.getPhoneVerificationCode());

        // Calculate elapse minutes since the last sent phone verification code
        long elapsedTime = 0;
        if (user.getLastPhoneVerificationCodeSentAt() != null) {
            elapsedTime = (long) ((System.currentTimeMillis() - user.getLastPhoneVerificationCodeSentAt().getTime())
                    / (double) (1000 * 60));
        }

        // Throttle the resend verification code
        if (elapsedTime < MINUTES_IN_A_DAY && user.getEmittedPhoneCodeCount() >= maxPhoneVerificationCodePerDay) {
            throw new TooManyRequestException("You reached the maximum allowed messages per day.");
        } else if (elapsedTime > MINUTES_IN_A_DAY) {
            user.setLastPhoneVerificationCodeSentAt(new Date());
            user.resetEmittedPhoneCodeCount();
        }

        if (elapsedTime <= maxPhoneVerificationCodePerMinute)
            throw new TooManyRequestException("You reached the maximum allowed messages per minute.");

        // Verify the phone number
        phoneVerificationService.sendVerificationCode(user, phoneVerification);

        // Update the last sent verification phone code time
        user.setLastPhoneVerificationCodeSentAt(new Date());
        user.incrementEmittedPhoneCodeCount();

        // Persist user state
        this.update(user);
    }

    @Override
    public String resendPhoneVerificationCode(PhoneVerification phoneVerification) throws Exception {
        Optional<User> userOptional = this.findByEmail(phoneVerification.getEmail());
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        User user = userOptional.get();
        if (!StringUtils.equals(user.getPhone(), phoneVerification.getPhone()))
            throw new BadRequestException("Phone number mismatch.");

        // Process the phone verification code resend
        this.resendPhoneVerificationCode(user.getUserId());

        return user.getUserId();
    }

    @Transactional
    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User patch(Long id, Map<String, Object> userMap) {
        if (userMap == null || userMap.isEmpty()) throw new BadRequestException("User information cannot be blank.");

        Optional<User> userOptional = findById(id);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Remove all supported keys and assume the rest still in the set as invalid
        Set<String> invalidKeys = new HashSet<>(userMap.keySet());
        invalidKeys.removeAll(User.PATCHABLE_FIELDS);

        // If set is not empty, with have some invalid keys
        if (!invalidKeys.isEmpty()) {
            throw new BadRequestException(String.format("The following fields are not supported: %s",
                    StringUtils.join(invalidKeys, " , ")));
        }

        // Build the patch object
        User userPatch = userOptional.get();

        // Retrieve the first name
        if (userMap.containsKey(User.Patchable.FIRST_NAME)) {
            userPatch.setFirstName(StringHelper.valueOf(userMap.get(User.Patchable.FIRST_NAME)));
        }

        // Retrieve the last name
        if (userMap.containsKey(User.Patchable.LAST_NAME)) {
            userPatch.setLastName(StringHelper.valueOf(userMap.get(User.Patchable.LAST_NAME)));
        }

        // Retrieve the nickname
        if (userMap.containsKey(User.Patchable.NICKNAME)) {
            userPatch.setNickname(StringHelper.valueOf(userMap.get(User.Patchable.NICKNAME)));
        }

        // Retrieve the id card
        if (userMap.containsKey(User.Patchable.ID_CARD)) {
            userPatch.setIdCard(StringHelper.valueOf(userMap.get(User.Patchable.ID_CARD)));
        }

        // Retrieve the phone number
        if (userMap.containsKey(User.Patchable.PHONE)) {
            // TODO Implement flow to update phone
            userPatch.setPhoneVerified(Boolean.FALSE);
            userPatch.resetEmittedPhoneCodeCount();
            userPatch.setPhone(StringHelper.valueOf(userMap.get(User.Patchable.PHONE)));
        }

        // Retrieve the email address
        if (userMap.containsKey(User.Patchable.EMAIL)) {
            // TODO Implement flow to update email
            userPatch.setEmail(StringHelper.valueOf(userMap.get(User.Patchable.EMAIL)));
        }

        // Retrieve the address
        if (userMap.containsKey(User.Patchable.ADDRESS)) {
            if (!(userMap.get(User.Patchable.ADDRESS) instanceof Map))
                throw new BadRequestException("Address should a map.");

            //noinspection unchecked
            Address address = AddressHelper.from((Map<String, Object>) userMap.get(User.Patchable.ADDRESS));
            address.setUserId(userPatch);

            // Save the address
            userPatch.setAddress(addressService.patch(address));
        }

        // Save the user info
        try {
            // Update the user information
            User patchedUser = this.update(userPatch);

            //Notify observers about the user action
            publisher.publishEvent(new UserActionOccurredEvent(patchedUser.getId(), ActionType.ID_CARD,
                    ActionType.PHONE, ActionType.ADDRESS));

            return patchedUser;
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateUserException();
        }
    }

    @Transactional
    @Override
    public User saveSettings(Long id, Map<String, Object> settings) {
        Optional<User> userOptional = findById(id);
        if (!userOptional.isPresent()) throw new UserNotFoundException();
        if (settings == null || settings.isEmpty()) throw new BadRequestException("User settings should not be empty.");

        User user = userOptional.get();
        user.setSettings(settings);

        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User patchSettings(Long id, Map<String, Object> settings) {
        Optional<User> userOptional = findById(id);
        if (!userOptional.isPresent()) throw new UserNotFoundException();
        if (settings == null || settings.isEmpty()) throw new BadRequestException("User settings should not be empty.");

        User user = userOptional.get();
        if (user.getSettings() == null) user.setSettings(new HashMap<>());

        // Path user settings
        user.getSettings().putAll(settings);

        return userRepository.save(user);
    }

    @Override
    public List<UserSocialToken> saveSocialToken(Long userId, UserSocialToken socialToken) {
        Optional<User> userOptional = findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        User user = userOptional.get();

        // Initialize user meta data
        if (user.getMetaData() == null) user.setMetaData(new UserMetaData());

        // Add the social token
        user.getMetaData().addSocialToken(socialToken);

        // Update user
        return this.update(user).getMetaData().getSocialTokens();
    }

    @Override
    public List<UserSocialToken> updateSocialToken(Long userId, UserSocialToken socialToken) {
        Optional<User> userOptional = findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        User user = userOptional.get();

        // Initialize user meta data
        if (user.getMetaData() == null) user.setMetaData(new UserMetaData());

        // Remove existing token for this provider
        user.getMetaData().removeSocialToken(socialToken.getProvider());

        // Add the social token
        user.getMetaData().addSocialToken(socialToken);

        // Update user
        return this.update(user).getMetaData().getSocialTokens();
    }

    @Override
    public List<UserSocialToken> deleteSocialToken(Long userId, SocialNetworkProvider provider) {
        Optional<User> userOptional = findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        User user = userOptional.get();

        // Initialize user meta data
        if (user.getMetaData() == null) user.setMetaData(new UserMetaData());

        // Remove existing token for this provider
        user.getMetaData().removeSocialToken(provider);

        // Update user
        return this.update(user).getMetaData().getSocialTokens();
    }

    @Transactional
    @Override
    public void delete(String id) {
        userRepository.deleteByUserId(id);
    }

    private void dispatchPhoneVerificationCode(RegistrationChannel channel,
                                               PhoneVerification phoneVerification, User newUser) {

        try {
            if (channel == RegistrationChannel.APPLE_POLITICAL) {
                phoneVerificationService.sendVerificationCode(newUser, phoneVerification);
            }
        } catch (Exception ex) {
            logger.warn(ex.getMessage(), ex);

            Map<String, String> data = new HashMap<>();
            data.put("id", newUser.getUserId());

            throw new MultiStatusException("Account created", data,
                    new ErrorResponse(ApiStatusCode.PHONE_VERIFICATION_FAILED)
                            .withMessage("Failed to send phone verification code."));
        }
    }
}
