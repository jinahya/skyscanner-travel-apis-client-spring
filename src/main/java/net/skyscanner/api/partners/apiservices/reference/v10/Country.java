package net.skyscanner.api.partners.apiservices.reference.v10;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.skyscanner.api.partners.apiservices.AbstractObject;

@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class Country extends AbstractObject {

    private static final long serialVersionUID = 4215196899083662340L;

    private String code;

    private String name;

    private transient Locale locale;
}
