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

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.springframework.util.ReflectionUtils.doWithFields;

public final class QueryParamEntries {

    public static <T> void toFormData(final Class<T> clazz, final T value, final MultiValueMap<String, String> map) {
        requireNonNull(clazz, "clazz is null");
        requireNonNull(value, "value is null");
        requireNonNull(map, "map is null");
        doWithFields(
                clazz,
                f -> {
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }
                    ofNullable(f.get(value))
                            .map(Object::toString)
                            .ifPresent(v -> map.add(f.getName(), v));
                },
                f -> f.isAnnotationPresent(FormDataEntry.class)
        );
    }

    private static <T> void toFormDataHelper(final Class<T> clazz, final Object value,
                                             final MultiValueMap<String, String> map) {
        requireNonNull(clazz, "clazz is null");
        toFormData(clazz, clazz.cast(value), map);
    }

    public static void toFormData(final Object value, final MultiValueMap<String, String> map) {
        requireNonNull(value, "value is null");
        toFormDataHelper(value.getClass(), value, map);
    }

    public static <T extends AbstractRequest> MultiValueMap<String, String> queryParams(
            final Class<T> clazz, final T request) {
        requireNonNull(clazz, "clazz is null");
        requireNonNull(request, "request is null");
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        doWithFields(
                clazz,
                f -> {
                    final QueryParamEntry a = f.getAnnotation(QueryParamEntry.class);
                    final String n = of(a.name()).map(String::trim).filter(v -> !"".equals(v)).orElse(f.getName());
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }
                    ofNullable(f.get(request))
                            .filter(Objects::nonNull)
                            .map(Object::toString)
                            .ifPresent(v -> queryParams.add(n, v));
                },
                f -> f.isAnnotationPresent(QueryParamEntry.class)
        );
        return queryParams;
    }

    private static <T extends AbstractRequest> MultiValueMap<String, String> queryParamsHelper(
            final Class<T> clazz, final Object request) {
        requireNonNull(clazz, "clazz is null");
        requireNonNull(request, "request is null");
        return queryParams(clazz, clazz.cast(request));
    }

    public static <T extends AbstractRequest> MultiValueMap<String, String> queryParams(final T request) {
        requireNonNull(request, "request is null");
        return queryParamsHelper(request.getClass(), request);
    }

    private QueryParamEntries() {
        super();
    }
}
