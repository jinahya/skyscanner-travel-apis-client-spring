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

import com.github.jinahya.skyscanner.travel.apis.client.AbstractSkyscannerTravelApisClient.SkyscannerTravelApisRootUri;
import com.github.jinahya.skyscanner.travel.apis.client.reactive.SkyscannerTravelApisReactiveClient.SkyscannerTravelApisReactiveWebClient;
import com.github.jinahya.skyscanner.travel.apis.client.reactive.function.SkyscannerApiKeyFilterFunction;
import com.github.jinahya.skyscanner.travel.apis.client.reactive.function.SkyscannerApiKeyFilterFunction.SkyscannerTravelApisApiKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.MalformedURLException;
import java.net.URI;

@SpringBootApplication
@Slf4j
public class Application {

    public static final String SYSTEM_PROPERTY_NAME_API_KEY = "apiKey";

    @SkyscannerTravelApisRootUri
    @Bean
    URI skyscannerTravelApisRootUri() {
        return URI.create(skyscannerTravelApisRootUri);
    }

    @SkyscannerTravelApisApiKey
    @Bean
    String skyscannerTravelApisApiKey() {
        return skyscannerTravelApisApiKey;
    }

    @SkyscannerTravelApisReactiveWebClient
    @Bean
    WebClient skyscannerTravelApisReactiveWebClient(@SkyscannerTravelApisRootUri final URI rootUri,
                                                    final SkyscannerApiKeyFilterFunction filterFunction)
            throws MalformedURLException {
        return WebClient.builder()
                .baseUrl(rootUri.toURL().toExternalForm())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .filter(filterFunction)
                .codecs(c -> c.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build()
                ;
    }

    // -----------------------------------------------------------------------------------------------------------------
//    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Value("${skyscanner-travel-apis.root-uri}")
    private String skyscannerTravelApisRootUri;

    @Value("#{systemProperties['" + SYSTEM_PROPERTY_NAME_API_KEY + "']}")
    private String skyscannerTravelApisApiKey;
}
