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

import com.github.jinahya.randomorg.api.bind.r2.AbstractParams;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

@Setter
@Getter
public class GenerateIntegersParams extends AbstractParams {

    public static final int MIN_N = 1;

    public static final int MAX_N = (int) 1.0e4;

    public static final int MIN_MIN = (int) -1.0e+9;

    public static final int MAX_MIN = (int) +1.0e+9;

    public static final int MIN_MAX = MIN_MIN;

    public static final int MAX_MAX = MAX_MIN;

    // -----------------------------------------------------------------------------------------------------------------
    @Max(MAX_N)
    @Min(MIN_N)
    private int n = MIN_N;

    @Delegate
    @Valid
    @NotNull
    private final GenerateIntegersParamsUnit unit = new GenerateIntegersParamsUnit();
}
