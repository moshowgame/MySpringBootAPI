package com.softdev.system.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.regex.Pattern;

@Slf4j
public class SqlInjectionFilter implements Filter {

    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
            "(?i)(\\b(union|select|insert|update|delete|drop|alter|truncate|exec|execute)\\b.*\\b(from|into|table|database|where)\\b)"
                    + "|(?i)(\\b(or|and)\\b\\s+\\d+\\s*=\\s*\\d+)"
                    + "|(?i)(--|;|/\\*|\\*/)",
            Pattern.CASE_INSENSITIVE
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String queryString = httpRequest.getQueryString();
        if (queryString != null && SQL_INJECTION_PATTERN.matcher(queryString).find()) {
            log.warn("SQL injection detected in query string: {}", queryString);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
            return;
        }

        chain.doFilter(request, response);
    }
}
