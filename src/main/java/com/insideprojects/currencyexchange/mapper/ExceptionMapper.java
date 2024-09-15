package com.insideprojects.currencyexchange.mapper;

import com.insideprojects.currencyexchange.dto.ExceptionDto;
import com.insideprojects.currencyexchange.exception.InvalidInputParametersException;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExceptionMapper {
    ExceptionMapper INSTANCE = Mappers.getMapper(ExceptionMapper.class);

    ExceptionDto exceptionToExceptionDto(Exception exception);
}
