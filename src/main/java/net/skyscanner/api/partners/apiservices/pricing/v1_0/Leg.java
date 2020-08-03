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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@Setter
@Getter
@ToString
@Slf4j
public class Leg {

    private String id;

    private List<@NotNull Long> segmentIds;

    private Long originStation;

    private Long destinationStation;

    private LocalDateTime departure;

    private LocalDateTime arrival;

    private Duration duration;

    private String journeyMode;

    private List<@NotNull Long> stops;

    private List<@NotNull Long> carriers;

    private List<@NotNull Long> operatingCarriers;

    private String directionality;

    private List<@Valid @NotNull FlightNumber> flightNumbers;
}
