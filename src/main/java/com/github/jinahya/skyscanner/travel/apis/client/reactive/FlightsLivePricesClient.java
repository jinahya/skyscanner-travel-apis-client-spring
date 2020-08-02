package com.github.jinahya.skyscanner.travel.apis.client.reactive;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.pricing.v1_0.FlightsLivePricesResultPollingRequest;
import net.skyscanner.api.partners.apiservices.pricing.v1_0.FlightsLivePricesResultPollingResponse;
import net.skyscanner.api.partners.apiservices.pricing.v1_0.FlightsLivePricesResultPollingResponse.Status;
import net.skyscanner.api.partners.apiservices.pricing.v1_0.FlightsLivePricesSessionCreationRequest;
import org.reactivestreams.Subscriber;
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
public class FlightsLivePricesClient extends SkyscannerTravelApisReactiveClient {

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

    public void pollResult(@NotBlank final String location,
                           @NotNull final FlightsLivePricesResultPollingRequest request,
                           @NotNull final Subscriber<? super FlightsLivePricesResultPollingResponse> subscriber) {
        while (true) {
            final Status status = applyWebClient(c -> c
                    .get()
                    .uri(location, b -> b.queryParams(queryParams(request)).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(FlightsLivePricesResultPollingResponse.class)
                    .doOnError(subscriber::onError)
                    .doOnNext(subscriber::onNext)
                    .map(FlightsLivePricesResultPollingResponse::getStatus)
                    //.switchIfEmpty(just(FlightsLivePricesResultPollingResponse.Status.UpdatesComplete))
                    .block()
            );
            if (status == UpdatesComplete) {
                subscriber.onComplete();
                break;
            }
        }
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
