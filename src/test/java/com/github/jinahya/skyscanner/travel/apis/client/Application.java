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

import com.github.jinahya.skyscanner.travel.apis.client.SkyscannerTravelApisClient.SkyscannerTravelApisRestTemplate;
import com.github.jinahya.skyscanner.travel.apis.client.http.SkyscannerApiKeyRequestInterceptor;
import com.github.jinahya.skyscanner.travel.apis.client.http.SkyscannerApiKeyRequestInterceptor.SkyscannerTravelApisApiKey;
import com.github.jinahya.skyscanner.travel.apis.client.reactive.SkyscannerTravelApisReactiveClient.SkyscannerTravelApisWebClient;
import com.github.jinahya.skyscanner.travel.apis.client.reactive.function.SkyscannerApiKeyFilterFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@Slf4j
public class Application {

    public static final String SYSTEM_PROPERTY_NAME_API_KEY = "apiKey";

//    @SkyscannerTravelApisRootUri
//    @Bean
//    URI skyscannerTravelApisRootUri() {
//        return URI.create(skyscannerTravelApisRootUri);
//    }

    @SkyscannerTravelApisApiKey
    @Bean
    String skyscannerTravelApisApiKey() {
        return skyscannerTravelApisApiKey;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @SkyscannerTravelApisRestTemplate
    @Bean
    RestTemplate skyscannerTravelApisRestTemplate(final SkyscannerApiKeyRequestInterceptor requestInterceptor) {
        return new RestTemplateBuilder()
                .rootUri(skyscannerTravelApisRootUri)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .interceptors(requestInterceptor)
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------
    @SkyscannerTravelApisWebClient
    @Bean
    WebClient skyscannerTravelApisWebClient(final SkyscannerApiKeyFilterFunction filterFunction) {
        return WebClient.builder()
                .baseUrl(skyscannerTravelApisRootUri)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .filter(filterFunction)
                .codecs(c -> c.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build()
                ;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Value("${skyscanner-travel-apis.root-uri}")
    private String skyscannerTravelApisRootUri;

    @Value("#{systemProperties['" + SYSTEM_PROPERTY_NAME_API_KEY + "']}")
    private String skyscannerTravelApisApiKey;
}
