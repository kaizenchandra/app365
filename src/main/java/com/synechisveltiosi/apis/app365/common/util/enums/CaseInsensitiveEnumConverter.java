package com.synechisveltiosi.apis.app365.common.util.enums;

import org.springframework.core.convert.converter.Converter;

public class CaseInsensitiveEnumConverter<T extends Enum> implements Converter<String, T> {

    private final Class<T> enumType;

    public CaseInsensitiveEnumConverter(Class<T> enumType) {
        this.enumType = enumType;
    }

    @SuppressWarnings({"unchecked", "NullableProblems"})
    @Override
    public T convert(String source) {
        return (T) Enum.valueOf(this.enumType, source.trim().toUpperCase());
    }
}
