package com.github.jinahya.randomorg.api.bind.r2.basic;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static com.github.jinahya.randomorg.api.bind.BeanValidationTests.requireValid;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
class GenerateIntegersParamsTest {

    @Test
    void read_generateIntegers_params_01() throws IOException {
        try (InputStream resource = getClass().getResourceAsStream("generateIntegers_params_01.json")) {
            final GenerateIntegersParams params = new ObjectMapper().readValue(resource, GenerateIntegersParams.class);
            log.debug("params: {}", params);
            requireValid(params);
            assertEquals("6b1e65b9-4186-45c2-8981-b77a9842c4f0", params.getApiKey());
            assertEquals(6, params.getN());
            assertEquals(1, params.getMin());
            assertEquals(6, params.getMax());
            assertEquals(true, params.getReplacement());
            assertNull(params.getBase());
        }
    }
}