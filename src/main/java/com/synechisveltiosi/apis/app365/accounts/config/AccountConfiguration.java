package com.synechisveltiosi.apis.app365.accounts.config;

import com.synechisveltiosi.apis.app365.common.SocialNetworkProvider;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountConfiguration implements Serializable {

    private static final long serialVersionUID = 0L;

    @JsonProperty("enableCrmUse")
    private Boolean enableCrmUse;

    @JsonProperty("crm")
    private CrmConfig crmConfig;

    @JsonProperty("mail")
    private MailConfig mailConfig;

    @JsonProperty("apiTokens")
    private final List<ApiTokenConfig> apiTokenConfigs = new ArrayList<>();

    @JsonProperty("database")
    private final List<DatabaseConfig> databaseConfigs = new ArrayList<>();

    @JsonProperty("socialTokens")
    private final List<SocialTokenConfig> socialTokenConfigs = new ArrayList<>();

    public Boolean isEnableCrmUse() {
        return enableCrmUse != null && enableCrmUse;
    }

    public void setEnableCrmUse(Boolean enableCrmUse) {
        this.enableCrmUse = enableCrmUse;
    }

    public CrmConfig getCrmConfig() {
        return crmConfig;
    }

    public void setCrmConfig(CrmConfig crmConfig) {
        this.crmConfig = crmConfig;
    }

    public MailConfig getMailConfig() {
        return mailConfig;
    }

    public void setMailConfig(MailConfig mailConfig) {
        this.mailConfig = mailConfig;
    }

    public List<ApiTokenConfig> getApiTokenConfigs() {
        return apiTokenConfigs;
    }

    public void addApiTokenConfig(ApiTokenConfig apiTokenConfig) {
        if (apiTokenConfig == null) throw new IllegalArgumentException("API token config cannot be null");

        // Add the API token configuration
        if (!apiTokenConfigs.contains(apiTokenConfig))
            apiTokenConfigs.add(apiTokenConfig);
    }

    public void removeApiTokenConfig(String service) {
        if (StringUtils.isBlank(service)) throw new IllegalArgumentException("Service name cannot be blank or null");

        // Create the API token config
        ApiTokenConfig config = new ApiTokenConfig();
        config.setService(service);

        // Remove config
        apiTokenConfigs.remove(config);
    }

    public List<DatabaseConfig> getDatabaseConfigs() {
        return databaseConfigs;
    }

    public void addDatabaseConfig(DatabaseConfig databaseConfig) {
        if (databaseConfig == null) throw new IllegalArgumentException("Database config cannot be null");

        // Add the database configuration
        if (!databaseConfigs.contains(databaseConfig))
            databaseConfigs.add(databaseConfig);
    }

    public void removeDatabaseConfig(String service) {
        if (StringUtils.isBlank(service)) throw new IllegalArgumentException("Service name cannot be blank or null");

        // Create the database config
        DatabaseConfig config = new DatabaseConfig();
        config.setService(service);

        // Remove config
        databaseConfigs.remove(config);
    }

    public List<SocialTokenConfig> getSocialTokenConfigs() {
        return socialTokenConfigs;
    }

    public void addSocialTokenConfig(SocialTokenConfig socialTokenConfig) {
        if (socialTokenConfig == null) throw new IllegalArgumentException("Social token config cannot be null");

        // Add the token configuration
        if (!socialTokenConfigs.contains(socialTokenConfig))
            socialTokenConfigs.add(socialTokenConfig);
    }

    public void removeSocialTokenConfig(SocialNetworkProvider provider) {
        if (provider == null) throw new IllegalArgumentException("Social network provider cannot be null");

        // Create the token config
        SocialTokenConfig token = new SocialTokenConfig();
        token.setProvider(provider);

        // Remove token
        socialTokenConfigs.remove(token);
    }
}
