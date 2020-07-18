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

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import static com.github.jinahya.randomorg.api.bind.r2.basic.GenerateIntegersParams.MAX_MAX;
import static com.github.jinahya.randomorg.api.bind.r2.basic.GenerateIntegersParams.MAX_MIN;
import static com.github.jinahya.randomorg.api.bind.r2.basic.GenerateIntegersParams.MIN_MAX;
import static com.github.jinahya.randomorg.api.bind.r2.basic.GenerateIntegersParams.MIN_MIN;

@Setter
@Getter
public class GenerateIntegersParamsUnit {

    // -----------------------------------------------------------------------------------------------------------------
    private static final int MIN_BASE = 10;

    private static final int MAX_BASE = MIN_BASE;

    // -----------------------------------------------------------------------------------------------------------------
    @AssertTrue
    private boolean isMinLessThanOrEqualsToMax() {
        return min <= max;
    }

    // ------------------------------------------------------------------------------------------------------------- min
    // ------------------------------------------------------------------------------------------------------------- max
    // ----------------------------------------------------------------------------------------------------- replacement
    // ------------------------------------------------------------------------------------------------------------ base
    public void setBase(final Integer base) {
        if (base != null && (base < MIN_BASE || base > MAX_BASE)) {
            throw new IllegalArgumentException("illegal base: " + base);
        }
        this.base = base;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Max(MAX_MIN)
    @Min(MIN_MIN)
    private int min = MIN_MIN;

    @Max(MAX_MAX)
    @Min(MIN_MAX)
    private int max = MAX_MAX;

    private Boolean replacement;

    @Max(MAX_BASE)
    @Min(MIN_BASE)
    private Integer base;
}
