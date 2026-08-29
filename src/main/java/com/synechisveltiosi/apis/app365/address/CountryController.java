package com.synechisveltiosi.apis.app365.address;

import com.synechisveltiosi.apis.app365.accounts.config.CrmConfig;
import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.accounts.exception.AccountNotFoundException;
import com.synechisveltiosi.apis.app365.accounts.service.AccountService;
import com.synechisveltiosi.apis.app365.address.entity.*;
import com.synechisveltiosi.apis.app365.address.service.*;
import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;
import com.synechisveltiosi.apis.app365.common.auth.repository.CrmOAuth2Repository;
import com.synechisveltiosi.apis.app365.common.dto.places.*;
import com.synechisveltiosi.apis.app365.common.http.request.HttpHeader;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotAcceptableException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.ServiceUnavailableException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.UnauthorizedException;
import com.synechisveltiosi.apis.app365.common.util.mapper.Mapper;
import com.synechisveltiosi.apis.app365.config.AppConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping(value = "/countries",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class CountryController {

    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);
    private final Mapper<Country, CountryResponse> countryResponseMapper;
    private final Mapper<State, StateResponse> stateResponseMapper;
    private final Mapper<Municipality, MunicipalityResponse> municipalityResponseMapper;
    private final Mapper<City, CityResponse> cityResponseMapper;
    private final Mapper<Sector, SectorResponse> sectorResponseMapper;
    private final CountryService countryService;
    private final StateService stateService;
    private final MunicipalityService municipalityService;
    private final CityService cityService;
    private final SectorService sectorService;
    private final AppConfig appConfig;
    private final AccountService accountService;
    private final HttpHeader httpHeader;
    private final CrmOAuth2Repository crmOAuth2Repository;


    @Autowired
    public CountryController(Mapper<Country, CountryResponse> countryResponseMapper,
                             Mapper<State, StateResponse> stateResponseMapper,
                             Mapper<Municipality, MunicipalityResponse> municipalityResponseMapper,
                             Mapper<City, CityResponse> cityResponseMapper,
                             Mapper<Sector, SectorResponse> sectorResponseMapper,
                             CountryService countryService, StateService stateService,
                             MunicipalityService municipalityService, CityService cityService,
                             SectorService sectorService,
                             AppConfig appConfig, AccountService accountService, HttpHeader httpHeader, CrmOAuth2Repository crmOAuth2Repository) {

        this.countryResponseMapper = countryResponseMapper;
        this.stateResponseMapper = stateResponseMapper;
        this.municipalityResponseMapper = municipalityResponseMapper;
        this.cityResponseMapper = cityResponseMapper;
        this.sectorResponseMapper = sectorResponseMapper;
        this.countryService = countryService;
        this.stateService = stateService;
        this.municipalityService = municipalityService;
        this.cityService = cityService;
        this.sectorService = sectorService;
        this.appConfig = appConfig;
        this.accountService = accountService;
        this.httpHeader = httpHeader;
        this.crmOAuth2Repository = crmOAuth2Repository;
    }

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getCountries() throws IOException {

        if (isEnableCrmUse()) return fetch(appConfig.getCrm().getGetEndpoints().getAddress().getCountries());

        return ResponseEntity.ok(countryResponseMapper.map(countryService.findAll()));
    }

    @GetMapping(value = "/{id}/states", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getStates(@PathVariable @NotBlank String id) throws IOException {

        if (isEnableCrmUse())
            return fetch(appConfig.getCrm().getGetEndpoints().getAddress().getStates().replace("{id}", id));

        return ResponseEntity.ok(stateResponseMapper.map(stateService.findAll(id)));
    }

    @GetMapping(value = "/{id}/district", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getDistrictByCountry(@PathVariable @NotBlank String id) throws IOException {

        if (isEnableCrmUse()) return fetch(
                appConfig.getCrm()
                        .getGetEndpoints()
                        .getAddress()
                        .getDistrictByCountry()
                        .replace("{id}", id)
        );

        throw new BadRequestException();
    }


    @GetMapping(value = "/states/{id}/district", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getDistrictByState(@PathVariable @NotBlank String id) throws IOException {

        if (isEnableCrmUse()) return fetch(
                appConfig.getCrm()
                        .getGetEndpoints()
                        .getAddress()
                        .getDistrictByState()
                        .replace("{id}", id)
        );

        throw new BadRequestException();
    }

    @GetMapping(value = "/{id}/district/{districtID}/states", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getStatesByCountryAndDistrict(@PathVariable @NotBlank String id, @PathVariable @NotBlank String districtID) throws IOException {

        if (isEnableCrmUse()) return fetch(
                appConfig.getCrm()
                        .getGetEndpoints()
                        .getAddress()
                        .getStatesByCountryAndDistrict()
                        .replace("{id}", id)
                        .replace("{districtID}", districtID)
        );

        throw new BadRequestException();
    }

    @GetMapping(value = "/district/{id}/municipalities", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getMunicipalitiesByDistrict(@PathVariable @NotBlank String id) throws IOException {

        if (isEnableCrmUse()) return fetch(
                appConfig.getCrm()
                        .getGetEndpoints()
                        .getAddress()
                        .getMunicipalitiesByDistrict()
                        .replace("{id}", id)
        );

        throw new BadRequestException();
    }

    @GetMapping(value = "/states/municipalities/{id}/district/{districtID}/region", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getRegionByMunicipalityAndDistrict(@PathVariable @NotBlank String id, @PathVariable String districtID) throws IOException {

        if (isEnableCrmUse()) return fetch(
                appConfig.getCrm()
                        .getGetEndpoints()
                        .getAddress()
                        .getRegionByMunicipalityAndDistrict()
                        .replace("{id}", id)
                        .replace("{districtID}", districtID)
        );

        throw new BadRequestException();
    }

    @GetMapping(value = "/states/municipalities/{id}/district/{districtID}/zone", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getZoneByMunicipalityAndDistrict(@PathVariable @NotBlank String id, @PathVariable String districtID) throws IOException {

        if (isEnableCrmUse()) return fetch(
                appConfig.getCrm()
                        .getGetEndpoints()
                        .getAddress()
                        .getZoneByMunicipalityAndDistrict()
                        .replace("{id}", id)
                        .replace("{districtID}", districtID)
        );

        throw new BadRequestException();
    }

    @GetMapping(value = "/states/municipal-district/{id}/zone", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getZoneByMunicipalDistrict(@PathVariable @NotBlank String id) throws IOException {

        if (isEnableCrmUse()) return fetch(
                appConfig.getCrm()
                        .getGetEndpoints()
                        .getAddress()
                        .getZoneByMunicipalDistrict()
                        .replace("{id}", id)
        );

        throw new BadRequestException();
    }

    @GetMapping(value = "/states/region/{id}/zone", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getZoneByRegion(@PathVariable @NotBlank String id) throws IOException {

        if (isEnableCrmUse()) return fetch(
                appConfig.getCrm()
                        .getGetEndpoints()
                        .getAddress()
                        .getZoneByRegion()
                        .replace("{id}", id)
        );

        throw new BadRequestException();
    }

    @GetMapping(value = "/states/{id}/municipalities", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getMunicipalities(@PathVariable @NotBlank String id) throws IOException {

        if (isEnableCrmUse())
            return fetch(appConfig.getCrm().getGetEndpoints().getAddress().getMunicipalities().replace("{id}", id));

        return ResponseEntity.ok(municipalityResponseMapper.map(municipalityService.findAll(id)));
    }

    @GetMapping(value = "/states/municipalities/{id}/cities", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getCities(@PathVariable @NotBlank String id) throws IOException {

        if (isEnableCrmUse())
            return fetch(appConfig.getCrm().getGetEndpoints().getAddress().getCities().replace("{id}", id));

        return ResponseEntity.ok(cityResponseMapper.map(cityService.findAll(id)));
    }

    @GetMapping(value = "/states/municipalities/cities/{id}/sectors")
    public ResponseEntity<?> getSections(@PathVariable @NotBlank String id) throws IOException {

        if (isEnableCrmUse())
            return fetch(appConfig.getCrm().getGetEndpoints().getAddress().getSections().replace("{id}", id));

        return ResponseEntity.ok(sectorResponseMapper.map(sectorService.findAll(id)));
    }

    @GetMapping(value = "/states/municipalities/cities/sectors/{id}/demarcation", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getSectors(@PathVariable @NotBlank String id) throws IOException {

        if (isEnableCrmUse())
            return fetch(appConfig.getCrm().getGetEndpoints().getAddress().getSectors().replace("{id}", id));

        return ResponseEntity.ok(sectorResponseMapper.map(new ArrayList<>()));
    }

    private boolean isEnableCrmUse() {
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);

        return account.getConfiguration().isEnableCrmUse();
    }

    private ResponseEntity<?> fetch(String url) throws IOException {

        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);

        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();
        AccessToken accessToken = crmOAuth2Repository.authenticate(account);

        OkHttpClient client = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder()
                .url(crmConfig.getBaseUrl() + url)
                .addHeader(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken())
                .addHeader("X-TenantID", crmConfig.getTenantId());

        Response response = client.newCall(requestBuilder.build()).execute();

        // Retry request if the access token get expired
        if (response.code() == 401) {
            accessToken = crmOAuth2Repository.refreshToken(account);

            // Modify request to add new access token
            requestBuilder.header(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken());
            response = client.newCall(requestBuilder.build()).execute();
        }

        // Deserialize request response
        switch (response.code()) {
            case 200:
                // Process the response

                return ResponseEntity.ok(response.body().string());

            case 401:

                throw new UnauthorizedException();

            case 503:
                throw new ServiceUnavailableException();
            default:
                String message = "Unhandled http code";
                logger.error(message + ". OAuth response: " + response);
                throw new NotAcceptableException(message);

        }

    }
}
