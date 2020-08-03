package com.github.jinahya.skyscanner.travel.apis.client;

import static java.util.Objects.requireNonNull;

public abstract class AbstractSkyscannerTravelApisClientTest<T extends AbstractSkyscannerTravelApisClient> {

    protected AbstractSkyscannerTravelApisClientTest(final Class<T> clientClass) {
        super();
        this.clientClass = requireNonNull(clientClass, "clientClass is null");
    }

    protected Class<T> clientClass;
}