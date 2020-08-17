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

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.pricing.v1_0.FlightsLivePricesResultPollingRequest;
import net.skyscanner.api.partners.apiservices.pricing.v1_0.FlightsLivePricesResultPollingResponse;
import net.skyscanner.api.partners.apiservices.pricing.v1_0.FlightsLivePricesSessionCreationRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static net.skyscanner.api.partners.apiservices.QueryParamEntries.queryParams;
import static net.skyscanner.api.partners.apiservices.pricing.v1_0.FlightsLivePricesResultPollingResponse.Status.UpdatesComplete;
import static net.skyscanner.api.partners.apiservices.pricing.v1_0.FlightsLivePricesResultPollingResponse.Status.UpdatesPending;
import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

@Validated
@Component
@Slf4j
public class FlightsLivePricesReactiveClient extends SkyscannerTravelApisReactiveClient {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new session for polling prices and returns the {@link HttpHeaders#LOCATION} header value.
     *
     * @param request a request object.
     * @return a mono with the {@link HttpHeaders#LOCATION} header value.
     * @see #pollResult(String, FlightsLivePricesResultPollingRequest)
     */
    @NonNull
    public Mono<String> createSession(@Valid @NotNull final FlightsLivePricesSessionCreationRequest request) {
        return webClient()
                .post()
                .uri(b -> b.pathSegment("pricing", "v1.0").build())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(fromFormData(request.toFormData()))
                .exchange()
                .handle((r, s) -> {
                    final HttpStatus status = r.statusCode();
                    if (!status.is2xxSuccessful()) {
                        log.error("an unsuccessful status received: " + status);
                        s.error(r.createException().block());
                        return;
                    }
                    final String location = r.headers().asHttpHeaders().getFirst(HttpHeaders.LOCATION);
                    if (location == null) {
                        log.error("no {} header received", HttpHeaders.LOCATION);
                        s.error(r.createException().block());
                        return;
                    }
                    s.next(location);
                });
    }

    /**
     * Polls live prices.
     *
     * @param location a {@link HttpHeaders#LOCATION} header value from {@link #createSession(FlightsLivePricesSessionCreationRequest)}
     *                 method.
     * @param request  a request object.
     * @return a flux of live prices.
     * @see #createSession(FlightsLivePricesSessionCreationRequest)
     */
    @NonNull
    public Flux<FlightsLivePricesResultPollingResponse> pollResult(
            @NotBlank final String location, @Valid @NotNull final FlightsLivePricesResultPollingRequest request) {
        return Flux.generate(
                () -> UpdatesPending,
                (state, sink) -> {
                    if (state == UpdatesComplete) {
                        sink.complete();
                        return state;
                    }
                    return webClient()
                            .get()
                            .uri(location, b -> b.queryParams(queryParams(request)).build())
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(FlightsLivePricesResultPollingResponse.class)
                            .doOnNext(sink::next)
                            .doOnError(sink::error)
                            .map(FlightsLivePricesResultPollingResponse::getStatus)
                            .block()
                            ;
                }
        );
    }
}
