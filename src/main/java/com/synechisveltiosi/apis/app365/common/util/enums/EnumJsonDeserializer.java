package com.synechisveltiosi.apis.app365.common.util.enums;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;

import java.io.IOException;

public class EnumJsonDeserializer extends BeanDeserializerModifier {

    @Override
    public JsonDeserializer<?> modifyEnumDeserializer(DeserializationConfig config, JavaType type,
                                                      BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
        return new JsonDeserializer<Enum>() {

            @Override
            public Enum deserialize(JsonParser jp, DeserializationContext context) throws IOException {
                //noinspection unchecked
                Class<? extends Enum> rawClass = (Class<Enum<?>>) type.getRawClass();

                //noinspection unchecked
                return Enum.valueOf(rawClass, jp.getValueAsString().toUpperCase());
            }
        };
    }
}
