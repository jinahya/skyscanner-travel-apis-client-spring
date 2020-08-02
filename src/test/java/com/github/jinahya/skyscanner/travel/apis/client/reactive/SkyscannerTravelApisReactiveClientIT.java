package com.github.jinahya.skyscanner.travel.apis.client.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.github.jinahya.skyscanner.travel.apis.client.Application.SYSTEM_PROPERTY_NAME_API_KEY;
import static java.util.Objects.requireNonNull;

@EnabledIf("#{systemProperties['" + SYSTEM_PROPERTY_NAME_API_KEY + "'] != null}")
@SpringBootTest
@Slf4j
abstract class SkyscannerTravelApisReactiveClientIT<T extends SkyscannerTravelApisReactiveClient> {

    SkyscannerTravelApisReactiveClientIT(final Class<T> clientClass) {
        super();
        this.clientClass = requireNonNull(clientClass, "clientClass is null");
    }

//    /**
//     * Tets {@link SkyscannerTravelApisReactiveClient#listPlaces(ListPlacesRequest, FluxProcessor)} method.
//     *
//     * @throws IOException if an I/O error occurs.
//     */
//    @Test
//    void testListPlaces() throws IOException, InterruptedException, ExecutionException {
//        final ListPlacesRequest request = ListPlacesRequest.builder()
//                .country("KR")
//                .currency("KRW")
//                .locale("ko-KR")
//                .query("ICN")
//                .build();
//        final EmitterProcessor<Place> processor = EmitterProcessor.create();
//        processor.subscribe(p -> log.debug("place: {}", p));
//        final CompletableFuture<Disposable> future = client.listPlaces(request, processor);
//        future.get();
//    }

    // -----------------------------------------------------------------------------------------------------------------
    protected <R> R applyClientInstance(final Function<? super T, ? extends R> function) {
        return requireNonNull(function, "function is null").apply(clientInstance);
    }

    protected void acceptClientInstance(final Consumer<? super T> consumer) {
        requireNonNull(consumer, "consumer is null");
        applyClientInstance(c -> {
            consumer.accept(c);
            return null;
        });
    }

    // -----------------------------------------------------------------------------------------------------------------
    protected final Class<T> clientClass;

    @Autowired
    private T clientInstance;
}