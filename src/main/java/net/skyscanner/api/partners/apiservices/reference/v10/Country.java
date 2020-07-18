package net.skyscanner.api.partners.apiservices.reference.v10;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@EqualsAndHashCode
@Setter
@Getter
public class Country implements Serializable {

    private static final long serialVersionUID = 4215196899083662340L;

    private String code;

    private String name;

    private transient Locale locale;
}
