package com.github.jinahya.skyscanner.travel.apis.client.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Function;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.springframework.core.io.buffer.DataBufferUtils.releaseConsumer;
import static org.springframework.core.io.buffer.DataBufferUtils.write;
import static reactor.core.publisher.Mono.fromFuture;
import static reactor.core.publisher.Mono.using;

@Slf4j
public final class ResponseSpecUtils {

    public static <R> Mono<R> pipeBodyAndApply(final WebClient.ResponseSpec response,
                                               final Function<? super ReadableByteChannel, ? extends R> function) {
        if (response == null) {
            throw new NullPointerException("response is null");
        }
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

    private ResponseSpecUtils() {
        super();
    }
}
