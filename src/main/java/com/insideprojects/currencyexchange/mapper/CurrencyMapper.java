package com.insideprojects.currencyexchange.mapper;

import com.insideprojects.currencyexchange.dto.CurrencyDto;
import com.insideprojects.currencyexchange.model.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrencyMapper {
    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    CurrencyDto currencyToCurrencyDto (Currency currency);

    Currency currencyDtoToCurrency(CurrencyDto currencyDto);

}
