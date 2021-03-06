package com.sapo.edu.managetransferslip.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    private Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
            logger.error("Error: " + e.getMessage());
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "E-> UnAuthorized");
    }
}
