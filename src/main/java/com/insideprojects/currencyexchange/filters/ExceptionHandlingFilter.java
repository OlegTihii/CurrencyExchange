package com.insideprojects.currencyexchange.filters;

import com.google.gson.Gson;
import com.insideprojects.currencyexchange.dto.ExceptionDto;
import com.insideprojects.currencyexchange.exception.InvalidInputParametersException;
import com.insideprojects.currencyexchange.mapper.ExceptionMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class ExceptionHandlingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(request, response);

        } catch (InvalidInputParametersException e) {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ExceptionDto exceptionDto = ExceptionMapper.INSTANCE.exceptionToExceptionDto(e);
            response.getWriter().write(new Gson().toJson(exceptionDto));
        }
    }
}
