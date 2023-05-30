package com.finalaeonproject.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalaeonproject.util.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    Response<Object> responseBody = new Response<>(false, "Unauthorized", null);

    ObjectMapper objectMapper = new ObjectMapper();
    String jsonResponseBody = objectMapper.writeValueAsString(responseBody);

    response.setContentType("application/json");
    response.getWriter().write(jsonResponseBody);
  }
}
