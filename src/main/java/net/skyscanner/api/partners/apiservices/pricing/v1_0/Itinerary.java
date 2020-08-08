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
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.AbstractResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@Data
@Slf4j
public class Itinerary extends AbstractResponse {

    // -----------------------------------------------------------------------------------------------------------------
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    @Data
    @Slf4j
    public static class PricingOption extends AbstractResponse {

        private List<@NotNull Long> agents;

        private Integer quoteAgeInMinutes;

        private BigDecimal price;

        private String deepLinkUrl;
    }

    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    @Data
    @Slf4j
    public static class BookingDetailsLink extends AbstractResponse {

        private String uri;

        private String body;

        private String method;
    }

    // -----------------------------------------------------------------------------------------------------------------
    private String outboundLegId;

    private String inboundLegId;

    private List<@Valid @NotNull PricingOption> pricingOptions;

    private BookingDetailsLink bookingDetailsLink;
}
