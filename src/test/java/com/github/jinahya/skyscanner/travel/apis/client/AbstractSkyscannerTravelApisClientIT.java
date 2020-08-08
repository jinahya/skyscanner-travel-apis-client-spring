package com.github.jinahya.skyscanner.travel.apis.client;

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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import static com.github.jinahya.skyscanner.travel.apis.client.Application.SYSTEM_PROPERTY_NAME_API_KEY;
import static java.util.Objects.requireNonNull;

@EnabledIf("#{systemProperties['" + SYSTEM_PROPERTY_NAME_API_KEY + "'] != null}")
@SpringBootTest
@Accessors(fluent = true)
@Slf4j
public abstract class AbstractSkyscannerTravelApisClientIT<T extends AbstractSkyscannerTravelApisClient> {

    protected AbstractSkyscannerTravelApisClientIT(final Class<T> clientClass) {
        super();
        this.clientClass = requireNonNull(clientClass, "clientClass is null");
    }

    protected Class<T> clientClass;

    @Autowired
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.PROTECTED)
    private T clientInstance;
}
