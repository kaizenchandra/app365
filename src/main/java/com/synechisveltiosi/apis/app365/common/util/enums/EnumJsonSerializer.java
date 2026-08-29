package com.synechisveltiosi.apis.app365.common.util.enums;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class EnumJsonSerializer extends StdSerializer<Enum> {

    public EnumJsonSerializer() {
        super(Enum.class);
    }

    @Override
    public void serialize(Enum anEnum, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(anEnum.name().toLowerCase());
    }
}
