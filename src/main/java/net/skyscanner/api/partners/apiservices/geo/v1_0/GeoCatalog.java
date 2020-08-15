package net.skyscanner.api.partners.apiservices.geo.v1_0;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.AbstractResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@Setter
@Getter
@ToString(callSuper = true)
@Slf4j
public class GeoCatalog extends AbstractResponse {

    private List<@Valid @NotNull Continent> continents;
}
