package net.skyscanner.api.partners.apiservices.pricing.v1_0;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.AbstractResponse;
import net.skyscanner.api.partners.apiservices.reference.v10.Currency;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Slf4j
public class FlightsLivePricesResultPollingResponse extends AbstractResponse {

    public enum Status {
        UpdatesPending, UpdatesComplete
    }

    public static FlightsLivePricesResultPollingResponse ofStatus(final Status status) {
        return builder().status(status).build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    private String sessionKey;

    private FlightsLivePricesSessionCreationRequest query;

    @NotNull
    private Status status;

    private List<@Valid @NotNull Itinerary> itineraries;

    private List<@Valid @NotNull Leg> legs;

    private List<@Valid @NotNull Segment> segments;

    private List<@Valid @NotNull Carrier> carriers;

    private List<@Valid @NotNull Carrier> agents;

    private List<@Valid @NotNull Place> places;

    private List<@Valid @NotNull Currency> currencies;
}
