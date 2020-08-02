package net.skyscanner.api.partners.apiservices.reference.v10;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@EqualsAndHashCode
@ToString
@Setter
@Getter
@Slf4j
public class Currency implements Serializable {

    private static final long serialVersionUID = -3671431772010618305L;

    private String code;

    private String symbol;

    private String thousandsSeparator;

    private String decimalSeparator;

    private Boolean symbolOnLeft;

    private Boolean spaceBetweenAmountAndSymbol;

    private Integer roundingCoefficient;

    private Integer decimalDigits;
}
