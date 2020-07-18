package com.github.jinahya.randomorg.api.bind.r2.signed;

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

import com.github.jinahya.randomorg.api.bind.r2.AbstractGenerateIntegersResult;
import com.github.jinahya.randomorg.api.bind.r2.AbstractGenerateIntegersResultRandom;
import lombok.Getter;
import lombok.Setter;

public class GenerateSignedIntegersResult extends AbstractGenerateIntegersResult<GenerateSignedIntegersResult.Random> {

    @Setter
    @Getter
    public static class Random extends AbstractGenerateIntegersResultRandom {

        private String method;

        private String hashedApiKey;

        private Object license;

        private String userData;

        private int serialNumber;
    }
}
