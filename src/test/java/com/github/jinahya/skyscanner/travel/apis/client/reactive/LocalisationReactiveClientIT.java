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
import net.skyscanner.api.partners.apiservices.reference.v1_0.Country;
import net.skyscanner.api.partners.apiservices.reference.v1_0.Currency;
import net.skyscanner.api.partners.apiservices.reference.v1_0.Locale;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.FluxSink;

@Slf4j
class LocalisationReactiveClientIT extends SkyscannerTravelApisReactiveClientIT<LocalisationReactiveClient> {

    LocalisationReactiveClientIT() {
        super(LocalisationReactiveClient.class);
    }

    @Test
    void testLocales() {
        final DirectProcessor<Locale> processor = DirectProcessor.create();
        processor.subscribe(l -> {
            log.debug("locale: {}", l);
        });
        final FluxSink<Locale> sink = processor.sink();
        clientInstance()
                .locales(sink)
                .block();
    }

    @Test
    void testCurrencies() {
        final DirectProcessor<Currency> processor = DirectProcessor.create();
        processor.subscribe(c -> {
            log.debug("currency: {}", c);
        });
        final FluxSink<Currency> sink = processor.sink();
        clientInstance()
                .currencies(sink)
                .block();
    }

    @Test
    void testMarkets() {
        final DirectProcessor<Country> processor = DirectProcessor.create();
        processor.subscribe(c -> {
            log.debug("country: {}", c);
        });
        final FluxSink<Country> sink = processor.sink();
        clientInstance()
                .markets("ko-KR", sink)
                .block();
    }
}
