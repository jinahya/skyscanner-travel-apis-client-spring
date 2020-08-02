package net.skyscanner.api.partners.apiservices.reference.v10;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.skyscanner.api.partners.apiservices.AbstractResponse;

@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class Country extends AbstractResponse {

    private static final long serialVersionUID = 3201164038531344103L;

    private String code;

    private String name;
}
