package net.skyscanner.api.partners.apiservices.geo.v1_0;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.AbstractObject;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@Setter
@Getter
@ToString(callSuper = true)
@Slf4j
public class Airport extends AbstractObject {

    // -------------------------------------------------------------------------------------------------------- location
    public BigDecimal getLongitude() {
        if (location == null) {
            return null;
        }
        final int index = location.indexOf(',');
        return new BigDecimal(location.substring(0, index).trim());
    }

    public BigDecimal getLatitude() {
        if (location == null) {
            return null;
        }
        final int index = location.indexOf(',');
        return new BigDecimal(location.substring(index + 1).trim());
    }

    // -----------------------------------------------------------------------------------------------------------------
    private String id;

    private String name;

    private String countryId;

    private String location; // longitude, latitude

    private String cityId;

    private String regionId;
}
