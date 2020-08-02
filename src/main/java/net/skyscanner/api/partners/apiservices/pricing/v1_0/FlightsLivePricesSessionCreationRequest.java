package net.skyscanner.api.partners.apiservices.pricing.v1_0;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.AbstractRequest;
import net.skyscanner.api.partners.apiservices.FormDataEntries;
import net.skyscanner.api.partners.apiservices.FormDataEntry;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Currency;
import java.util.Locale;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@Setter
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Slf4j
public class FlightsLivePricesSessionCreationRequest extends AbstractRequest {

    // --------------------------------------------------------------------------------------------------------- country
    public void setCountryFromLocale(final Locale locale) {
        setCountry(ofNullable(locale).map(Locale::getCountry).orElse(null));
    }

    // -------------------------------------------------------------------------------------------------------- currency
    public void setCurrencyFromCurrency(final Currency currency) {
        setCurrency(ofNullable(currency).map(Currency::getCurrencyCode).orElse(null));
    }

    // ---------------------------------------------------------------------------------------------------------- locale
    public void setLocaleFromLocale(final Locale locale) {
        setLocale(ofNullable(locale).map(Locale::toString).orElse(null));
    }

    // ----------------------------------------------------------------------------------------------------- originPlace

    // ------------------------------------------------------------------------------------------------ destinationPlace

    // ---------------------------------------------------------------------------------------------------- outboundDate

    // ----------------------------------------------------------------------------------------------------- inboundDate

    // ------------------------------------------------------------------------------------------------------ cabinClass

    // ---------------------------------------------------------------------------------------------------------- adults

    // -------------------------------------------------------------------------------------------------------- children

    // --------------------------------------------------------------------------------------------------------- infants

    // ------------------------------------------------------------------------------------------------- includeCarriers
    public void setIncludeCarriersFromCollection(final Collection<String> includeCarriers) {
        setIncludeCarriers(ofNullable(includeCarriers).map(c -> String.join(",", c)).orElse(null));
    }

    // ------------------------------------------------------------------------------------------------- excludeCarriers
    public void setExcludeCarriersFromCollection(final Collection<String> excludeCarriers) {
        setIncludeCarriers(ofNullable(excludeCarriers).map(c -> String.join(",", c)).orElse(null));
    }

    // ---------------------------------------------------------------------------------------------------- groupPricing

    // -----------------------------------------------------------------------------------------------------------------
    public WebClient.RequestHeadersSpec<?> body(final WebClient.RequestBodySpec bodySpec) {
        requireNonNull(bodySpec, "bodySpec is null");
        return bodySpec.body(fromFormData(toFormData()));
    }

    public MultiValueMap<String, String> toFormData(final MultiValueMap<String, String> formData) {
        requireNonNull(formData, "formData is null");
        FormDataEntries.toFormData(this, formData);
        return formData;
    }

    public MultiValueMap<String, String> toFormData() {
        return toFormData(new LinkedMultiValueMap<>());
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotBlank
    @NonNull
    @FormDataEntry
    private String country;

    @NotBlank
    @NonNull
    @FormDataEntry
    private String currency;

    @NotBlank
    @NonNull
    @FormDataEntry
    private String locale;

    @NotBlank
    @NonNull
    @FormDataEntry
    private String originPlace;

    @NotBlank
    @NonNull
    @FormDataEntry
    private String destinationPlace;

    //@JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NonNull
    @FormDataEntry
    private LocalDate outboundDate;

    //@JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Nullable
    @FormDataEntry
    private LocalDate inboundDate;

    @FormDataEntry
    private String cabinClass;

    @Max(8)
    @Min(1)
    @FormDataEntry
    private int adults;

    @Max(8)
    @Min(0)
    @FormDataEntry
    private int children;

    @Max(8)
    @Min(0)
    @Nullable
    @FormDataEntry
    private int infants;

    @Size(min = 1)
    @Nullable
    @FormDataEntry
    private String includeCarriers; // comma(,)-separated list

    @Size(min = 1)
    @Nullable
    @FormDataEntry
    private String excludeCarriers; // comma(,)-separated list

    @Nullable
    @FormDataEntry
    private Boolean groupPricing;
}
