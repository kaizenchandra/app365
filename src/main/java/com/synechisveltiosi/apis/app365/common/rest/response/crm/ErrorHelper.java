package com.synechisveltiosi.apis.app365.common.rest.response.crm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synechisveltiosi.apis.app365.campaign.*;
import com.synechisveltiosi.apis.app365.campaign.exception.*;
import com.synechisveltiosi.apis.app365.common.rest.response.ErrorResponse;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.*;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class ErrorHelper {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHelper.class);

    @SuppressWarnings("Duplicates")
    public static RestResponseException handleExceptions(ObjectMapper objectMapper, Response response) throws RuntimeException, IOException {

        RestResponseException exception = new NotAcceptableException();

        ResponseBody body = response.body();
        if (body != null) {
            String jsonError = body.string();

            ErrorResponse errorResponse = objectMapper.readValue(jsonError, ErrorResponse.class);
            processErrorSubCodes(errorResponse);
        }

        switch (response.code()) {
            case 400:
                exception = new BadRequestException(response.message());
                break;

            case 401:
                exception = new UnauthorizedException(response.message());
                break;

            case 404:
                exception = new NotFoundException(response.message());
                break;

            case 409:
                exception = new ConflictException(response.message());

                break;

            case 503:
                exception = new ServiceUnavailableException(response.message());
                break;

            default:
                String message = "Unhandled http code";
                logger.error(message + ". OAuth response: " + response);
        }

        return exception;
    }


    private static void processErrorSubCodes(ErrorResponse errorResponse) {
        if (errorResponse == null || errorResponse.getCode() == null) return;

        switch (errorResponse.getCode()) {
            case 409002:
                throw new CoordinatorDuplicatedException();

            case 409003:
                throw new MemberDuplicatedException();

            case 409004:
                throw new SelfCoordinatorRegistrationException();

            case 409005:
                throw new MilitantRequiredException();

            case 409001:
            case 409006:
                throw new MilitantDuplicatedException();

            case 409007:
                throw new PhoneDuplicatedException();

            case 409008:
                throw new EmailDuplicatedException();

            case 409010:
                throw new PhoneAlreadyRegisteredException();

            case 409011:
                throw new EmailAddressAlreadyRegisteredException();

            case 409013:
                throw new CbaHeaderDuplicatedException();

            case 404001:
                throw new MilitantNotFoundException();

            case 404002:
                throw new TeamMemberNotFoundException();

            case 404003:
                throw new CoordinatorNotFoundException();

            case 404004:
                throw new TeamNotFoundException();

            case 400005:
                throw new CoordinatorNotFoundException();

            case 400003:
                throw new PhoneInvalidException();

            case 400004:
                throw new EmailAddressInvalidException();
        }
    }
}
