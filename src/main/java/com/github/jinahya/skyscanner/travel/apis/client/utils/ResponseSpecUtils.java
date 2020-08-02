package com.github.jinahya.skyscanner.travel.apis.client.utils;

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

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.springframework.core.io.buffer.DataBufferUtils.releaseConsumer;
import static org.springframework.core.io.buffer.DataBufferUtils.write;
import static reactor.core.publisher.Mono.fromFuture;
import static reactor.core.publisher.Mono.using;

@Slf4j
public final class ResponseSpecUtils {

    public static <R> Mono<R> pipeBodyAndApply(final WebClient.ResponseSpec response,
                                               final Function<? super ReadableByteChannel, ? extends R> function) {
        requireNonNull(response, "response is null");
        requireNonNull(function, "function is null");
        return using(
                Pipe::open,
                p -> fromFuture(supplyAsync(() -> function.apply(p.source())))
                        .doFirst(() -> write(response.bodyToFlux(DataBuffer.class), p.sink())
                                .doOnError(t -> log.error("failed to write body to pipe.sink", t))
                                .doFinally(s -> {
                                    try {
                                        p.sink().close();
                                    } catch (final IOException ioe) {
                                        log.error("failed to close pipe.sink", ioe);
                                    }
                                })
                                .subscribe(releaseConsumer())),
                p -> {
                    try {
                        p.source().close();
                    } catch (final IOException ioe) {
                        log.error("failed to close the pipe.source", ioe);
                        throw new RuntimeException(ioe);
                    }
                }
        );
    }

    public static Mono<Void> pipeBodyAndAccept(final WebClient.ResponseSpec response,
                                               final Consumer<? super ReadableByteChannel> consumer) {
        requireNonNull(response, "response is null");
        requireNonNull(consumer, "consumer is null");
        return pipeBodyAndApply(response, c -> {
            consumer.accept(c);
            return null;
        });
    }

    private ResponseSpecUtils() {
        super();
    }
}
