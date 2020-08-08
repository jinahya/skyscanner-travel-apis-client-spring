package com.github.jinahya.skyscanner.travel.apis.client.reactive;

import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.autosuggest.v1_0.Place;
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
        final DirectProcessor<Place> processor = DirectProcessor.create();
        processor.subscribe(p -> {
            log.debug("place: {}", p);
        });
        clientInstance()
                .retrievePlaces(
                        PlacesRequest.builder()
                                .country("KR")
                                .currency("KRW")
                                .locale("ko-KR")
                                .query("ICN")
                                .build(),
                        processor.sink()
                )
                .block();
    }
}