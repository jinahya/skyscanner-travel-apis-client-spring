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

import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.autosuggest.v1_0.Place;
import net.skyscanner.api.partners.apiservices.autosuggest.v1_0.PlaceRequest;
import net.skyscanner.api.partners.apiservices.autosuggest.v1_0.PlacesRequest;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.FluxSink;

@Slf4j
class PlacesReactiveClientIT extends SkyscannerTravelApisReactiveClientIT<PlacesReactiveClient> {

    PlacesReactiveClientIT() {
        super(PlacesReactiveClient.class);
    }

    /**
     * Tests {@link PlacesReactiveClient#retrievePlaces(PlacesRequest, FluxSink)} method.
     */
    @Test
    void testRetrievePlaces() {
        final DirectProcessor<Place> placesProcessor = DirectProcessor.create();
        final PlacesRequest placesRequest = PlacesRequest.builder()
                .country("KR")
                .currency("KRW")
                .locale("ko-KR")
                .query("ICN")
                .build();
        placesProcessor.subscribe(p -> {
            log.debug("place: {}", p);
            final Place place = clientInstance()
                    .retrievePlace(
                            PlaceRequest.builder()
                                    .country(placesRequest.getCountry())
                                    .currency(placesRequest.getCurrency())
                                    .locale(placesRequest.getLocale())
                                    .id(p.getPlaceId())
                                    .build()
                    )
                    .block();
            log.debug("place: {}", place);
        });
        clientInstance()
                .retrievePlaces(placesRequest, placesProcessor.sink())
                .block();
    }
}
