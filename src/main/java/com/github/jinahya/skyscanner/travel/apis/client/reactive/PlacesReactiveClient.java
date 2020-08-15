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
import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.autosuggest.v1_0.Place;
import net.skyscanner.api.partners.apiservices.autosuggest.v1_0.PlaceRequest;
import net.skyscanner.api.partners.apiservices.autosuggest.v1_0.Places;
import net.skyscanner.api.partners.apiservices.autosuggest.v1_0.PlacesRequest;
import net.skyscanner.api.partners.apiservices.geo.v1_0.Continent;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

import static com.github.jinahya.skyscanner.travel.apis.client.utils.JsonParserUtils.parseWrappedArrayInDocument;
import static com.github.jinahya.skyscanner.travel.apis.client.utils.ResponseSpecUtils.pipeBodyAndAccept;
import static java.nio.channels.Channels.newInputStream;

/**
 * A client related to <a href="https://skyscanner.github.io/slate/#places">Places</a>.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Validated
@Component
@Slf4j
public class PlacesReactiveClient extends SkyscannerTravelApisReactiveClient {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Retrieve places.
     *
     * @param sink a flux sink to which parsed elements are pushed.
     * @return a mono to block.
     * @see <a href="https://skyscanner.github.io/slate/#list-of-places">List of places</a>
     */
    @NonNull
    public Mono<Void> retrievePlaces(@Valid @NotNull PlacesRequest request,
                                     @NotNull final FluxSink<? super Place> sink) {
        final WebClient.ResponseSpec response = webClient()
                .get()
                .uri(b -> b.pathSegment("autosuggest", "v1.0", "{country}", "{currency}", "{locale}")
                        .queryParam("query", request.getQuery())
                        .build(request.getCountry(), request.getCurrency(), request.getLocale()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
        return pipeBodyAndAccept(
                response,
                c -> {
                    try {
                        try (JsonParser parser = objectMapper().createParser(newInputStream(c))) {
                            parseWrappedArrayInDocument(parser, Place.class, sink::next);
                            sink.complete();
                        }
                    } catch (final IOException ioe) {
                        sink.error(ioe);
                    }
                }
        );
    }

    /**
     * Retrieves a place.
     *
     * @param request a request object.
     * @return a mono of place.
     */
    @NonNull
    public Mono<Place> retrievePlace(@Valid @NotNull PlaceRequest request) {
        return webClient()
                .get()
                .uri(b -> b.pathSegment("autosuggest", "v1.0", "{country}", "{currency}", "{locale}")
                        .queryParam("id", request.getId())
                        .build(request.getCountry(), request.getCurrency(), request.getLocale()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Places.class)
                .handle((b, s) -> {
                    final List<Place> places = b.getPlaces();
                    if (places == null || places.isEmpty()) {
                        s.error(new WebClientException("no place retrieved") {
                        });
                        return;
                    }
                    assert places.size() == 1;
                    s.next(places.get(0));
                    s.complete();
                })
                ;
    }

    /**
     * Retrieves geo catalog.
     * <blockquote>Get the full list of all the places that we support.</blockquote>
     *
     * @param languageId A locale in ISO format, for example {@code fr-FR}.
     * @param sink       a flux sink to which parsed elements are produced.
     * @return a mono to block.
     * @see <a href="https://skyscanner.github.io/slate/index.html#geo-catalog">Geo Catalog</a>
     */
    @NonNull
    public Mono<Void> retrieveGeoCatalog(@Nullable final String languageId,
                                         @NotNull final FluxSink<? super Continent> sink) {
        final WebClient.ResponseSpec response = webClient()
                .get()
                .uri(b -> {
                    b = b.pathSegment("geo", "v1.0");
                    if (languageId != null) {
                        b = b.queryParam("languageid", languageId);
                    }
                    return b.build();
                })
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
        return pipeBodyAndAccept(
                response,
                c -> {
                    try {
                        try (JsonParser parser = objectMapper().createParser(newInputStream(c))) {
                            parseWrappedArrayInDocument(parser, Continent.class, sink::next);
                            sink.complete();
                        }
                    } catch (final IOException ioe) {
                        sink.error(ioe);
                    }
                }
        );
    }
}
