package com.github.jinahya.skyscanner.travel.apis.client.utils;

/*-
 * #%L
 * skyscanner-travel-apis-client-spring
 * %%
 * Copyright (C) 2020 Jinahya, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

@Slf4j
public final class JsonParserUtils {

    /**
     * Parses array elements at the first field in an object node and accepts to specified consumer.
     * <p>
     * Some of Skyscanner Travel APIs respond JSON documents look like this.
     * <blockquote><pre>
     *     {
     *         "Some-Unnecessary-Field": [
     *             {
     *             },
     *             {
     *             },
     *             ...
     *         ]
     *     }
     * </pre></blockquote>
     * This method locates the first field(e.g. {@code "Some-Unnecessary-Field"}) and parses array elements and accepts
     * each elements to specified consumer.
     *
     * @param jsonParser      a json parser whose next token should be {@link JsonToken#START_OBJECT}.
     * @param elementClass    a class of array elements.
     * @param elementConsumer the consumer to be accepted with every parsed array elements.
     * @param <T>             element type parameter
     * @throws IOException if an I/O error occurs.
     */
    public static <T> void parseArray(final JsonParser jsonParser, final Class<T> elementClass,
                                      final Consumer<? super T> elementConsumer)
            throws IOException {
        requireNonNull(jsonParser, "jsonParser is null");
        requireNonNull(elementClass, "elementClass is null");
        requireNonNull(elementConsumer, "elementConsumer is null");
        final JsonToken currentToken = jsonParser.currentToken();
        if (currentToken != JsonToken.START_ARRAY) {
            throw new IllegalArgumentException(
                    "jsonParser.currentToken is not " + JsonToken.START_ARRAY + " but " + currentToken);
        }
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            final T elementValue = jsonParser.readValueAs(elementClass);
            elementConsumer.accept(elementValue);
        }
    }

    /**
     * Parses wrapped array elements in a document and accepts each element to specified consumer.
     * <p>
     * Some Skyscanner Travel APIs respond JSON documents look like this.
     * <blockquote><pre>
     * {
     *   "Some-Unnecessary-Field": [
     *     {
     *     },
     *     {
     *     },
     *     ...
     *   ]
     * }
     * </pre></blockquote>
     * This method locates the root array(e.g. {@code "Some-Unnecessary-Field"}) and accepts each parsed element to
     * specified consumer.
     *
     * @param jsonParser      a json parser.
     * @param elementClass    a class of array elements.
     * @param elementConsumer the consumer to be accepted with each parsed array element.
     * @param <T>             element type parameter
     * @throws IOException if an I/O error occurs.
     */
    public static <T> void parseWrappedArrayInDocument(final JsonParser jsonParser, final Class<T> elementClass,
                                                       final Consumer<? super T> elementConsumer)
            throws IOException {
        requireNonNull(jsonParser, "jsonParser is null");
        requireNonNull(elementClass, "elementClass is null");
        requireNonNull(elementConsumer, "elementConsumer is null");
        // locate the array
        while (jsonParser.currentToken() != JsonToken.START_ARRAY) {
            final JsonToken token = jsonParser.nextToken();
            if (token == null || token == JsonToken.NOT_AVAILABLE) {
                throw new JsonParseException(jsonParser, "failed to locate " + JsonToken.START_ARRAY);
            }
        }
        // parse array elements
        parseArray(jsonParser, elementClass, elementConsumer);
        // discard all tokens to the end
        for (JsonToken token; (token = jsonParser.nextToken()) != null; ) {
        }
//        while (jsonParser.currentToken() != null && jsonParser.currentToken() != JsonToken.NOT_AVAILABLE) {
//            final JsonToken token = jsonParser.nextToken();
//        }
    }

    private JsonParserUtils() {
        super();
    }
}
