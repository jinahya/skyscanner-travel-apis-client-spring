package net.skyscanner.api.partners.apiservices.reference.v10;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@EqualsAndHashCode
@Setter
@Getter
public class Currency implements Serializable {

    private static final long serialVersionUID = -4377751020059973842L;

    private String code;

    private String symbol;

    private String thousandsSeparator;

    private String decimalSeparator;

    private boolean symbolOnLeft;

    private boolean spaceBetweenAmountAndSymbol;

    private int roundingCoefficient;

    private int decimalDigits;
}
