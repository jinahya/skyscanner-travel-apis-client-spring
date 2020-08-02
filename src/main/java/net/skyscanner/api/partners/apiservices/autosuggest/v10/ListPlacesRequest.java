package net.skyscanner.api.partners.apiservices.autosuggest.v10;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Getter
@Builder
@Slf4j
public class ListPlacesRequest {

    // -----------------------------------------------------------------------------------------------------------------
    @NotBlank
    private String country;

    @NotBlank
    private String currency;

    @NotBlank
    private String locale;

    @Size(min = 2)
    @NotNull
    private String query;
}
