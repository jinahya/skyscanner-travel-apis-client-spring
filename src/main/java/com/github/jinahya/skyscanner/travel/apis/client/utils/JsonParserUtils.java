package com.github.jinahya.skyscanner.travel.apis.client.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

@Slf4j
public final class JsonParserUtils {

    public static <T> void parseArray(final JsonParser jsonParser, final String fieldName, final Class<T> elementClass,
                                      final Consumer<? super T> elementConsumer)
            throws IOException {
        requireNonNull(jsonParser, "jsonParser is null");
        requireNonNull(fieldName, "fieldName is null");
        requireNonNull(elementClass, "elementClass is null");
        requireNonNull(elementConsumer, "elementConsumer is null");
        final JsonToken rootObject = jsonParser.nextToken();
        assert rootObject == JsonToken.START_OBJECT;
        final String actualFieldName = jsonParser.nextFieldName();
        if (!actualFieldName.equals(fieldName)) {
            throw new IllegalArgumentException(
                    "wrong field name read: " + actualFieldName + "; expected: " + fieldName);
        }
        final JsonToken startArray = jsonParser.nextToken();
        assert startArray == JsonToken.START_ARRAY;
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            final T elementValue = jsonParser.readValueAs(elementClass);
            elementConsumer.accept(elementValue);
        }
        final JsonToken endObject = jsonParser.nextToken();
        assert endObject == JsonToken.END_OBJECT;
        final JsonToken nullToken = jsonParser.nextToken();
        assert nullToken == null;
    }

    private JsonParserUtils() {
        super();
    }
}
