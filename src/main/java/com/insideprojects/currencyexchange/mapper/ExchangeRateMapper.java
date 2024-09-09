package com.insideprojects.currencyexchange.mapper;

import com.insideprojects.currencyexchange.dto.ExchangeDto;
import com.insideprojects.currencyexchange.model.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExchangeRateMapper {
    ExchangeRateMapper INSTANCE = Mappers.getMapper(ExchangeRateMapper.class);

    ExchangeDto exchangeToExchangeDto(ExchangeRate exchangeRate);

    ExchangeRate exchangeDtoToExchangeRate(ExchangeDto exchangeDto);
}
