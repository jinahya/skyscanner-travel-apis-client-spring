package com.github.jinahya.skyscanner.travel.apis.client;

abstract class SkyscannerTravelApisClientTest<T extends SkyscannerTravelApisClient>
        extends AbstractSkyscannerTravelApisClientTest<T> {

    protected SkyscannerTravelApisClientTest(final Class<T> clientClass) {
        super(clientClass);
    }
}