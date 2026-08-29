package com.synechisveltiosi.apis.app365.common.util.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {

    @SuppressWarnings("NullableProblems")
    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> aClass) {
        return new CaseInsensitiveEnumConverter<>(aClass);
    }
}
