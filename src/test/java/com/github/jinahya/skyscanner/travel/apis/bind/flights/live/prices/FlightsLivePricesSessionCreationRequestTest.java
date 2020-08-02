package com.github.jinahya.skyscanner.travel.apis.bind.flights.live.prices;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.pricing.v1_0.FlightsLivePricesSessionCreationRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Locale;

@Slf4j
class FlightsLivePricesSessionCreationRequestTest {

    @Test
    void testExample() throws JsonProcessingException {
        final FlightsLivePricesSessionCreationRequest request = FlightsLivePricesSessionCreationRequest.builder()
                .country(Locale.UK.getCountry())
                .currency(Currency.getInstance(Locale.UK).getCurrencyCode())
                .locale(Locale.UK.toString())
                .originPlace("EDI")
                .destinationPlace("LHR")
                .outboundDate(LocalDate.of(2017, 5, 30))
                .inboundDate(LocalDate.of(2017, 6, 2))
                .adults(1)
                .children(0)
                .infants(0)
                .apiKey("prtl6749387986743898559646983194")
                .build();
        final String json = new ObjectMapper().writeValueAsString(request);
        log.debug("json: {}", json);
    }
}
