package net.skyscanner.api.partners.apiservices.pricing.v1_0;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@ToString
@Setter
@Getter
@Slf4j
public class FlightNumber {

    private String flightNumber;

    private Long carrierId;
}
