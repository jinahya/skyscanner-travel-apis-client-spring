package com.github.jinahya.skyscanner.travel.apis.client.http;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;

@Component
public class SkyscannerApiKeyRequestInterceptor implements ClientHttpRequestInterceptor {

    public static final String QUERY_PARAM_NAME_API_KEY = "apiKey";

    /**
     * A qualifier annotation for {@code apiKey} for Skyscanner-Travel-APIs.
     */
    @Qualifier
    @Documented
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SkyscannerTravelApisApiKey {

    }

    // -----------------------------------------------------------------------------------------------------------------
    public static UriBuilder appendApiKey(final URI uri, final String apiKey) {
        return UriComponentsBuilder.fromUri(uri)
                .queryParam(QUERY_PARAM_NAME_API_KEY, apiKey);
    }

    public static HttpRequest appendApiKey(final HttpRequest httpRequest, final String apiKey) {
        return new HttpRequestWrapper(httpRequest) {
            @Override
            public URI getURI() {
                return appendApiKey(super.getURI(), apiKey).build();
            }
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public ClientHttpResponse intercept(final HttpRequest request, final byte[] body,
                                        final ClientHttpRequestExecution execution)
            throws IOException {
        return execution.execute(appendApiKey(request, skyscannerTravelApisApiKey), body);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Autowired
    @SkyscannerTravelApisApiKey
    private String skyscannerTravelApisApiKey;
}
