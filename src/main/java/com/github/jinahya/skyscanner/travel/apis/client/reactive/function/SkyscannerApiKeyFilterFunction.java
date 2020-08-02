package com.github.jinahya.skyscanner.travel.apis.client.reactive.function;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;

/**
 * An exchange filter function for attaching {@link #QUERY_PARAM_NAME_API_KEY} attribute.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Component
public class SkyscannerApiKeyFilterFunction implements ExchangeFilterFunction {

    public static final String QUERY_PARAM_NAME_API_KEY = "apiKey";

    /**
     * A qualifier annotation for {@code apiKey} for Skyscanner-Travel-APIs.
     */
    @Qualifier
    @Documented
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SkyscannerTravelApisApiKey {

    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public Mono<ClientResponse> filter(final ClientRequest clientRequest, final ExchangeFunction exchangeFunction) {
        final URI url = UriComponentsBuilder.fromUri(clientRequest.url())
                .queryParam(QUERY_PARAM_NAME_API_KEY, skyscannerTravelApisApiKey)
                .build()
                .toUri();
        final ClientRequest newClientRequest
                = ClientRequest.from(clientRequest)
                .url(url)
                .build();
        return exchangeFunction.exchange(newClientRequest);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotBlank
    @Autowired
    @SkyscannerTravelApisApiKey
    private String skyscannerTravelApisApiKey;
}
