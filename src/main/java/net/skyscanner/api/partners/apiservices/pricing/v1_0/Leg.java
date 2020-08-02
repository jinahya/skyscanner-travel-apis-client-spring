package net.skyscanner.api.partners.apiservices.pricing.v1_0;

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
