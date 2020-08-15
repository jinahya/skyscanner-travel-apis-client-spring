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
import net.skyscanner.api.partners.apiservices.reference.v1_0.Countries;
import net.skyscanner.api.partners.apiservices.reference.v1_0.Country;
import net.skyscanner.api.partners.apiservices.reference.v1_0.Currencies;
import net.skyscanner.api.partners.apiservices.reference.v1_0.Currency;
import net.skyscanner.api.partners.apiservices.reference.v1_0.Locale;
import net.skyscanner.api.partners.apiservices.reference.v1_0.Locales;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * A client related to <a href="https://skyscanner.github.io/slate/#localisation">Localisation</a>.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Validated
@Component
@Slf4j
public class LocalisationClient extends SkyscannerTravelApisClient {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Retrieves locales.
     *
     * @return a list of locales.
     * @see <a href="https://skyscanner.github.io/slate/#locales">Locales</a>
     */
    @NonNull
    public List<Locale> retrieveLocales() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(APPLICATION_JSON));
        final HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        final ResponseEntity<Locales> responseEntity
                = restTemplate().exchange("/reference/v1.0/locales", GET, requestEntity, Locales.class);
        return responseEntity.getBody().getLocales();
    }

    /**
     * Retrieves currencies.
     *
     * @return a list of currencies.
     * @see <a href="https://skyscanner.github.io/slate/#currencies">Currencies</a>
     */
    @NonNull
    public List<Currency> retrieveCurrencies() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(APPLICATION_JSON));
        final HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        final ResponseEntity<Currencies> responseEntity
                = restTemplate().exchange("/reference/v1.0/currencies", GET, requestEntity, Currencies.class);
        return responseEntity.getBody().getCurrencies();
    }

    /**
     * Retrieves market countries.
     *
     * @param locale a locale of preferred language of the result.
     * @return a list of countries.
     * @see <a href="https://skyscanner.github.io/slate/#markets">Markets</a>
     */
    @NonNull
    public List<Country> retrieveMarkets(@NotBlank final String locale) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(APPLICATION_JSON));
        final HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        final ResponseEntity<Countries> responseEntity
                = restTemplate().exchange("/reference/v1.0/countries/" + locale, GET, requestEntity, Countries.class);
        return responseEntity.getBody().getCountries();
    }
}
