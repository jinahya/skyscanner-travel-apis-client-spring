package com.github.jinahya.randomorg.api.bind.r2.basic;

/*-
 * #%L
 * jsonrpc-bind-jackson
 * %%
 * Copyright (C) 2019 - 2020 Jinahya, Inc.
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

import com.github.jinahya.randomorg.api.bind.r2.Base;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public class GenerateIntegerSequencesResult {

    public static class Random {

        // -----------------------------------------------------------------------------------------------------------------

        public List<List<Object>> getData() {
            return data;
        }

        public void setData(final List<List<Object>> data) {
            this.data = data;
        }

        public List<List<Integer>> getDataAsInteger(final IntFunction<Integer> radixFunction) {
            if (data == null) {
                return null;
            }
            return IntStream.range(0, data.size())
                    .mapToObj(i -> {
                        final List<Object> l = data.get(i);
                        final Base b = ofNullable(radixFunction.apply(i)).map(Base::valueOfRadix).orElse(null);
                        return l.stream().map(e -> b == null ? (Integer) e : b.parse((String) e)).collect(toList());
                    })
                    .collect(toList());
        }

        // -----------------------------------------------------------------------------------------------------------------
        public String getCompletionTime() {
            return completionTime;
        }

        public void setCompletionTime(String completionTime) {
            this.completionTime = completionTime;
        }

        public TemporalAccessor getCompletionTimeParsedWith(final DateTimeFormatter formatter) {
            requireNonNull(formatter, "formatter is null");
            if (completionTime == null) {
                return null;
            }
            return formatter.parse(completionTime);
        }

        public Instant getCompletionTimeAsInstant() {
            return Instant.from(getCompletionTimeParsedWith(DateTimeFormatter.ISO_INSTANT));
        }

        // -------------------------------------------------------------------------------------------------------------
        @NotEmpty
        private List<List<Object>> data;

        @NotBlank
        private String completionTime;
    }

    // -------------------------------------------------------------------------------------------------------- bitsUsed
    public int getBitsUsed() {
        return bitsUsed;
    }

    public void setBitsUsed(final int bitsUsed) {
        this.bitsUsed = bitsUsed;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public int getBitsLeft() {
        return bitsLeft;
    }

    public void setBitsLeft(int bitsLeft) {
        this.bitsLeft = bitsLeft;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public int getRequestsLeft() {
        return requestsLeft;
    }

    public void setRequestsLeft(final int requestsLeft) {
        this.requestsLeft = requestsLeft;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public long getAdvisoryDelay() {
        return advisoryDelay;
    }

    public void setAdvisoryDelay(final long advisoryDelay) {
        this.advisoryDelay = advisoryDelay;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Positive
    private int bitsUsed;

    @PositiveOrZero
    private int bitsLeft;

    @PositiveOrZero
    private int requestsLeft;

    @PositiveOrZero
    private long advisoryDelay;
}
