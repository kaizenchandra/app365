package com.synechisveltiosi.apis.app365.common.http.request;

import com.synechisveltiosi.apis.app365.common.VisibilityType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class HttpHeader {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpHeader.class);

    private final HttpServletRequest request;
    private String subdomainRegex;

    public HttpHeader(HttpServletRequest request) {
        this.request = request;
    }

    public String getSubdomainRegex() {
        return subdomainRegex;
    }

    public void setSubdomainRegex(String subdomainRegex) {
        this.subdomainRegex = subdomainRegex;
    }

    public String getAcceptLanguage() {
        return request.getHeader("Accept-Language");
    }

    public String getTimeZoneId() {
        return request.getHeader("X-TZ-Id");
    }

    public VisibilityType getVisibility() {
        String visibility = request.getHeader("X-Visibility");
        if (!StringUtils.isBlank(visibility)) {
            return VisibilityType.valueOf(visibility.toUpperCase());
        }

        return null;
    }

    public Integer getTimeZoneOffset() {
        String offset = request.getHeader("X-TZ-Offset");
        if (offset != null)
            return Integer.parseInt(offset);

        return null;
    }

    public String getTenantId() {
        // Make the regex is not null or empty
        if (StringUtils.isBlank(getSubdomainRegex())) return null;

        // Get the tenant id
        String tenantId = request.getHeader("X-Tenant-Id");
        if (!StringUtils.isBlank(tenantId)) {
            return tenantId.toLowerCase();
        }

        // Find subdomain match from the host
//        Pattern pattern = Pattern.compile(getSubdomainRegex());
//        Matcher matcher = pattern.matcher(host);
//        if (matcher.find()) {
//            return matcher.group(0); // Return the first group match
//        }

        return null;
    }
}
