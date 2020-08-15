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

import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.reference.v1_0.Country;
import net.skyscanner.api.partners.apiservices.reference.v1_0.Currency;
import net.skyscanner.api.partners.apiservices.reference.v1_0.Locale;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Validator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class LocalisationClientIT extends SkyscannerTravelApisClientIT<LocalisationClient> {

    LocalisationClientIT() {
        super(LocalisationClient.class);
    }

    @Test
    void testRetrieveLocales() {
        final List<Locale> locales = clientInstance().retrieveLocales();
        assertThat(locales)
                .isNotNull()
                .isNotEmpty()
                .allSatisfy(l -> {
                    log.debug("locale: {}", l);
                    assertThat(l).isNotNull();
                    assertThat(validator.validate(l)).isEmpty();
                });
    }

    @Test
    void testRetrieveCurrencies() {
        final List<Currency> currencies = clientInstance().retrieveCurrencies();
        assertThat(currencies)
                .isNotNull()
                .isNotEmpty()
                .allSatisfy(c -> {
                    log.debug("currency: {}", c);
                    assertThat(c).isNotNull();
                    assertThat(validator.validate(c)).isEmpty();
                });
    }

    @Test
    void testRetrieveCountries() {
        final List<Country> countries = clientInstance().retrieveMarkets("ko-KR");
        assertThat(countries)
                .isNotNull()
                .isNotEmpty()
                .allSatisfy(c -> {
                    log.debug("country: {}", c);
                    assertThat(c).isNotNull();
                    assertThat(validator.validate(c)).isEmpty();
                });
    }

    @Autowired
    private Validator validator;
}
