package com.github.jinahya.skyscanner.travel.apis.client;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import static com.github.jinahya.skyscanner.travel.apis.client.Application.SYSTEM_PROPERTY_NAME_API_KEY;
import static java.util.Objects.requireNonNull;

@EnabledIf("#{systemProperties['" + SYSTEM_PROPERTY_NAME_API_KEY + "'] != null}")
@SpringBootTest
@Accessors(fluent = true)
@Slf4j
public abstract class AbstractSkyscannerTravelApisClientIT<T extends AbstractSkyscannerTravelApisClient> {

    protected AbstractSkyscannerTravelApisClientIT(final Class<T> clientClass) {
        super();
        this.clientClass = requireNonNull(clientClass, "clientClass is null");
    }

    protected Class<T> clientClass;

    @Autowired
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.PROTECTED)
    private T clientInstance;
}