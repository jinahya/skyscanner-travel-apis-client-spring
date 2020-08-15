package net.skyscanner.api.partners.apiservices.pricing.v1_0;

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

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.AbstractResponse;
import net.skyscanner.api.partners.apiservices.reference.v1_0.Currency;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@ToString
@Setter(AccessLevel.PROTECTED)
@Getter
@Slf4j
public class FlightsLivePricesResultPollingResponse extends AbstractResponse {

    public enum Status {
        UpdatesPending, UpdatesComplete
    }

    public static FlightsLivePricesResultPollingResponse ofStatus(final Status status) {
        final FlightsLivePricesResultPollingResponse instance = new FlightsLivePricesResultPollingResponse();
        instance.status = status;
        return instance;
    }

    // ------------------------------------------------------------------------------------------------------ sessionKey
    // ----------------------------------------------------------------------------------------------------------- query
    // --------------------------------------------------------------------------------------------------------- status
    // ----------------------------------------------------------------------------------------------------- itineraries
    // ------------------------------------------------------------------------------------------------------------ legs
    // -------------------------------------------------------------------------------------------------------- segments

    // -----------------------------------------------------------------------------------------------------------------
    private String sessionKey;

    private FlightsLivePricesSessionCreationRequest query;

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    private Status status;

    // -----------------------------------------------------------------------------------------------------------------
    private List<@Valid @NotNull Itinerary> itineraries;

    private List<@Valid @NotNull Leg> legs;

    private List<@Valid @NotNull Segment> segments;

    private List<@Valid @NotNull Carrier> carriers;

    private List<@Valid @NotNull Agent> agents;

    private List<@Valid @NotNull Place> places;

    private List<@Valid @NotNull Currency> currencies;
}
