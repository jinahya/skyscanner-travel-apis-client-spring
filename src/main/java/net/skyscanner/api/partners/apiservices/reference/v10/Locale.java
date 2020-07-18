package net.skyscanner.api.partners.apiservices.reference.v10;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@EqualsAndHashCode
@Setter
@Getter
public class Locale implements Serializable {

    private static final long serialVersionUID = 520597589779744016L;

    private String code;

    private String name;
}
