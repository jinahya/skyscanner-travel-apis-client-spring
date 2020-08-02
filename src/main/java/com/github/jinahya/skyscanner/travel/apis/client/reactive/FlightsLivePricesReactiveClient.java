package com.github.jinahya.skyscanner.travel.apis.client.reactive;

/*-
 * #%L
 * skyscanner-travel-apis-client-spring
 * %%
 * Copyright (C) 2020 Jinahya, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.pricing.v1_0.FlightsLivePricesResultPollingRequest;
import net.skyscanner.api.partners.apiservices.pricing.v1_0.FlightsLivePricesResultPollingResponse;
import net.skyscanner.api.partners.apiservices.pricing.v1_0.FlightsLivePricesSessionCreationRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static net.skyscanner.api.partners.apiservices.QueryParamEntries.queryParams;
import static net.skyscanner.api.partners.apiservices.pricing.v1_0.FlightsLivePricesResultPollingResponse.Status.UpdatesComplete;
import static net.skyscanner.api.partners.apiservices.pricing.v1_0.FlightsLivePricesResultPollingResponse.Status.UpdatesPending;
import static org.springframework.web.reactive.function.BodyInserters.fromFormData;
import static reactor.core.publisher.Flux.generate;

@Validated
@Component
@Slf4j
public class FlightsLivePricesReactiveClient extends SkyscannerTravelApisReactiveClient {

    // -----------------------------------------------------------------------------------------------------------------
    public Mono<String> createSession(@Valid @NotNull final FlightsLivePricesSessionCreationRequest request) {
        return applyWebClient(c -> c
                .post()
                .uri(b -> b.pathSegment("pricing", "v1.0").build())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(fromFormData(request.toFormData()))
                .exchange()
                .handle((r, s) -> {
                    final String location = r.headers().asHttpHeaders().getFirst(HttpHeaders.LOCATION);
                    if (location == null) {
                        s.error(new RuntimeException("no location header received"));
                        return;
                    }
                    s.next(location);
                })
        );
    }

    public Flux<FlightsLivePricesResultPollingResponse> pollResult(
            @NotBlank final String location,
            @NotNull final FlightsLivePricesResultPollingRequest request) {
        return generate(
                () -> FlightsLivePricesResultPollingResponse.ofStatus(UpdatesPending),
                (status, sink) -> {
                    if (status.getStatus() == UpdatesComplete) {
                        sink.complete();
                        return status;
                    }
                    return applyWebClient(c -> c
                            .get()
                            .uri(location, b -> b.queryParams(queryParams(request)).build())
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(FlightsLivePricesResultPollingResponse.class)
                            .doOnNext(sink::next)
                            .doOnError(sink::error)
                            .block()
                    );
                }
        );
    }
}
