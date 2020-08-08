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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.AbstractRequest;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@Setter(AccessLevel.PROTECTED)
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Slf4j
public class FlightsLivePricesResultPollingRequest extends AbstractRequest {

    public enum SortType {
        carrier, duration, outboundarrivetime, outbounddeparttime, inboundarrivetime, inbounddeparttime, price
    }

    public enum SortOrder {
        asc, desc
    }

    /**
     * Constants for {@code *DepartTime} attributes.
     */
    public enum DepartTime {
        /**
         * Morning.
         */
        M,
        /**
         * Afternoon.
         */
        A,
        /**
         * Evening.
         */
        E
    }

    // -------------------------------------------------------------------------------------------------------- sortType

    // ------------------------------------------------------------------------------------------------------- sortOrder

    // -------------------------------------------------------------------------------------------------------- duration
    @JsonProperty("duration")
    @PositiveOrZero
    @Nullable
    public Long getDurationInMinutes() {
        return ofNullable(duration).map(Duration::toMinutes).orElse(null);
    }

    // ------------------------------------------------------------------------------------------------- includeCarriers
    @JsonProperty("includeCarriers")
    @Nullable
    public String getIncludeCarriersAsJoined() {
        return ofNullable(getIncludeCarriers())
                .map(c -> c.stream().collect(joining(";")))
                .orElse(null);
    }

    // ------------------------------------------------------------------------------------------------- excludeCarriers
    @JsonProperty("excludeCarriers")
    @Nullable
    public String getExcludeCarriersAsJoined() {
        return ofNullable(getExcludeCarriers())
                .map(c -> c.stream().collect(joining(";")))
                .orElse(null);
    }

    // -------------------------------------------------------------------------------------------------- originAirports
    @JsonProperty("originAirports")
    @Nullable
    public String getOriginAirportsAsJoined() {
        return ofNullable(getOriginAirports())
                .map(c -> c.stream().collect(joining(";")))
                .orElse(null);
    }

    // --------------------------------------------------------------------------------------------- destinationAirports
    @JsonProperty("destinationAirports")
    @Nullable
    public String getDestinationAirportsAsJoined() {
        return ofNullable(getDestinationAirports())
                .map(c -> c.stream().collect(joining(";")))
                .orElse(null);
    }

    // ----------------------------------------------------------------------------------------------------------- stops

    // ---------------------------------------------------------------------------------------------- outboundDepartTime
    @JsonProperty("outboundDepartTime")
    @Nullable
    public String getOutboundDepartTimeAsJoined() {
        return ofNullable(getOutboundDepartTime())
                .map(c -> c.stream().map(Enum::name).collect(joining(";")))
                .orElse(null);
    }

    // ----------------------------------------------------------------------------------------- outboundDepartStartTime

    // ------------------------------------------------------------------------------------------- outboundDepartEndTime

    // ----------------------------------------------------------------------------------------------- inboundDepartTime
    @JsonProperty("inboundDepartTime")
    @Nullable
    public String getInboundDepartTimeAsJoined() {
        return ofNullable(getInboundDepartTime())
                .map(c -> c.stream().map(Enum::name).collect(joining(";")))
                .orElse(null);
    }

    // ------------------------------------------------------------------------------------------ inboundDepartStartTime

    // -------------------------------------------------------------------------------------------- inboundDepartEndTime

    // -----------------------------------------------------------------------------------------------------------------
    @Nullable
    private SortType sortType;

    @Nullable
    private SortOrder sortOrder;

    @JsonIgnore
    @Nullable
    private Duration duration;

    // -----------------------------------------------------------------------------------------------------------------
    @JsonIgnore
    @Nullable
    private Set<@NotBlank String> includeCarriers;

    @JsonIgnore
    @Nullable
    private Set<@NotBlank String> excludeCarriers;

    // -----------------------------------------------------------------------------------------------------------------
    @JsonIgnore
    @Nullable
    private Set<@NotBlank String> originAirports;

    @JsonIgnore
    @Nullable
    private Set<@NotBlank String> destinationAirports;

    // -----------------------------------------------------------------------------------------------------------------
    @PositiveOrZero
    @Nullable
    private Integer stops;

    // -----------------------------------------------------------------------------------------------------------------
    @JsonIgnore
    @Size(min = 1)
    @Nullable
    private Set<@NotNull DepartTime> outboundDepartTime;

    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm")
    @Nullable
    private LocalTime outboundDepartStartTime;

    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm")
    @Nullable
    private LocalTime outboundDepartEndTime;

    // -----------------------------------------------------------------------------------------------------------------
    @JsonIgnore
    @Size(min = 1)
    @Nullable
    private Set<@NotNull DepartTime> inboundDepartTime;

    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm")
    @Nullable
    private LocalTime inboundDepartStartTime;

    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm")
    @Nullable
    private LocalTime inboundDepartEndTime;
}
