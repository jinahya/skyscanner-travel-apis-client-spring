package com.github.jinahya.skyscanner.travel.apis.client.reactive;

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

import com.fasterxml.jackson.core.JsonParser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.reference.v1_0.Country;
import net.skyscanner.api.partners.apiservices.reference.v1_0.Currency;
import net.skyscanner.api.partners.apiservices.reference.v1_0.Locale;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static com.github.jinahya.skyscanner.travel.apis.client.utils.JsonParserUtils.parseWrappedArrayInDocument;
import static com.github.jinahya.skyscanner.travel.apis.client.utils.ResponseSpecUtils.pipeBodyAndAccept;
import static java.nio.channels.Channels.newInputStream;

@Validated
@Component
@Slf4j
public class LocalisationReactiveClient extends SkyscannerTravelApisReactiveClient {

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    public Mono<Void> locales(@NotNull final FluxSink<? super Locale> sink) {
        final WebClient.ResponseSpec response = webClient().get()
                .uri(b -> b.pathSegment("reference", "v1.0", "locales").build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
        return pipeBodyAndAccept(
                response,
                c -> {
                    try {
                        try (JsonParser parser = objectMapper().createParser(newInputStream(c))) {
                            //parseFirstArrayInDocument(parser, "Locales", Locale.class, sink::next);
                            parseWrappedArrayInDocument(parser, Locale.class, sink::next);
                            sink.complete();
                        }
                    } catch (final IOException ioe) {
                        sink.error(ioe);
                    }
                }
        );
    }

    @NotNull
    public Mono<Void> currencies(@NotNull final FluxSink<? super Currency> sink) {
        final WebClient.ResponseSpec response = webClient().get()
                .uri(b -> b.pathSegment("reference", "v1.0", "currencies").build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
        return pipeBodyAndAccept(
                response,
                c -> {
                    try {
                        try (JsonParser parser = objectMapper().createParser(newInputStream(c))) {
//                            parseFirstArrayInDocument(parser, "Currencies", Currency.class, sink::next);
                            parseWrappedArrayInDocument(parser, Currency.class, sink::next);
                            sink.complete();
                        }
                    } catch (final IOException ioe) {
                        sink.error(ioe);
                    }
                }
        );
    }

    @NotNull
    public Mono<Void> markets(@NotBlank final String locale, @NotNull final FluxSink<? super Country> sink) {
        final WebClient.ResponseSpec response = webClient().get()
                .uri(b -> b.pathSegment("reference", "v1.0", "countries", locale).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
        return pipeBodyAndAccept(
                response,
                c -> {
                    try {
                        try (JsonParser parser = objectMapper().createParser(newInputStream(c))) {
//                            parseFirstArrayInDocument(parser, "Countries", Country.class, sink::next);
                            parseWrappedArrayInDocument(parser, Country.class, sink::next);
                            sink.complete();
                        }
                    } catch (final IOException ioe) {
                        sink.error(ioe);
                    }
                }
        );
    }
}
