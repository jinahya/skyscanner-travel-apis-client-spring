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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import net.skyscanner.api.partners.apiservices.autosuggest.v1_0.ListPlacesRequest;
import net.skyscanner.api.partners.apiservices.autosuggest.v1_0.Place;
import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.channels.Pipe;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.github.jinahya.skyscanner.travel.apis.client.utils.JsonParserUtils.parseArray;
import static java.nio.channels.Channels.newInputStream;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.springframework.core.io.buffer.DataBufferUtils.releaseConsumer;
import static org.springframework.core.io.buffer.DataBufferUtils.write;

@Accessors(fluent = true)
@Slf4j
public abstract class SkyscannerTravelApisReactiveClient {

    /**
     * A qualifier annotation for an instance of {@link WebClient} for accessing Skyscanner Travel APIs.
     */
    @Qualifier
    @Documented
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SkyscannerTravelApisReactiveWebClient {

    }

    // -----------------------------------------------------------------------------------------------------------------
    @PostConstruct
    private void onPostConstruct() {
    }

    // -----------------------------------------------------------------------------------------------------------------
    public CompletableFuture<Disposable> listPlaces(@Valid @NotNull final ListPlacesRequest request,
                                                    @NotNull final Subscriber<? super Place> processor)
            throws IOException {
        final Pipe pipe = Pipe.open();
        final CompletableFuture<Disposable> writer;
        {
            final Flux<DataBuffer> source = webClient
                    .get()
                    .uri(b -> b.pathSegment("autosuggest", "v1.0", "{country}", "{currency}", "{locale}")
                            .queryParam("query", request.getQuery())
                            .build(request.getCountry(), request.getCurrency(), request.getLocale()))
                    .retrieve()
                    .bodyToFlux(DataBuffer.class);
            writer = supplyAsync(
                    () -> write(source, pipe.sink())
                            .doFinally(s -> {
                                try {
                                    pipe.sink().close();
                                } catch (final IOException ioe) {
                                    throw new RuntimeException(ioe);
                                }
                            })
                            .subscribe(releaseConsumer())
            );
        }
        try (JsonParser parser = objectMapper.createParser(newInputStream(pipe.source()))) {
            parseArray(parser, "Places", Place.class, processor::onNext);
        }
        processor.onComplete();
        return writer;
    }

    // -----------------------------------------------------------------------------------------------------------------
    protected <R> R applyWebClient(@NotNull final Function<? super WebClient, ? extends R> function) {
        return function.apply(webClient);
    }

    protected void acceptWebClient(@NotNull final Consumer<? super WebClient> consumer) {
        applyWebClient(c -> {
            consumer.accept(c);
            return null;
        });
    }

    protected <U, R> R applyWebClient(
            @NotNull final BiFunction<? super WebClient, ? super U, ? extends R> function,
            @NotNull final Supplier<? extends U> supplier) {
        return applyWebClient(v -> function.apply(v, supplier.get()));
    }

    protected <U> void acceptWebClient(
            @NotNull final BiConsumer<? super WebClient, ? super U> consumer,
            @NotNull final Supplier<? extends U> supplier) {
        applyWebClient(
                (v, u) -> {
                    consumer.accept(v, u);
                    return null;
                },
                supplier
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Autowired
    @SkyscannerTravelApisReactiveWebClient
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.PROTECTED)
    private WebClient webClient;

    @Autowired
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.PROTECTED)
    private ObjectMapper objectMapper;
}
