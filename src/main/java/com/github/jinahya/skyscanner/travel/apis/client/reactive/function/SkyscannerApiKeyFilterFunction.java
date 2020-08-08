package com.github.jinahya.skyscanner.travel.apis.client.reactive.function;

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
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;

/**
 * An exchange filter function for attaching {@link #QUERY_PARAM_NAME_API_KEY} attribute.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Component
public class SkyscannerApiKeyFilterFunction implements ExchangeFilterFunction {

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
    @Override
    public Mono<ClientResponse> filter(final ClientRequest clientRequest, final ExchangeFunction exchangeFunction) {
        final URI url = UriComponentsBuilder.fromUri(clientRequest.url())
                .queryParam(QUERY_PARAM_NAME_API_KEY, skyscannerTravelApisApiKey)
                .build()
                .toUri();
        final ClientRequest newClientRequest
                = ClientRequest.from(clientRequest)
                .url(url)
                .build();
        return exchangeFunction.exchange(newClientRequest);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotBlank
    @Autowired
    @SkyscannerTravelApisApiKey
    private String skyscannerTravelApisApiKey;
}
