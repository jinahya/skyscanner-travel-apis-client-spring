package com.github.jinahya.randomorg.api.bind.r2.basic;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.github.jinahya.randomorg.api.bind.BeanValidationTests.requireValid;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
class GenerateIntegerSequencesParamsTest {

    @Test
    void read_generateIntegers_params_01() throws IOException {
        try (InputStream resource = getClass().getResourceAsStream("generateIntegerSequences_params_01.json")) {
            final GenerateIntegerSequencesParams params
                    = new ObjectMapper().readValue(resource, GenerateIntegerSequencesParams.class);
            log.debug("params: {}", params);
            requireValid(params);
            assertEquals("6b1e65b9-4186-45c2-8981-b77a9842c4f0", params.getApiKey());
            assertEquals(2, params.getN());
            assertIterableEquals(asList(5, 1), (List<?>) params.getLength());
            assertIterableEquals(asList(1, 1), (List<?>) params.getMin());
            assertIterableEquals(asList(69, 26), (List<?>) params.getMax());
            assertIterableEquals(asList(false, false), (List<?>) params.getReplacement());
            assertIterableEquals(asList(10, 10), (List<?>) params.getBase());
        }
    }
}