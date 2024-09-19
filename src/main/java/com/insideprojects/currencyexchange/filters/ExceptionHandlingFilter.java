package com.insideprojects.currencyexchange.filters;

import com.google.gson.Gson;
import com.insideprojects.currencyexchange.dto.ExceptionDto;
import com.insideprojects.currencyexchange.exception.CurrencyNotFoundException;
import com.insideprojects.currencyexchange.exception.CurrencyPairAlreadyExistsException;
import com.insideprojects.currencyexchange.exception.CurrencyPairNotFoundException;
import com.insideprojects.currencyexchange.exception.InvalidInputParametersException;
import com.insideprojects.currencyexchange.mapper.ExceptionMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class ExceptionHandlingFilter implements Filter {
    Logger logger = LogManager.getLogger(ExceptionHandlingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(request, response);

        } catch (InvalidInputParametersException | CurrencyPairAlreadyExistsException e) {
            logger.error("", e);
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ExceptionDto exceptionDto = ExceptionMapper.INSTANCE.exceptionToExceptionDto(e);
            response.getWriter().write(new Gson().toJson(exceptionDto));

        } catch (CurrencyNotFoundException | CurrencyPairNotFoundException e) {
            logger.error("", e);
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_NOT_FOUND);
            ExceptionDto exceptionDto = ExceptionMapper.INSTANCE.exceptionToExceptionDto(e);
            response.getWriter().write(new Gson().toJson(exceptionDto));

        }
    }
}
