package com.github.jinahya.skyscanner.travel.apis.client.reactive;

import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.pricing.v1_0.FlightsLivePricesResultPollingRequest;
import net.skyscanner.api.partners.apiservices.pricing.v1_0.FlightsLivePricesSessionCreationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.github.jinahya.skyscanner.travel.apis.client.Application.SYSTEM_PROPERTY_NAME_API_KEY;

@EnabledIf("#{systemProperties['" + SYSTEM_PROPERTY_NAME_API_KEY + "'] != null}")
@SpringBootTest
@Slf4j
class FlightsLivePricesClientIT extends SkyscannerTravelApisReactiveClientIT<FlightsLivePricesClient> {

    FlightsLivePricesClientIT() {
        super(FlightsLivePricesClient.class);
    }

    @Test
    void testFlightsLivePrices() {
        final LocalDate today = LocalDate.now();
        final FlightsLivePricesSessionCreationRequest sessionCreationRequest
                = FlightsLivePricesSessionCreationRequest.builder()
                .country("KR")
                .currency("KRW")
                .locale("ko-KR")
                .originPlace("ICN-sky")
                .destinationPlace("LOND-sky")
                .outboundDate(today.plus(10, ChronoUnit.DAYS))
                .inboundDate(today.plus(12, ChronoUnit.DAYS))
                .adults(1)
                .build();
        final FlightsLivePricesResultPollingRequest resultPollingRequest
                = FlightsLivePricesResultPollingRequest.builder()
                .build();
        final String location = applyClientInstance(c -> c.createSession(sessionCreationRequest)).block();
        acceptClientInstance(c -> {
            c.pollResult(location, resultPollingRequest)
                    .doOnNext(r -> {
                        log.debug("flights live prices response: {}", r.hashCode());
                    })
                    .blockLast();
            ;
        });
    }
}