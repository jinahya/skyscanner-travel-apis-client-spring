package net.skyscanner.api.partners.apiservices;

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

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.beans.Transient;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

@ToString
@Slf4j
public abstract class AbstractObject implements Serializable {

    /**
     * Sets an entry of unrecognized property.
     *
     * @param key   the key of the unrecognized property.
     * @param value the value of the unrecognized property.
     * @return previous value mapped to the {@code key}.
     */
    @JsonAnySetter
    private Object putUnrecognizedProperty(final String key, final Object value) {
        requireNonNull(key, "key is null");
        return getUnrecognizedProperties().put(key, value);
    }

    /**
     * Returns unrecognized properties of this object.
     *
     * @return unrecognized properties of this object.
     */
    @JsonAnyGetter
    @Transient
    public Map<String, Object> getUnrecognizedProperties() {
        if (unrecognizedProperties == null) {
            unrecognizedProperties = new HashMap<>();
        }
        return unrecognizedProperties;
    }

    private Map<String, Object> unrecognizedProperties;
}
