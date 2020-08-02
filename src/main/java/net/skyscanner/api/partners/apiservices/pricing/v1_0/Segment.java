package net.skyscanner.api.partners.apiservices.pricing.v1_0;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@Setter
@Getter
@Slf4j
public class Segment {

    private Long id;

    private Long originStation;

    private Long destinationStation;

    private LocalDateTime departureDateTime;

    private LocalDateTime arrivalDateTime;

    private Long carrier;

    private Long operatingCarrier;

    private Duration duration;

    private String flightNumber;

    private String journeyMode;

    private String directionality;
}
