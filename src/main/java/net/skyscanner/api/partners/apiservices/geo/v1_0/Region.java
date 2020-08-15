package net.skyscanner.api.partners.apiservices.geo.v1_0;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.AbstractObject;

@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@Setter
@Getter
@ToString(callSuper = true)
@Slf4j
public class Region extends AbstractObject {

}
