package com.igrowker.altour.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CorsFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    HttpServletResponse res = (HttpServletResponse) servletResponse;
    HttpServletRequest req = (HttpServletRequest) servletRequest;

    res.setHeader("Access-Control-Allow-Origin", "http://0.0.0.0:8080"); // todo agregar url front!
    res.setHeader("Access-Control-Allow-Credentials", "true");
    res.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, PATCH");
    res.setHeader("Access-Control-Allow-Headers", "authorization, Content-Type, Accept, x-auth-token, Location, customerId, link, Refresh-Token");
    res.setHeader("Access-Control-Expose-Headers", "x-auth-token, authorization, X-Total-Pages, Content-Disposition, Location, link, Refresh-Token");

    if (req.getMethod().equalsIgnoreCase("OPTIONS")) {
        res.setStatus(HttpServletResponse.SC_OK);
        return;
    }
    filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
