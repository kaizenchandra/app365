package com.synechisveltiosi.apis.app365.security;

import com.synechisveltiosi.apis.app365.common.rest.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public DefaultAccessDeniedHandler() {

    }

    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException, ServletException {
        ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());

        // Log access denied exception
        logger.error("Access denied.", ex);


        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(error.getStatus());
        response.getOutputStream().println(error.toString());
    }
}
