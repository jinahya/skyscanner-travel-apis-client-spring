package com.github.jinahya.skyscanner.travel.apis.client;

import com.github.jinahya.skyscanner.travel.apis.client.SkyscannerTravelApisClient.SkyscannerTravelApisRootUri;
import com.github.jinahya.skyscanner.travel.apis.client.reactive.SkyscannerTravelApisReactiveClient.SkyscannerTravelApisReactiveWebClient;
import com.github.jinahya.skyscanner.travel.apis.client.reactive.function.SkyscannerApiKeyFilterFunction;
import com.github.jinahya.skyscanner.travel.apis.client.reactive.function.SkyscannerApiKeyFilterFunction.SkyscannerTravelApisApiKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.MalformedURLException;
import java.net.URI;

@SpringBootApplication
@Slf4j
public class Application {

    public static final String SYSTEM_PROPERTY_NAME_API_KEY = "apiKey";

    @SkyscannerTravelApisRootUri
    @Bean
    URI skyscannerTravelApisRootUri() {
        return URI.create(skyscannerTravelApisRootUri);
    }

    @SkyscannerTravelApisApiKey
    @Bean
    String skyscannerTravelApisApiKey() {
        return skyscannerTravelApisApiKey;
    }

    @SkyscannerTravelApisReactiveWebClient
    @Bean
    WebClient skyscannerTravelApisReactiveWebClient(@SkyscannerTravelApisRootUri final URI rootUri,
                                                    final SkyscannerApiKeyFilterFunction filterFunction)
            throws MalformedURLException {
        return WebClient.builder()
                .baseUrl(rootUri.toURL().toExternalForm())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .filter(filterFunction)
                .codecs(c -> c.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build()
                ;
    }

    @Value("${skyscanner-travel-apis.root-uri}")
    private String skyscannerTravelApisRootUri;

    @Value("#{systemProperties['" + SYSTEM_PROPERTY_NAME_API_KEY + "']}")
    private String skyscannerTravelApisApiKey;
}
