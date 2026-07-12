package com.softdev.system.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        String[] escaped = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            escaped[i] = stripXss(values[i]);
        }
        return escaped;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return stripXss(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return stripXss(value);
    }

    private String stripXss(String value) {
        if (value == null) {
            return null;
        }
        return value.replaceAll("<", "&lt;")
                     .replaceAll(">", "&gt;")
                     .replaceAll("\\(", "&#40;")
                     .replaceAll("\\)", "&#41;")
                     .replaceAll("'", "&#39;")
                     .replaceAll("eval\\((.*)\\)", "")
                     .replaceAll("[\"'][\\s]*javascript:(.*)[\"']", "\"\"")
                     .replaceAll("script", "");
    }
}
