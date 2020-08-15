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

import com.github.jinahya.skyscanner.travel.apis.client.http.SkyscannerApiKeyRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.net.URI;

import static com.github.jinahya.skyscanner.travel.apis.client.http.SkyscannerApiKeyRequestInterceptor.appendApiKey;

/**
 * An exchange filter function for attaching {@link SkyscannerApiKeyRequestInterceptor#QUERY_PARAM_NAME_API_KEY}
 * attribute.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Component
public class SkyscannerApiKeyFilterFunction implements ExchangeFilterFunction {

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public Mono<ClientResponse> filter(ClientRequest clientRequest, final ExchangeFunction exchangeFunction) {
        final URI url = appendApiKey(clientRequest.url(), skyscannerTravelApisApiKey).build();
        clientRequest = ClientRequest.from(clientRequest).url(url).build();
        return exchangeFunction.exchange(clientRequest);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Autowired
    @SkyscannerApiKeyRequestInterceptor.SkyscannerTravelApisApiKey
    private String skyscannerTravelApisApiKey;
}
